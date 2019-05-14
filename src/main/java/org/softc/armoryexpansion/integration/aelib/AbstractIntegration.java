package org.softc.armoryexpansion.integration.aelib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.ITiCMaterial;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;

import java.io.*;
import java.util.*;

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
            this.configHelper = new Config(new Configuration(new File(event.getModConfigurationDirectory().getPath() + "/" + ArmoryExpansion.MODID + "/" + modid + ".cfg")));
//            configHelper = new Config(new Configuration(event.getSuggestedConfigurationFile()));
            this.setMaterials(event);
            this.configHelper.syncConfig(materials);
            this.registerMaterials();
            this.registerMaterialStats();
        }
    }

    public void init(FMLInitializationEvent event) {
        if(isEnabled){
            this.oredictMaterials();
            this.updateMaterials();
            this.registerMaterialTraits();
        }
    }

    public void postInit(FMLPostInitializationEvent event){

    }

    public void registerBlocks(RegistryEvent.Register<Block> event){
        this.materials.values().forEach(m -> event.getRegistry().registerAll(m.getFluidBlock()));
    }

    public Configuration getConfiguration() {
        return this.configHelper.getConfiguration();
    }

    private Config getConfigHelper() {
        return this.configHelper;
    }

    protected void addMaterial(ITiCMaterial material){
        if(isMaterialEnabled(material)){
            this.materials.putIfAbsent(material.getIdentifier(), material);
        }
    }

    private void setMaterials(FMLPreInitializationEvent event){
        this.loadMaterialsFromJson(event.getModConfigurationDirectory(), modid);
        this.loadMaterialsFromSource();
        this.saveMaterialsToJson(event.getModConfigurationDirectory(), modid);
    }

    protected abstract void loadMaterialsFromSource();

    private void loadMaterials(TiCMaterial[] jsonMaterials){
        for(TiCMaterial m:jsonMaterials){
            this.materials.putIfAbsent(m.getIdentifier(), m);
        }
    }

    void loadMaterialsFromJson(InputStream path){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();

        TiCMaterial[] jsonMaterials = gson.fromJson(new BufferedReader(new InputStreamReader(path)), TiCMaterial[].class);
        this.loadMaterials(jsonMaterials);
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
        this.loadMaterials(jsonMaterials);
    }

    private void loadMaterialsFromJson(File configDir, String root, String modid){
        this.loadMaterialsFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void loadMaterialsFromJson(File configDir, String modid){
        this.loadMaterialsFromJson(configDir, "armoryexpansion", modid);
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
        this.saveMaterialsToJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void saveMaterialsToJson(File configDir, String modid){
        this.saveMaterialsToJson(configDir, "armoryexpansion", modid);
    }

    private void oredictMaterials() {
        this.materials.values().forEach(ITiCMaterial::registerOreDict);
    }

    private void registerMaterials() {
        this.materials.values().forEach(m -> {
            if(this.isMaterialEnabled(m)){
                if(m.registerTinkersMaterial()){
                    this.logger.info("Registered tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void registerMaterialFluids() {
        this.materials.values().forEach(m -> {
            if(this.isMaterialEnabled(m)){
                if(m.registerTinkersFluid()){
                    this.logger.info("Registered fluid for tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void registerMaterialStats() {
        this.materials.values().forEach(m -> {
            if(this.isMaterialEnabled(m)){
                if(m.registerTinkersMaterialStats(getProperties(m))){
                    this.logger.info("Registered stats for tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void updateMaterials() {
        this.materials.values().forEach(m -> {
            if(this.isMaterialEnabled(m)){
                if(m.updateTinkersMaterial()){
                    this.logger.info("Updated tinker's material {" + m.getIdentifier() + "};");
                }
            }
        });
    }

    private void registerMaterialTraits() {
        this.materials.values().forEach(m -> {
            if(this.isMaterialEnabled(m)){
                if(m.registerTinkersMaterialTraits()){
                    this.logger.info("Registered traits for tinker's material {" + m.getIdentifier() + "};");
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
            return this.getProperties(material).get(property);
        }
        return null;
    }

    public boolean isEnabled(ITiCMaterial material, String property){
        return (this.getProperty(material, property) != null) && Objects.requireNonNull(this.getProperty(material, property)).getBoolean();
    }

    private boolean isMaterialEnabled(ITiCMaterial material){
        Property property = getProperty(material, CATEGORY_MATERIAL);
        if (property != null){
            return (this.getProperty(material, CATEGORY_MATERIAL) != null) && Objects.requireNonNull(this.getProperty(material, CATEGORY_MATERIAL)).getBoolean();
        }
        return true;
    }

    private String returnMaterialExample(){
        return  "//  {\n" +
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
    }
}
