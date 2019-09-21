package org.softc.armoryexpansion.common.integration.modsupport;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.integration.JsonIntegration;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructsarmory.material.ArmorMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructsarmory.material.IArmorMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;
import org.softc.armoryexpansion.common.util.Math;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

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

    private Collection<ArmorMaterial> jsonMaterials = new LinkedList<>();

    public ConArmIntegration() {
        super(ConstructsArmory.MODID, ArmoryExpansion.MODID, ConstructsArmory.MODID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = ConstructsArmory.MODID;
        this.logger = event.getModLog();
        this.configDir = event.getModConfigurationDirectory().getPath();
        if (ArmoryExpansion.isIntegrationEnabled(this.modId)){
            this.loadMaterialsFromOtherIntegrations(event);
            this.loadIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterialStats();
        }
        ArmoryExpansion.getConfig().save();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void registerItems(RegistryEvent<Item> event){
        if(ArmoryExpansion.isIntegrationEnabled(this.modId)){
            this.loadIntegrationData(this.configDir);
            this.integrationConfigHelper.syncConfig(this.materials);
            this.saveIntegrationData(this.configDir);
            this.registerMaterialStats();
        }
        ArmoryExpansion.getConfig().save();
    }

    private void loadMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        this.loadJsonMaterialsFromOtherIntegrations(event);
    }

    private void loadJsonMaterialsFromOtherIntegrations(FMLPreInitializationEvent event){
        File jsonDir = new File(event.getModConfigurationDirectory().getPath() + "/" + ArmoryExpansion.MODID + "/");
        //noinspection ResultOfMethodCallIgnored
        jsonDir.mkdirs();
        for (File json : Objects.requireNonNull(
                jsonDir.listFiles((dir, name) -> name.contains("-materials.json") && !name.contains(ConstructsArmory.MODID)))){
            this.loadMaterialsFromOtherIntegration(json);
        }
    }

    private void loadMaterialsFromOtherIntegration(File file){
        Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        try {
            Collections.addAll(this.jsonMaterials, gson.fromJson(new FileReader(file), ArmorMaterial[].class));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addMaterial(IBasicMaterial material){
        if(this.isMaterialEnabled(material.getIdentifier())){
            this.materials.putIfAbsent(material.getIdentifier(), material);
        }
    }

    @Override
    protected void loadMaterialsFromSource() {
        TinkerRegistry.getAllMaterials().stream().filter(this::isConversionAvailable)
                .map(material -> this.newTiCMaterial(material, TinkerMaterials.iron)).filter(material -> !this.jsonMaterials.contains(material))
                .forEach(material -> this.addMaterial((IBasicMaterial) material));
    }

    private boolean isConversionAvailable(Material material){
        boolean core = !material.hasStats(ArmorMaterialType.CORE) && material.hasStats(MaterialTypes.HEAD);
        boolean plates = !material.hasStats(ArmorMaterialType.PLATES) && material.hasStats(MaterialTypes.HANDLE);
        boolean trim = !material.hasStats(ArmorMaterialType.TRIM) && material.hasStats(MaterialTypes.EXTRA);
        return core || plates || trim;
    }

    private IArmorMaterial newTiCMaterial(Material material, Material baseMaterial){
        ArmorMaterial armorMaterial = new ArmorMaterial(material.identifier, material.materialTextColor, MaterialRenderType.METAL,
                this.getCoreMaterialStats(material, baseMaterial),
                this.getPlatesMaterialStats(material, baseMaterial),
                this.getTrimMaterialStats(material, baseMaterial)
        );
        armorMaterial.setCastable(material.isCastable());
        armorMaterial.setCraftable(material.isCraftable());

        return armorMaterial;
    }

    private CoreMaterialStats getCoreMaterialStats(Material material, Material baseMaterial){
        return new CoreMaterialStats(
                this.calculateDurability(material, baseMaterial),
                this.calculateDefense(material, baseMaterial)
        );
    }

    private PlatesMaterialStats getPlatesMaterialStats(Material material, Material baseMaterial){
        return new PlatesMaterialStats(
                this.calculateDefense(material, baseMaterial),
                this.calculateExtraDurability(material, baseMaterial),
                this.calculateToughness(material, baseMaterial)
        );
    }

    private TrimMaterialStats getTrimMaterialStats(Material material, Material baseMaterial){
        return new TrimMaterialStats(
                this.calculateExtraDurability(material, baseMaterial)
        );
    }

    private int calculateDurability(Material material, CoreMaterialStats core, HeadMaterialStats head){
        HeadMaterialStats materialHead = material.getStats(MaterialTypes.HEAD);
        return null != materialHead ? (int) Math.clamp(core.durability * materialHead.durability / head.durability / STAT_MULT, DURA_MIN, DURA_MAX): 0;
    }

    private int calculateDurability(Material material, Material baseMaterial){
        return this.calculateDurability(material, baseMaterial.getStats(ArmorMaterialType.CORE), baseMaterial.getStats(MaterialTypes.HEAD));
    }

    private float calculateDefense(Material material, CoreMaterialStats core, HeadMaterialStats head){
        HeadMaterialStats materialHead = material.getStats(MaterialTypes.HEAD);
        return null != materialHead ? Math.clamp(1.5f * core.defense * materialHead.attack / head.attack  / STAT_MULT, DEF_MIN,DEF_MAX) : 0;
    }

    private float calculateDefense(Material material, Material baseMaterial){
        return this.calculateDefense(material, baseMaterial.getStats(ArmorMaterialType.CORE), baseMaterial.getStats(MaterialTypes.HEAD));
    }

    private float calculateToughness(Material material, PlatesMaterialStats plates, HandleMaterialStats handle){
        HandleMaterialStats materialHandle = material.getStats(MaterialTypes.HANDLE);
        return null != materialHandle ? Math.clamp(3 * plates.toughness * materialHandle.durability / handle.durability / STAT_MULT, TOUGH_MIN, TOUGH_MAX) : 0;
    }

    private float calculateToughness(Material material, Material baseMaterial){
        return this.calculateToughness(material, baseMaterial.getStats(ArmorMaterialType.PLATES), baseMaterial.getStats(MaterialTypes.HANDLE));
    }

    private float calculateExtraDurability(Material material, TrimMaterialStats trim, ExtraMaterialStats extra){
        ExtraMaterialStats materialExtra = material.getStats(MaterialTypes.EXTRA);
        return null != materialExtra ? 2 * trim.extraDurability * materialExtra.extraDurability / extra.extraDurability / STAT_MULT : 0;
    }

    private float calculateExtraDurability(Material material, Material baseMaterial){
        return this.calculateExtraDurability(material, baseMaterial.getStats(ArmorMaterialType.TRIM), baseMaterial.getStats(MaterialTypes.EXTRA));
    }
}
