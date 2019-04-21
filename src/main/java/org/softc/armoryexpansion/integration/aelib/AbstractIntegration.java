package org.softc.armoryexpansion.integration.aelib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.ITiCMaterial;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.softc.armoryexpansion.integration.aelib.Config.CATEGORY_MATERIAL;

public abstract class AbstractIntegration{
    private Logger logger;
    protected String modid = "";
    private Config configHelper;
    private boolean isEnabled = false;
    private Map<String, ITiCMaterial> materials = new HashMap<>();

    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        Property property = ArmoryExpansion.config
                .get("integrations", modid, true, "Whether integration with " + modid + " should be enabled");
        isEnabled = property == null || property.getBoolean();
        ArmoryExpansion.config.save();
        if(isEnabled){
            configHelper = new Config(new Configuration(new File(event.getModConfigurationDirectory().getPath() + "/" + ArmoryExpansion.MODID + "/" + modid + ".cfg")));
//            configHelper = new Config(new Configuration(event.getSuggestedConfigurationFile()));
            setMaterials(event);
            configHelper.syncConfig(materials);
            registerMaterials();
            registerMaterialStats();
        }
    }

    public void init(FMLInitializationEvent event) {
        if(isEnabled){
            oredictMaterials();
            updateMaterials();
            registerMaterialTraits();
        }
    }

    public Configuration getConfiguration() {
        return configHelper.getConfiguration();
    }

    private Config getConfigHelper() {
        return configHelper;
    }

    protected void addMaterial(ITiCMaterial material){
        if(isMaterialEnabled(material)){
            this.materials.putIfAbsent(material.getIdentifier(), material);
        }
    }

    private void setMaterials(FMLPreInitializationEvent event){
        loadMaterialsFromJson(event.getModConfigurationDirectory(), modid);
        loadMaterialsFromSource();
        saveMaterialsToJson(event.getModConfigurationDirectory(), modid);
    }

    protected abstract void loadMaterialsFromSource();

    private void loadMaterials(TiCMaterial[] jsonMaterials){
        for(TiCMaterial m:jsonMaterials){
            materials.putIfAbsent(m.getIdentifier(), m);
        }
    }

    protected void loadMaterialsFromJson(InputStream path){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();

        TiCMaterial[] jsonMaterials = gson.fromJson(new BufferedReader(new InputStreamReader(path)), TiCMaterial[].class);
        loadMaterials(jsonMaterials);
    }

    private void loadMaterialsFromJson(String path){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();

        TiCMaterial[] jsonMaterials = new TiCMaterial[0];
        try {
            File input = new File(path);
            jsonMaterials = gson.fromJson(new FileReader(input), TiCMaterial[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loadMaterials(jsonMaterials);
    }

    private void loadMaterialsFromJson(File configDir, String root, String modid){
        loadMaterialsFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void loadMaterialsFromJson(File configDir, String modid){
        loadMaterialsFromJson(configDir, "armoryexpansion", modid);
    }

    private void saveMaterialsToJson(String path){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();
        File output = new File(path);
        //noinspection ResultOfMethodCallIgnored
        output.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(returnMaterialExample());
            writer.write(gson.toJson(materials.values()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMaterialsToJson(File configDir, String root, String modid){
        saveMaterialsToJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void saveMaterialsToJson(File configDir, String modid){
        saveMaterialsToJson(configDir, "armoryexpansion", modid);
    }

    private void oredictMaterials() {
        materials.values().forEach(ITiCMaterial::registerOreDict);
    }

    private void registerMaterials() {
        materials.values().forEach(m -> {
            if(isMaterialEnabled(m)){
                if(m.registerTinkersMaterial()){
                    logger.info("Registered tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void registerMaterialStats() {
        materials.values().forEach(m -> {
            if(isMaterialEnabled(m)){
                if(m.registerTinkersMaterialStats(getProperties(m))){
                    logger.info("Registered stats for tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void updateMaterials() {
        materials.values().forEach(m -> {
            if(isMaterialEnabled(m)){
                if(m.updateTinkersMaterial()){
                    logger.info("Updated tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void registerMaterialTraits() {
        materials.values().forEach(m -> {
            if(isMaterialEnabled(m)){
                if(m.registerTinkersMaterialTraits()){
                    logger.info("Registered traits for tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private Map<String, Property> getProperties(ITiCMaterial material){
        return this.getConfigHelper().getProperties(material.getIdentifier());
    }

    private Property getProperty(ITiCMaterial material, String property){
        Map<String, Property> properties = getProperties(material);
        if(properties != null && properties.containsKey(property)){
            return getProperties(material).get(property);
        }
        return null;
    }

    public boolean isEnabled(ITiCMaterial material, String property){
        return (getProperty(material, property) != null) && Objects.requireNonNull(getProperty(material, property)).getBoolean();
    }

    private boolean isMaterialEnabled(ITiCMaterial material){
        Property property = getProperty(material, CATEGORY_MATERIAL);
        if (property != null){
            return (getProperty(material, CATEGORY_MATERIAL) != null) && Objects.requireNonNull(getProperty(material, CATEGORY_MATERIAL)).getBoolean();
        }
        return true;
    }

    private String returnMaterialExample(){
        String example =
                "//  {\n" +
                "//    The material's durability is this value multiplied by 8\n" +
                "//    \"durability\": 36,\n" +
                "//    The material's mining speed is this value multiplied by 0.65\n" +
                "//    \"miningSpeed\": 0.0,\n" +
                "//    The material's attack is this value | The material's bonus damage is this value divided by 3\n" +
                "//    \"damage\": 0.0,\n" +
                "//    The material's modifier is this value divided by 6 plus 0.1\n" +
                "//    \"magicAffinity\": 15.0,\n" +
                "//    The material's harvest level is this value\n" +
                "//    \"harvestLevel\": 0,\n" +
                "//    The material's range is this value\n" +
                "//    \"range\": 0.0,\n" +
                "//    The material's accuracy is this value with 1.0 being 100%\n" +
                "//    \"accuracy\": 0.0,\n" +
                "//    The material's defense is this value multiplied by 2\n" +
                "//    \"defense\": 9.0,\n" +
                "//    The material's toughness is this value multiplied by 1.5\n" +
                "//    \"toughness\": 2.0,\n" +
                "//    The material's traits\n" +
                "//    \"traits\": [\n" +
                "//      {\n" +
                "//        The trait's identifier\n" +
                "//        \"traitName\": \"rough_armor\",\n" +
                "//        The tool part to have the trait\n" +
                "//        \"traitPart\": \"core\"\n" +
                "//      }\n" +
                "//    ],\n" +
                "//    The material's oreDict entry and identifier\n" +
                "//    \"identifier\": \"scalesilverdragon\",\n" +
                "//    The item to be added to the material's oreDict entry\n" +
                "//    \"itemName\": \"iceandfire:dragonscales_silver\",\n" +
                "//    The item's meta value\n" +
                "//    \"meta\": 0,\n" +
                "//    The material's color\n" +
                "//    \"color\": 11184810,\n" +
                "//    The material render info to be used for the material's aspect (unused at the moment)\n" +
                "//    \"type\": \"METAL\",\n" +
                "//    If the material should be castable with a casting table or casting basin (no fluids are added to the materials at the moment)\n" +
                "//    \"isCastable\": false,\n" +
                "//    If the material should be craftable in the part crafter\n" +
                "//    \"isCraftable\": true,\n" +
                "//    If the material should be useable for tool parts\n" +
                "//    \"isToolMaterial\": false,\n" +
                "//    If the material should be useable for bow limbs\n" +
                "//    \"isBowMaterial\": false,\n" +
                "//    If the material should be useable for fletchings\n" +
                "//    \"isFletchingMaterial\": false,\n" +
                "//    If the material should be useable for projectile parts\n" +
                "//    \"isProjectileMaterial\": false,\n" +
                "//    If the material should be useable for armor parts\n" +
                "//    \"isArmorMaterial\": true\n" +
                "//  }\n";
        return example;
    }
}
