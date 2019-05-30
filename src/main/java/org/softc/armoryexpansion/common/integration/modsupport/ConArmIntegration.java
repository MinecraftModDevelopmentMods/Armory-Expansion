package org.softc.armoryexpansion.common.integration.modsupport;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.integration.JsonIntegration;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.ArmorMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.IArmorMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static c4.conarm.lib.materials.ArmorMaterialType.*;
import static org.softc.armoryexpansion.common.util.Math.clamp;
import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

@Mod(
        modid = ConArmIntegration.MODID,
        name = ConArmIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = ConArmIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class ConArmIntegration extends JsonIntegration {
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

    private List<ArmorMaterial> jsonMaterials = new LinkedList<>();

    public ConArmIntegration() {
        super(ConstructsArmory.MODID, ArmoryExpansion.MODID, ConstructsArmory.MODID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = ConstructsArmory.MODID;
        this.logger = event.getModLog();
        this.configDir = event.getModConfigurationDirectory().getPath();
        if (ArmoryExpansion.isIntegrationEnabled(modid)){
            this.loadMaterialsFromOtherIntegrations(event);
            this.setIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterials();
            this.registerAlloys();
            this.registerMaterialStats();
        }
        ArmoryExpansion.config.save();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void registerItems(RegistryEvent<Item> event){
        if(ArmoryExpansion.isIntegrationEnabled(modid)){
            this.setIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterials();
            this.registerAlloys();
            this.registerMaterialStats();
        }
        ArmoryExpansion.config.save();
    }

    private void loadMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        loadJsonMaterialsFromOtherIntegrations(event);
    }

    private void loadJsonMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        for (File json : Objects.requireNonNull(
                new File(event.getModConfigurationDirectory().getPath() + "/" + ArmoryExpansion.MODID + "/")
                        .listFiles((dir, name) -> name.contains("-materials.json") && !name.contains(ConstructsArmory.MODID)))){
            loadMaterialsFromOtherIntegration(json);
        }
    }

    private void loadMaterialsFromOtherIntegration(File file){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        try {
            Collections.addAll(jsonMaterials, gson.fromJson(new FileReader(file), ArmorMaterial[].class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void loadMaterialsFromSource() {
        for (Material material:TinkerRegistry.getAllMaterials())
        {
            if (this.isConversionAvailable(material)) {
                IArmorMaterial m = newTiCMaterial(material, TinkerMaterials.iron);
                //noinspection SuspiciousMethodCalls
                if (!jsonMaterials.contains(m)){
                    this.addMaterial((IBasicMaterial) m);
                }
            }
        }
    }

    @Override
    protected void loadAlloysFromSource() {
        // Left empty on purpose
    }

    private boolean isConversionAvailable(Material material){
        final boolean core = !material.hasStats(CORE) && material.hasStats(HEAD);
        final boolean plates = !material.hasStats(PLATES) && material.hasStats(HANDLE);
        final boolean trim = !material.hasStats(TRIM) && material.hasStats(EXTRA);
        return core || plates || trim;
    }

    private IArmorMaterial newTiCMaterial(Material material, Material baseMaterial){
        ArmorMaterial armorMaterial = new ArmorMaterial(material.identifier, material.materialTextColor, MaterialRenderType.METAL,
                getCoreMaterialStats(material, baseMaterial),
                getPlatesMaterialStats(material, baseMaterial),
                getTrimMaterialStats(material, baseMaterial)
        );
        armorMaterial.setCastable(material.isCastable());
        armorMaterial.setCraftable(material.isCraftable());

        return armorMaterial;
    }

    private CoreMaterialStats getCoreMaterialStats(Material material, Material baseMaterial){
        return new CoreMaterialStats(
                calculateDurability(material, baseMaterial),
                calculateDefense(material, baseMaterial)
        );
    }

    private PlatesMaterialStats getPlatesMaterialStats(Material material, Material baseMaterial){
        return new PlatesMaterialStats(
                calculateDefense(material, baseMaterial),
                calculateExtraDurability(material, baseMaterial),
                calculateToughness(material, baseMaterial)
        );
    }

    private TrimMaterialStats getTrimMaterialStats(Material material, Material baseMaterial){
        return new TrimMaterialStats(
                calculateExtraDurability(material, baseMaterial)
        );
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
