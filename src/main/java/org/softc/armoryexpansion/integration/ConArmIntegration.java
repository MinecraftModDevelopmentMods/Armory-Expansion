package org.softc.armoryexpansion.integration;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.aelib.WebIntegration;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.ITiCMaterial;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static c4.conarm.lib.materials.ArmorMaterialType.*;
import static org.softc.armoryexpansion.util.Math.clamp;
import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

@Mod(
        modid = ConArmIntegration.MODID,
        name = ConArmIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = ConArmIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class ConArmIntegration extends WebIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-" + ConstructsArmory.MODID;
    static final String NAME = ArmoryExpansion.NAME + " - " + ConstructsArmory.MODNAME;
    static final String DEPENDENCIES =
                    "required-after:" + ArmoryExpansion.MODID + "; " +
                    "after:*";

    private static final float STAT_MULT = 1.25f;
    private static final int DURA_MIN = 1;
    private static final int DURA_MAX = 120;
    private static final int DEF_MIN = 0;
    private static final int DEF_MAX = 50;
    private static final int TOUGH_MIN = DEF_MIN / 10;
    private static final int TOUGH_MAX = DEF_MAX / 10;

    private List<TiCMaterial> jsonMaterials = new LinkedList<>();

    public ConArmIntegration() {
        super(ConstructsArmory.MODID, "assets/" + ArmoryExpansion.MODID + "/data/" + ConstructsArmory.MODID);
    }

    private void loadMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        loadJsonMaterialsFromOtherIntegrations(event);
        loadWebMaterialsFromOtherIntegrations();
    }

    private void loadWebMaterialsFromOtherIntegrations(){
        for (String url:ArmoryExpansion.getWebServerList()) {
            try {
                for (String file:loadFileListFromServer(url)) {
                    if(!file.contains("conarm") && file.contains("-materials.json")){
                        InputStream stream = this.webClient.sendGet(url, file, "armory-expansion");
                        loadMaterialsFromOtherIntegration(stream);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String[] loadFileListFromServer(String url) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.webClient.sendGet(url, "getfiles")));
        String[] fileList = gson.fromJson(reader, String[].class);
        reader.close();

        this.logger.info("Retrieved file list from: " + url);
        return fileList;
    }

    private void loadJsonMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        for (File json : Objects.requireNonNull(
                new File(event.getModConfigurationDirectory().getPath() + "/" + ArmoryExpansion.MODID + "/")
                        .listFiles((dir, name) -> name.contains("-materials.json") && !name.contains(ConstructsArmory.MODID)))){
            loadMaterialsFromOtherIntegration(json);
        }
    }

    private void loadMaterialsFromOtherIntegration(InputStream stream){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        Collections.addAll(jsonMaterials, gson.fromJson(reader, TiCMaterial[].class));

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMaterialsFromOtherIntegration(File file){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        try {
            Collections.addAll(jsonMaterials, gson.fromJson(new FileReader(file), TiCMaterial[].class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = ConstructsArmory.MODID;
        this.logger = event.getModLog();
        Property property = ArmoryExpansion.config
                .get("integrations", modid, true, "Whether integration with " + modid + " should be enabled");
        if (property == null || property.getBoolean()){
            this.loadMaterialsFromOtherIntegrations(event);
            super.preInit(event);
        }
    }

    @Override
    protected void loadAlloysFromWeb() {
        // Left empty on purpose
        // No alloys should ever be generated
    }

    @Override
    protected void loadMaterialsFromSource() {
        for (Material material:TinkerRegistry.getAllMaterials())
        {
            if (this.isConversionAvailable(material)) {
                ITiCMaterial m = newTiCMaterial(material, TinkerMaterials.iron);
                //noinspection SuspiciousMethodCalls
                if (!jsonMaterials.contains(m)){
                    this.addMaterial(m);
                }
            }
        }
    }

    @Override
    protected void loadAlloysFromSource() {
        // Left empty on purpose
        // No alloys should ever be generated
    }

    private boolean isConversionAvailable(Material material){
        final boolean core = !material.hasStats(CORE) && material.hasStats(HEAD);
        final boolean plates = !material.hasStats(PLATES) && material.hasStats(HANDLE);
        final boolean trim = !material.hasStats(TRIM) && material.hasStats(EXTRA);
        return core || plates || trim;
    }

    private ITiCMaterial newTiCMaterial(Material material, Material baseMaterial){
        return new TiCMaterial(material.identifier, null, material.materialTextColor)
                .setArmorMaterial(true)
                .setDurability(calculateDurability(material, baseMaterial))
                .setMagicAffinity(calculateExtraDurability(material, baseMaterial))
                .setDefense(calculateDefense(material, baseMaterial))
                .setToughness(calculateToughness(material, baseMaterial));
    }

    private int calculateDurability(Material material, CoreMaterialStats core, HeadMaterialStats head){
        final HeadMaterialStats materialHead = material.getStats(HEAD);
        return materialHead != null ? (int)clamp(core.durability * materialHead.durability / head.durability / STAT_MULT, DURA_MIN, DURA_MAX): 0;
    }

    private int calculateDurability(Material material, Material baseMaterial){
        return calculateDurability(material, baseMaterial.getStats(CORE), baseMaterial.getStats(HEAD));
    }

    private float calculateDefense(Material material, CoreMaterialStats core, HeadMaterialStats head){
        final HeadMaterialStats materialHead = material.getStats(HEAD);
        return materialHead != null ? clamp(1.5f * core.defense * materialHead.attack / head.attack  / STAT_MULT, DEF_MIN,DEF_MAX) : 0;
    }

    private float calculateDefense(Material material, Material baseMaterial){
        return calculateDefense(material, baseMaterial.getStats(CORE), baseMaterial.getStats(HEAD));
    }

    private float calculateToughness(Material material, PlatesMaterialStats plates, HandleMaterialStats handle){
        final HandleMaterialStats materialHandle = material.getStats(HANDLE);
        return materialHandle != null ? clamp(3 * plates.toughness * materialHandle.durability / handle.durability / STAT_MULT, TOUGH_MIN, TOUGH_MAX) : 0;
    }

    private float calculateToughness(Material material, Material baseMaterial){
        return calculateToughness(material, baseMaterial.getStats(PLATES), baseMaterial.getStats(HANDLE));
    }

    private float calculateExtraDurability(Material material, TrimMaterialStats trim, ExtraMaterialStats extra){
        final ExtraMaterialStats materialExtra = material.getStats(EXTRA);
        return materialExtra != null ? 2 * trim.extraDurability * materialExtra.extraDurability / extra.extraDurability / STAT_MULT : 0;
    }

    private float calculateExtraDurability(Material material, Material baseMaterial){
        return calculateExtraDurability(material, baseMaterial.getStats(TRIM), baseMaterial.getStats(EXTRA));
    }
}
