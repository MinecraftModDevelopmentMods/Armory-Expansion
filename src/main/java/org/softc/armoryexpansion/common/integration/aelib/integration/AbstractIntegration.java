package org.softc.armoryexpansion.common.integration.aelib.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.input.BoundedInputStream;
import org.apache.logging.log4j.Logger;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.common.integration.aelib.config.IntegrationConfig;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.ArmorToolMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.ArmorToolRangedMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary.IOreDictionary;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.alloys.TiCAlloy;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractIntegration implements IIntegration {
    protected Logger logger;
    protected String modid = "";
    protected String root= "";
    protected String configDir;
    protected IntegrationConfig integrationConfigHelper = new IntegrationConfig();
    private boolean forceCreateJson = false;
    protected Map<String, IBasicMaterial> materials = new HashMap<>();
    private Map<String, IOreDictionary> oreDictionaryEntries = new HashMap<>();
    private Map<String, TiCAlloy> alloys = new HashMap<>();

    public AbstractIntegration() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // Forge Mod Loader events
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        this.configDir = event.getModConfigurationDirectory().getPath();
        if(ArmoryExpansion.isIntegrationEnabled(modid)){
            this.setIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterials();
//            this.registerMaterialFluids();
            this.registerAlloys();
            this.registerMaterialStats();
        }
        ArmoryExpansion.config.save();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        if(ArmoryExpansion.isIntegrationEnabled(modid)){
            this.oredictMaterials();
            this.registerMaterialFluidsIMC();
            this.updateMaterials();
            this.registerMaterialTraits();
        }
        ArmoryExpansion.config.save();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        // Used as a stub
    }

    @Override
    public void registerBlocks(RegistryEvent.Register<Block> event){
//        this.registerMaterialFluids();
//        this.registerFluidBlocks(event);
    }

    // Integration Data
    protected void setIntegrationData(String path){
        this.setConfig(path);
        this.setMaterials(path);
        this.setOreDictionaryEntries(path);
        this.setAlloys(path);
    }

    protected void saveIntegrationData(String path){
        this.saveConfig(path);
        this.saveMaterials(path);
        this.saveOreDictionaryEntries(path);
        this.saveAlloys(path);
    }

    // Materials
    protected void setMaterials(String path){
        this.loadMaterialsFromJson(new File(path), this.modid);
        this.logger.info("Done loading all materials from local JSON files");
        this.loadMaterialsFromSource();
        this.logger.info("Done loading all materials from source");
    }

    protected void addMaterial(IBasicMaterial material){
        if(isMaterialEnabled(material)){
            this.materials.putIfAbsent(material.getIdentifier(), material);
        }
    }

    private void saveMaterials(String path){
        this.saveMaterialsToJson(new File(path), this.modid, this.forceCreateJson);
        this.logger.info("Done saving all materials to local JSON files");
    }

    private void saveMaterialsToJson(String path, boolean forceCreate){
        if(materials.values().size() <= 0 && !forceCreate){
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        File output = new File(path);
        //noinspection ResultOfMethodCallIgnored
        output.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(gson.toJson(this.materials.values()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveMaterialsToJson(File configDir, String root, String modid, boolean forceCreate){
        this.saveMaterialsToJson(configDir.getPath() + "/" + root + "/" + modid + ".json", forceCreate);
    }

    private void saveMaterialsToJson(File configDir, String modid, boolean forceCreate){
        this.saveMaterialsToJson(configDir, this.root, modid + "-materials", forceCreate);
    }

    private void loadMaterials(ArmorToolMaterial[] jsonMaterials){
        if(jsonMaterials == null){
            return;
        }
        for(ArmorToolMaterial m:jsonMaterials){
            this.materials.putIfAbsent(m.getIdentifier(), m);
        }
    }

    void loadMaterialsFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        ArmorToolRangedMaterial[] jsonMaterials = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), ArmorToolRangedMaterial[].class);
        this.loadMaterials(jsonMaterials);
    }

    private void loadMaterialsFromJson(String path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        ArmorToolRangedMaterial[] jsonMaterials = new ArmorToolRangedMaterial[0];
        try {
            File input = new File(path);
            if(input.exists()){
                jsonMaterials = gson.fromJson(new FileReader(input), ArmorToolRangedMaterial[].class);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.loadMaterials(jsonMaterials);
    }

    private void loadMaterialsFromJson(File configDir, String root, String modid){
        this.loadMaterialsFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void loadMaterialsFromJson(File configDir, String modid){
        this.loadMaterialsFromJson(configDir, this.root, modid + "-materials");
    }

    protected abstract void loadMaterialsFromSource();

    // Ore Dictionary Entries
    private void setOreDictionaryEntries(String path){
        this.loadOreDictionaryEntriesFromJson(new File(path), this.modid);
        this.logger.info("Done loading all ore dictionary entries from local JSON files");
        this.loadOreDictionaryEntriesFromSource();
        this.logger.info("Done loading all ore dictionary entries from source");
    }

    private void addOreDictionaryEntry(IOreDictionary oreDictionary) {
        if(isMaterialEnabled(this.materials.get(oreDictionary.getIdentifier()))){
            this.oreDictionaryEntries.putIfAbsent(oreDictionary.getIdentifier(), oreDictionary);
        }
    }

    private void saveOreDictionaryEntries(String path) {
        this.saveOreDictionaryEntriesToJson(new File(path), this.modid, this.forceCreateJson);
        this.logger.info("Done saving all ore dictionary entries to local JSON files");
    }

    private void saveOreDictionaryEntriesToJson(String path, boolean forceCreate) {
        if(oreDictionaryEntries.values().size() <= 0 && !forceCreate){
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        File output = new File(path);
        //noinspection ResultOfMethodCallIgnored
        output.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(gson.toJson(this.oreDictionaryEntries.values()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveOreDictionaryEntriesToJson(File configDir, String root, String modid, boolean forceCreate) {
        this.saveOreDictionaryEntriesToJson(configDir.getPath() + "/" + root + "/" + modid + ".json", forceCreate);
    }

    private void saveOreDictionaryEntriesToJson(File configDir, String modid, boolean forceCreate) {
        this.saveOreDictionaryEntriesToJson(configDir, this.root, modid + "-oreDictEntries", forceCreate);
    }

    private void loadOreDictionaryEntries(IOreDictionary[] jsonMaterials) {
        if(jsonMaterials == null){
            return;
        }
        for(IOreDictionary iOreDictionary:jsonMaterials){
            this.oreDictionaryEntries.putIfAbsent(iOreDictionary.getIdentifier(), iOreDictionary);
        }
    }

    void loadOreDictionaryEntriesFromJson(InputStream path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        IOreDictionary[] jsonMaterials = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), IOreDictionary[].class);
        this.loadOreDictionaryEntries(jsonMaterials);
    }

    private void loadOreDictionaryEntriesFromJson(String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        IOreDictionary[] jsonMaterials = new IOreDictionary[0];
        try {
            File input = new File(path);
            if(input.exists()){
                jsonMaterials = gson.fromJson(new FileReader(input), IOreDictionary[].class);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.loadOreDictionaryEntries(jsonMaterials);
    }

    private void loadOreDictionaryEntriesFromJson(File configDir, String root, String modid) {
        this.loadOreDictionaryEntriesFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void loadOreDictionaryEntriesFromJson(File configDir, String modid) {
        this.loadOreDictionaryEntriesFromJson(configDir, this.root, modid + "-oreDictEntries");
    }

    protected abstract void loadOreDictionaryEntriesFromSource();

    // Alloys
    private void setAlloys(String path){
        this.loadAlloysFromJson(new File(path), this.modid);
        this.logger.info("Done loading all alloys from local JSON files");
        this.loadAlloysFromSource();
        this.logger.info("Done loading all alloys from source");
    }

    private void saveAlloys(String path){
        this.saveAlloysToJson(new File(path), this.modid, this.forceCreateJson);
        this.logger.info("Done saving all alloys to local JSON files");
    }

    private void saveAlloysToJson(String path, boolean forceCreate){
        if(alloys.values().size() <= 0 && !forceCreate){
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        File output = new File(path);
        //noinspection ResultOfMethodCallIgnored
        output.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(returnAlloyExample());
            writer.write(gson.toJson(this.alloys.values()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAlloysToJson(File configDir, String root, String modid, boolean forceCreate){
        this.saveAlloysToJson(configDir.getPath() + "/" + root + "/" + modid + ".json", forceCreate);
    }

    private void saveAlloysToJson(File configDir, String modid, boolean forceCreate){
        this.saveAlloysToJson(configDir, this.root, modid + "-alloys", forceCreate);
    }

    private void loadAlloys(TiCAlloy[] jsonAlloys){
        if(jsonAlloys == null){
            return;
        }
        for(TiCAlloy a:jsonAlloys){
            this.alloys.putIfAbsent(a.getName(), a);
        }
    }

    void loadAlloysFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        TiCAlloy[] jsonAlloys = new TiCAlloy[0];
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize())));
            jsonAlloys = gson.fromJson(reader, TiCAlloy[].class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.loadAlloys(jsonAlloys);
    }

    private void loadAlloysFromJson(String path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        TiCAlloy[] jsonAlloys = new TiCAlloy[0];
        try {
            File input = new File(path);
            if(input.exists()){
                jsonAlloys = gson.fromJson(new FileReader(input), TiCAlloy[].class);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loadAlloys(jsonAlloys);
    }

    private void loadAlloysFromJson(File configDir, String root, String modid){
        this.loadAlloysFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void loadAlloysFromJson(File configDir, String modid){
        this.loadAlloysFromJson(configDir, this.root, modid + "-alloys");
    }

    protected abstract void loadAlloysFromSource();

    // Config
    protected void setConfig(String path){
        this.loadConfigFromJson(new File(path), this.modid);
        this.logger.info("Done loading config from local JSON file");
        this.loadConfigFromSource();
        this.logger.info("Done loading config from source");
    }

    private void saveConfig(String path){
        this.saveConfigToJson(new File(path), this.modid, this.forceCreateJson);
        this.logger.info("Done saving config to local JSON file");
    }

    private void saveConfigToJson(String path, boolean forceCreate){
        if(materials.values().size() <= 0 && !forceCreate){
            return;
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        File output = new File(path);
        //noinspection ResultOfMethodCallIgnored
        output.getParentFile().mkdirs();
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(gson.toJson(this.integrationConfigHelper.getIntegrationMaterials().values().toArray()));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfigToJson(File configDir, String root, String modid, boolean forceCreate){
        this.saveConfigToJson(configDir.getPath() + "/" + root + "/" + modid + ".json", forceCreate);
    }

    private void saveConfigToJson(File configDir, String modid, boolean forceCreate){
        this.saveConfigToJson(configDir, this.root, modid + "-config", forceCreate);
    }

    private void loadConfig(MaterialConfigOptions[] materialConfigOptions){
        if(materialConfigOptions != null){
            if (this.integrationConfigHelper == null){
                this.integrationConfigHelper = new IntegrationConfig();
            }
            for(MaterialConfigOptions material : materialConfigOptions){
                this.integrationConfigHelper.insertMaterialConfigOptions(material);
            }
        }
    }

    void loadConfigFromJson(InputStream path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        MaterialConfigOptions[] jsonIntegrationConfig = gson.fromJson(
                new BufferedReader(
                        new InputStreamReader(
                                new BoundedInputStream(path, ArmoryExpansion.getBoundedInputStreamMaxSize()))), MaterialConfigOptions[].class);
        this.loadConfig(jsonIntegrationConfig);
    }

    private void loadConfigFromJson(String path){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

        MaterialConfigOptions[] jsonIntegrationConfig = new MaterialConfigOptions[0];
        try {
            File input = new File(path);
            if(input.exists()){
                jsonIntegrationConfig = gson.fromJson(new FileReader(input), MaterialConfigOptions[].class);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.loadConfig(jsonIntegrationConfig);
    }

    private void loadConfigFromJson(File configDir, String root, String modid){
        this.loadConfigFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private void loadConfigFromJson(File configDir, String modid){
        this.loadConfigFromJson(configDir, this.root, modid + "-config");
    }

    protected abstract void loadConfigFromSource();

    // IIntegration implementations
    @Override
    public void oredictMaterials() {
        this.materials.values().forEach(m -> {
            if (this.isMaterialEnabled(m)){
                this.oreDictionaryEntries.get(m.getIdentifier()).registerOreDict();
                this.logger.info("Oredicted material {" + m.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterials() {
        this.materials.values().forEach(m -> {
            if (m.registerTinkersMaterial(this.isMaterialEnabled(m))) {
                this.logger.info("Registered tinker's material {" + m.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterialFluids() {
        this.materials.values().forEach(m -> {
            if (m.registerTinkersFluid(this.isMaterialEnabled(m) && this.isMaterialFluidEnabled(m))) {
                this.logger.info("Registered fluid for material {" + m.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterialFluidsIMC(){
        this.materials.values().forEach(m -> {
            if (m.registerTinkersFluidIMC(this.isMaterialEnabled(m) && this.isMaterialFluidEnabled(m))) {
                this.logger.info("Sent IMC for tinker's fluid {" + m.getFluidName() + "};");
            }
        });
    }

    @Override
    public void registerFluidBlocks(RegistryEvent.Register<Block> event){
        this.materials.values().forEach(m -> {
            if(m.isCastable()){
                // TODO Fix this!!
//                event.getRegistry().registerAll(m.getFluidBlock());
//                this.logger.info("Registered fluid block for material {" + m.getIdentifier() + "};");
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
        this.materials.values().forEach(m -> {
            if (m.registerTinkersMaterialStats(this.getProperties(m))) {
                this.logger.info("Registered stats for tinker's material {" + m.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void updateMaterials() {
        this.materials.values().forEach(m -> {
            if (this.oreDictionaryEntries.get(m.getIdentifier()).updateTinkersMaterial(this.isMaterialEnabled(m))) {
                this.logger.info("Updated tinker's material {" + m.getIdentifier() + "};");
            }
        });
    }

    @Override
    public void registerMaterialTraits() {
        this.materials.values().forEach(m -> {
            if (m.registerTinkersMaterialTraits(this.isMaterialEnabled(m) && this.integrationConfigHelper.isTraitsEnabled(m))) {
                this.logger.info("Registered traits for tinker's material {" + m.getIdentifier() + "};");
            }
        });
    }

    @Override
    public boolean isMaterialEnabled(IBasicMaterial material){
        return this.integrationConfigHelper.isMaterialEnabled(material);
    }

    @Override
    public boolean isMaterialFluidEnabled(IBasicMaterial material){
        return this.integrationConfigHelper.isFluidEnabled(material);
    }

    @Override
    public void enableForceJsonCreation(){
        this.forceCreateJson = true;
    }

    // Helpers
    private MaterialConfigOptions getProperties(IBasicMaterial m) {
        return this.integrationConfigHelper.getSafeMaterialConfigOptions(m.getIdentifier());
    }

    private String returnAlloyExample() {
        return "//  {\n" +
                "//    \"output\": {\n" +
                "//      \"fluid\": \"iron\",\n" +
                "//      \"amount\": 144\n" +
                "//    },\n" +
                "//    \"inputs\": [\n" +
                "//      {\n" +
                "//        \"fluid\": \"copper\",\n" +
                "//        \"amount\": 108\n" +
                "//      },\n" +
                "//      {\n" +
                "//        \"fluid\": \"lead\",\n" +
                "//        \"amount\": 36\n" +
                "//      }\n" +
                "//    ]\n" +
                "//  }\n";
    }
}
