package org.softc.armoryexpansion.common.integration.aelib.integration;

import com.google.gson.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.apache.logging.log4j.*;
import org.softc.armoryexpansion.*;
import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.oredictionary.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.alloys.*;
import org.softc.armoryexpansion.common.util.*;

import java.io.*;
import java.util.*;

public abstract class AbstractIntegration implements IIntegration {
    protected Logger logger;
    protected String modId = "";
    protected String root= "";
    protected String configDir;
    protected IntegrationConfig integrationConfigHelper = new IntegrationConfig();

    protected Map<String, IBasicMaterial> materials = new HashMap<>();
    protected Map<String, MaterialTraits> materialTraits = new HashMap<>();
    protected Map<String, IOreDictionary> oreDictionaryEntries = new HashMap<>();
    protected Map<String, IAlloy> alloys = new HashMap<>();

    protected AbstractIntegration() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected AbstractIntegration(String modId, String root) {
        this();
        this.modId = modId;
        this.root = root;
    }

    @Override
    public boolean isLoadable() {
        return Loader.isModLoaded(this.modId) && ArmoryExpansion.isIntegrationEnabled(this.modId);
    }

    // Forge Mod Loader events
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        this.configDir = event.getModConfigurationDirectory().getPath();
        if(this.isLoadable()){
            this.loadIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterials();
            this.registerMaterialFluids();
        }
        ArmoryExpansion.getConfig().save();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        if(this.isLoadable()){
            this.oreDictMaterials();
            this.updateMaterials();
            this.registerMaterialTraits();
        }
        ArmoryExpansion.getConfig().save();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        // Used as a stub
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void registerItems(RegistryEvent<Item> event){
        if(this.isLoadable()){
            this.registerMaterialStats();
//            this.registerMaterialFluids();
            this.registerMaterialFluidsIMC();
            this.registerAlloys();
        }
        ArmoryExpansion.getConfig().save();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    @Override
    public void registerBlocks(RegistryEvent.Register<? super Block> event){
        if(ArmoryExpansion.isIntegrationEnabled(this.modId)) {
//            this.registerMaterialFluids();
            this.registerFluidBlocks(event);
        }
    }

    // Integration Data
    protected void loadIntegrationData(String path){
        this.loadConfig(path);
        this.loadMaterials(path);
        this.loadTraits(path);
        this.loadOreDictionaryEntries(path);
        this.loadAlloys(path);
    }

    protected void saveIntegrationData(String path){
        this.saveConfig(path);
        this.saveMaterials(path);
        this.saveTraits(path);
        this.saveOreDictionaryEntries(path);
        this.saveAlloys(path);
    }

    // Traits
    protected void loadTraits(String path) {
        this.loadTraitsFromJson(new File(path), this.modId);
        this.logger.info("Done loading all material traits from local JSON files");
        this.loadTraitsFromSource();
        this.logger.info("Done loading all material traits from source");
    }

    protected void saveTraits(String path){
        this.saveTraitsToJson(new File(path), this.modId, this.enableForceJsonCreation());
        this.logger.info("Done saving all material traits to local JSON files");
    }

    protected void saveTraitsToJson(File dir, String fileName, boolean forceCreate){
        if(!this.materialTraits.values().isEmpty() || forceCreate) {
            Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
            File output = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.TRAITS_SUFFIX));
            output.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(output)){
                writer.write(gson.toJson(this.materialTraits.values()));
            } catch (IOException e) {
                this.logger.error("", e);
            }
        }
    }

    protected void loadTraits(MaterialTraits[] jsonMaterials){
        if(null != jsonMaterials) {
            for (MaterialTraits material : jsonMaterials) {
                this.materialTraits.putIfAbsent(material.getIdentifier(), material);
            }
        }
    }

    protected void loadTraitsFromJson(File dir, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        MaterialTraits[] jsonMaterials = new MaterialTraits[0];
        try {
            File input = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.TRAITS_SUFFIX));
            if(input.exists()){
                jsonMaterials = gson.fromJson(new FileReader(input), MaterialTraits[].class);
            }
        } catch (FileNotFoundException e) {
            this.logger.error("", e);
        }
        this.loadTraits(jsonMaterials);
    }

    protected abstract void loadTraitsFromSource();

    // Materials
    protected void loadMaterials(String path){
        this.loadMaterialsFromJson(new File(path), this.modId);
        this.logger.info("Done loading all materials from local JSON files");
        this.loadMaterialsFromSource();
        this.logger.info("Done loading all materials from source");
    }

    protected void saveMaterials(String path){
        this.saveMaterialsToJson(new File(path), this.modId, this.enableForceJsonCreation());
        this.logger.info("Done saving all materials to local JSON files");
    }

    protected void saveMaterialsToJson(File dir, String fileName, boolean forceCreate){
        if(!this.materials.values().isEmpty() || forceCreate) {
            Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
            File output = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.MATERIALS_SUFFIX));
            output.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(output)){
                writer.write(gson.toJson(this.materials.values()));
            } catch (IOException e) {
                this.logger.error("", e);
            }
        }
    }

    protected void loadMaterials(ArmorToolMaterial[] jsonMaterials){
        if(null != jsonMaterials) {
            for (ArmorToolMaterial material : jsonMaterials) {
                this.materials.putIfAbsent(material.getIdentifier(), material);
            }
        }
    }

    protected void loadMaterialsFromJson(File dir, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        ArmorToolRangedMaterial[] jsonMaterials = new ArmorToolRangedMaterial[0];
        try {
            File input = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.MATERIALS_SUFFIX));
            if(input.exists()){
                jsonMaterials = gson.fromJson(new FileReader(input), ArmorToolRangedMaterial[].class);
            }
        } catch (FileNotFoundException e) {
            this.logger.error("", e);
        }
        this.loadMaterials(jsonMaterials);
    }

    protected abstract void loadMaterialsFromSource();

    // Ore Dictionary Entries
    protected void loadOreDictionaryEntries(String path){
        this.loadOreDictionaryEntriesFromJson(new File(path), this.modId);
        this.logger.info("Done loading all ore dictionary entries from local JSON files");
        this.loadOreDictionaryEntriesFromSource();
        this.logger.info("Done loading all ore dictionary entries from source");
    }

    protected void saveOreDictionaryEntries(String path) {
        this.saveOreDictionaryEntriesToJson(new File(path), this.modId, this.enableForceJsonCreation());
        this.logger.info("Done saving all ore dictionary entries to local JSON files");
    }

    protected void saveOreDictionaryEntriesToJson(File dir, String fileName, boolean forceCreate) {
        if(!this.oreDictionaryEntries.values().isEmpty() || forceCreate) {
            Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
            File output = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.ORE_DICT_ENTRIES_SUFFIX));
            output.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(output)) {
                writer.write(gson.toJson(this.oreDictionaryEntries.values()));
            } catch (IOException e) {
                this.logger.error("", e);
            }
        }
    }

    protected void loadOreDictionaryEntries(IOreDictionary[] jsonOreDictionaries) {
        if(null != jsonOreDictionaries) {
            for (IOreDictionary iOreDictionary : jsonOreDictionaries) {
                this.oreDictionaryEntries.putIfAbsent(iOreDictionary.getIdentifier(), iOreDictionary);
            }
        }
    }

    protected void loadOreDictionaryEntriesFromJson(File dir, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        IOreDictionary[] jsonMaterials = new BasicOreDictionary[0];
        try {
            File input = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.ORE_DICT_ENTRIES_SUFFIX));
            if(input.exists()){
                jsonMaterials = gson.fromJson(new FileReader(input), BasicOreDictionary[].class);
            }
        } catch (FileNotFoundException e) {
            this.logger.error("", e);
        }
        this.loadOreDictionaryEntries(jsonMaterials);
    }

    protected abstract void loadOreDictionaryEntriesFromSource();

    // Alloys
    protected void loadAlloys(String path){
        this.loadAlloysFromJson(new File(path), this.modId);
        this.logger.info("Done loading all alloys from local JSON files");
        this.loadAlloysFromSource();
        this.logger.info("Done loading all alloys from source");
    }

    protected void saveAlloys(String path){
        this.saveAlloysToJson(new File(path), this.modId, this.enableForceJsonCreation());
        this.logger.info("Done saving all alloys to local JSON files");
    }

    protected void saveAlloysToJson(File dir, String fileName, boolean forceCreate){
        if(!this.alloys.values().isEmpty() || forceCreate) {
            Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
            File output = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.ALLOYS_SUFFIX));
            output.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(output)) {
                writer.write(gson.toJson(this.alloys.values()));
            } catch (IOException e) {
                this.logger.error("", e);
            }
        }
    }

    protected void loadAlloys(Alloy[] jsonAlloys){
        if(null != jsonAlloys) {
            for (Alloy a : jsonAlloys) {
                this.alloys.putIfAbsent(a.getName(), a);
            }
        }
    }

    protected void loadAlloysFromJson(File dir, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        Alloy[] jsonAlloys = new Alloy[0];
        try {
            File input = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.ALLOYS_SUFFIX));
            if(input.exists()){
                jsonAlloys = gson.fromJson(new FileReader(input), Alloy[].class);
            }
        } catch (FileNotFoundException e) {
            this.logger.error("", e);
        }
        this.loadAlloys(jsonAlloys);
    }

    protected abstract void loadAlloysFromSource();

    // Config
    protected void loadConfig(String path){
        this.loadConfigFromJson(new File(path), this.modId);
        this.logger.info("Done loading config from local JSON file");
        this.loadConfigFromSource();
        this.logger.info("Done loading config from source");
    }

    protected void saveConfig(String path){
        this.saveConfigToJson(new File(path), this.modId, this.enableForceJsonCreation());
        this.logger.info("Done saving config to local JSON file");
    }

    protected void saveConfigToJson(File dir, String fileName, boolean forceCreate){
        if(!this.materials.values().isEmpty() || forceCreate) {
            Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
            File output = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.CONFIG_SUFFIX));
            output.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(output)) {
                writer.write(gson.toJson(this.integrationConfigHelper.getIntegrationMaterials().values().toArray()));
            } catch (IOException e) {
                this.logger.error("", e);
            }
        }
    }

    protected void loadConfig(MaterialConfigOptions[] materialConfig){
        if(null != materialConfig){
            if (null == this.integrationConfigHelper){
                this.integrationConfigHelper = new IntegrationConfig();
            }
            for(MaterialConfigOptions material : materialConfig){
                this.integrationConfigHelper.insertMaterialConfigOptions(material);
            }
        }
    }

    protected void loadConfigFromJson(File dir, String fileName){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        MaterialConfigOptions[] jsonConfig = new MaterialConfigOptions[0];
        try {
            File input = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.CONFIG_SUFFIX));
            if(input.exists()){
                jsonConfig = gson.fromJson(new FileReader(input), MaterialConfigOptions[].class);
            }
        } catch (FileNotFoundException e) {
            this.logger.error("", e);
        }
        this.loadConfig(jsonConfig);
    }

    protected abstract void loadConfigFromSource();

    // IIntegration implementations
    @Override
    public void oreDictMaterials() {
        this.materials.values().forEach(material -> {
            if (this.isMaterialEnabled(material.getIdentifier())){
                IOreDictionary oreDictionary = this.oreDictionaryEntries.get(material.getIdentifier());
                if (null != oreDictionary)
                    oreDictionary.registerOreDict();
                this.logger.info("Added material to Ore Dictionary {" + material.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterials() {
        this.materials.values().forEach(material -> {
            if (material.registerTinkersMaterial(this.isMaterialEnabled(material.getIdentifier()))) {
                this.logger.info("Registered tinker's material {" + material.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterialFluids() {
        this.materials.values().forEach(material -> {
            if (material.registerTinkersFluid(this.isMaterialEnabled(material.getIdentifier()) && this.isMaterialFluidEnabled(material.getIdentifier()))) {
                this.logger.info("Registered fluid for material {" + material.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterialFluidsIMC(){
        this.materials.values().forEach(material -> {
            if (material.registerTinkersFluidIMC(this.isMaterialEnabled(material.getIdentifier()) && this.isMaterialFluidEnabled(material.getIdentifier()))) {
                this.logger.info("Sent IMC for tinker's fluid {" + material.getFluidName() + "};");
            }
        });
    }

    @Override
    public void registerFluidBlocks(RegistryEvent.Register<? super Block> event){
        this.materials.values().forEach(material -> {
            if(material.isCastable()){
                // TODO Fix this!!
                event.getRegistry().register(material.getFluidBlock());
                this.logger.info("Registered fluid block for material {" + material.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerAlloys(){
        this.alloys.values().forEach(a -> {
            a.registerTiCAlloy();
            this.logger.info("Sent IMC for tinker's alloy {" + a.getName() + "};");
        });
    }

    @Override
    public void registerMaterialStats() {
        this.materials.values().forEach(material -> {
            if (material.registerTinkersMaterialStats(this.getProperties(material))) {
                this.logger.info("Registered stats for tinker's material {" + material.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void updateMaterials() {
        this.materials.values().forEach(material -> {
            IOreDictionary oreDictionaryEntry = this.oreDictionaryEntries.get(material.getIdentifier());
            if (null != oreDictionaryEntry && oreDictionaryEntry.updateTinkersMaterial(this.isMaterialEnabled(material.getIdentifier()))) {
                this.logger.info("Updated tinker's material {" + material.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterialTraits() {
        this.materialTraits.values().forEach(traits -> {
            if (traits.registerTinkersMaterialTraits(this.isMaterialEnabled(traits.getIdentifier()) && this.integrationConfigHelper.traitsEnabled(traits.getIdentifier()))) {
                this.logger.info("Registered traits for tinker's traits {" + traits.getIdentifier() + "};");
            }
        });
    }

    // Helpers
    @Override
    public boolean isMaterialEnabled(String material){
        return this.integrationConfigHelper.materialEnabled(material);
    }

    @Override
    public boolean isMaterialFluidEnabled(String material){
        return this.integrationConfigHelper.fluidEnabled(material);
    }
    protected MaterialConfigOptions getProperties(IBasicMaterial material) {
        return this.integrationConfigHelper.getSafeMaterialConfigOptions(material.getIdentifier());
    }

    protected String getFilePath(File dir, String fileName, ConfigFileSuffixEnum suffix) {
        return dir.getPath()+ "/" + this.root + "/" + fileName + "/" + fileName + suffix.getSuffix() + ".json";
    }

    protected boolean enableForceJsonCreation() {
        return false;
    }
}
