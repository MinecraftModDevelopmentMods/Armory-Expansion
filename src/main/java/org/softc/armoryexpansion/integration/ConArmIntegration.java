package org.softc.armoryexpansion.integration;

import c4.conarm.ConstructsArmory;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import com.mcmoddev.lib.integration.plugins.TinkersConstruct;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.aelib.AbstractIntegration;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

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
public class ConArmIntegration extends AbstractIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-" + ConstructsArmory.MODID;
    static final String NAME = ArmoryExpansion.NAME + " - " + ConstructsArmory.MODNAME;
    static final String DEPENDENCIES =
            "required-after:" + TinkersConstruct.PLUGIN_MODID + "; " +
                    "required-after:" + ConstructsArmory.MODID + "; " +
                    "required-after:" + ArmoryExpansion.MODID + "; " +
                    "after:*";
//                    "after:*" +
//                    "before:" + IceAndFireIntegration.MODID + "; " +
//                    "before:" + CustomMaterialsIntegration.MODID + "; ";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = ConstructsArmory.MODID;
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    protected void loadMaterialsFromSource() {
        final float STAT_MULT = 1.25f;
        final int DURA_MIN = 1;
        final int DURA_MAX = 120;
        final int DEF_MIN = 0;
        final int DEF_MAX = 50;
        final int TOUGH_MIN = DEF_MIN / 10;
        final int TOUGH_MAX = DEF_MAX / 10;

        final HeadMaterialStats ironHead = TinkerMaterials.iron.getStats(HEAD);
        final CoreMaterialStats ironCore = TinkerMaterials.iron.getStats(CORE);
        final HandleMaterialStats ironHandle = TinkerMaterials.iron.getStats(HANDLE);
        final PlatesMaterialStats ironPlates = TinkerMaterials.iron.getStats(PLATES);
        final ExtraMaterialStats ironExtra = TinkerMaterials.iron.getStats(EXTRA);
        final TrimMaterialStats ironTrim = TinkerMaterials.iron.getStats(TRIM);

        for (Material material:TinkerRegistry.getAllMaterials())
        {
            final boolean core = !material.hasStats(CORE) && material.hasStats(HEAD);
            final boolean plates = !material.hasStats(PLATES) && material.hasStats(HANDLE);
            final boolean trim = !material.hasStats(TRIM) && material.hasStats(EXTRA);
            final boolean mat = core || plates || trim;

            if (mat) {
                final HeadMaterialStats materialHead = material.getStats(HEAD);
                final HandleMaterialStats materialHandle = material.getStats(HANDLE);
                final ExtraMaterialStats materialExtra = material.getStats(EXTRA);

                int durability = (int)clamp(ironCore.durability * materialHead.durability / ironHead.durability / STAT_MULT, DURA_MIN, DURA_MAX);
                float defense = clamp(1.5f * ironCore.defense * materialHead.attack / ironHead.attack  / STAT_MULT, DEF_MIN,DEF_MAX);
                float toughness = clamp(3 * ironPlates.toughness * materialHandle.durability / ironHandle.durability / STAT_MULT, TOUGH_MIN, TOUGH_MAX);
                float extra = 2 * ironTrim.extraDurability * materialExtra.extraDurability / ironExtra.extraDurability / STAT_MULT;

                addMaterial(new TiCMaterial(material.identifier, material.getRepresentativeItem().getItem().getRegistryName().toString(), material.materialTextColor)
                        .setArmorMaterial(true)
                        .setDurability(durability)
                        .setMagicaffinity(extra)
                        .setDefense(defense)
                        .setToughness(toughness));
            }
        }
    }
}
