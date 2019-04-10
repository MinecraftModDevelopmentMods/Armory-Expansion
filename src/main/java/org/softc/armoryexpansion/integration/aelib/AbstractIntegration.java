package org.softc.armoryexpansion.integration.aelib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.softc.armoryexpansion.integration.aelib.Config.CATEGORY_MATERIAL;

public abstract class AbstractIntegration implements IIntegration {
    protected Logger logger;
    protected String modid = "";
    private Config configHelper;
    private boolean isEnabled = false;
    private Map<String, TiCMaterial> materials = new HashMap<>();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        Property property = ArmoryExpansion.config
                .get("integrations", modid, true, "Whether integration with " + modid + " should be enabled");
        isEnabled = property == null || property.getBoolean();
        ArmoryExpansion.config.save();
        if(isEnabled){
            configHelper = new Config(new Configuration(new File(event.getModConfigurationDirectory().getPath() + "\\" + ArmoryExpansion.MODID + "\\" + modid + ".cfg")));
//            configHelper = new Config(new Configuration(event.getSuggestedConfigurationFile()));
            setMaterials(event);
            configHelper.syncConfig(materials);
            registerMaterials();
            registerMaterialStats();
        }
    }

    @Override
    public void init(FMLInitializationEvent event) {
        if(isEnabled){
            oredictMaterials();
            updateMaterials();
            registerMaterialTraits();
        }
    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event) {
        if(isEnabled){
            // TODO Write better documentation
            // Should be overrided as needed
        }
    }

    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        if(isEnabled){
            // TODO Write better documentation
            // Should be overrided as needed
        }
    }

    public Configuration getConfiguration() {
        return configHelper.getConfiguration();
    }

    protected Config getConfigHelper() {
        return configHelper;
    }

    protected void addMaterial(TiCMaterial material){
        if(isMaterialEnabled(material)){
            this.materials.putIfAbsent(material.getIdentifier(), material);
        }
    }

    protected void setMaterials(FMLPreInitializationEvent event){
        loadMaterialsFromJson(event.getModConfigurationDirectory(), modid);
        loadMaterialsFromSource();
        saveMaterialsToJson(event.getModConfigurationDirectory(), modid);
    }

    protected abstract void loadMaterialsFromSource();

    protected void loadMaterialsFromJson(File configDir, String root, String modid){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();

        TiCMaterial[] jsonMaterials = new TiCMaterial[0];
        try {
            File input = new File(configDir.getPath() + "\\" + root + "\\" + modid + ".json");
            jsonMaterials = gson.fromJson(new FileReader(input), TiCMaterial[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(TiCMaterial m:jsonMaterials){
            materials.put(m.getIdentifier(), m);
        }
    }

    private void loadMaterialsFromJson(File configDir, String modid){
        loadMaterialsFromJson(configDir, "armoryexpansion", modid);
    }

    protected void saveMaterialsToJson(File configDir, String root, String modid){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();

        if(configDir.exists()){
            File output = new File(configDir.getPath() + "\\" + root + "\\" + modid + ".json");
            output.getParentFile().mkdirs();
            try {
                FileWriter writer = new FileWriter(output);
                writer.write(gson.toJson(materials.values()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveMaterialsToJson(File configDir, String modid){
        saveMaterialsToJson(configDir, "armoryexpansion", modid);
    }

    private void oredictMaterials() {
        materials.values().forEach(m -> m.registerOreDict());
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

    private Map<String, Property> getProperties(TiCMaterial material){
        return this.getConfigHelper().getProperties(material.getIdentifier());
    }

    private Property getProperty(TiCMaterial material, String property){
        Map<String, Property> properties = getProperties(material);
        if(properties != null && properties.containsKey(property)){
            return getProperties(material).get(property);
        }
        return null;
    }

    public boolean isEnabled(TiCMaterial material, String property){
        return getProperty(material, property).getBoolean();
    }

    private boolean isMaterialEnabled(TiCMaterial material){
        Property property = getProperty(material, CATEGORY_MATERIAL);
        if (property != null){
            return getProperty(material, CATEGORY_MATERIAL).getBoolean();
        }
        return true;
    }
}
