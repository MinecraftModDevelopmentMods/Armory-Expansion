package org.softc.armoryexpansion.dynamic_systems.dynamic_materials;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import org.softc.armoryexpansion.ArmoryExpansion;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

public final class MaterialRegistration
{
    private static final String CORE = "core";
    private static final String EXTRA = "extra";
    private static final String HANDLE = "handle";
    private static final String HEAD = "head";
    private static final String PLATES = "plates";
    private static final String TRIM = "trim";

    private static void registerCoreMaterialStat(final Material material)
    {
        final HeadMaterialStats materialHead = material.getStats(HEAD);
        final HeadMaterialStats ironHead = TinkerMaterials.iron.getStats(HEAD);
        final CoreMaterialStats ironCore = TinkerMaterials.iron.getStats(CORE);

        TinkerRegistry.addMaterialStats(
                material,
                new CoreMaterialStats(
                        ironCore.durability * materialHead.durability / ironHead.durability,
                        1.5f * ironCore.defense * materialHead.attack / ironHead.attack));
    }

    private static void registerPlatesMaterialStat(final Material material)
    {
        final HandleMaterialStats materialHandle = material.getStats(HANDLE);
        final HandleMaterialStats ironHandle = TinkerMaterials.iron.getStats(HANDLE);
        final PlatesMaterialStats ironPlates = TinkerMaterials.iron.getStats(PLATES);

        final float ironPlatesToughness = ironPlates.toughness > 0f ? ironPlates.toughness : 1;

        TinkerRegistry.addMaterialStats(
                material,
                new PlatesMaterialStats(
                        materialHandle.modifier,
                        ironPlates.durability * materialHandle.durability / ironHandle.durability,
                        3 * ironPlatesToughness * materialHandle.durability / ironHandle.durability));
    }

    private static void registerTrimMaterialStat(final Material material)
    {
        final ExtraMaterialStats materialExtra = material.getStats(EXTRA);
        final ExtraMaterialStats ironExtra = TinkerMaterials.iron.getStats(EXTRA);
        final TrimMaterialStats ironTrim = TinkerMaterials.iron.getStats(TRIM);

        TinkerRegistry.addMaterialStats(
                material,
                new TrimMaterialStats((
                        2 * ironTrim.extraDurability * materialExtra.extraDurability / ironExtra.extraDurability)));
    }

    public static void registerFromToolMaterialStat()
    {
        for (int i = 0; i < Config.propertiesMaterials.size(); i++) {
            final Material material = TinkerRegistry.getMaterial(Config.propertiesMaterials.get(i).getName().replaceAll("enable_", ""));
            if (Config.propertiesMaterials.get(i).getBoolean()  && material != null) {
                ArmoryExpansion.logger.info("Registering parts for " + material.getLocalizedName());
                if (Config.propertiesCore.get(i) != null && Config.propertiesCore.get(i).getBoolean() && !material.hasStats(CORE)) {
                    registerCoreMaterialStat(material);
                }
                if (Config.propertiesPlates.get(i) != null && Config.propertiesPlates.get(i).getBoolean() && !material.hasStats(PLATES)) {
                    registerPlatesMaterialStat(material);
                }
                if (Config.propertiesTrim.get(i) != null && Config.propertiesTrim.get(i).getBoolean() && !material.hasStats(TRIM)) {
                    registerTrimMaterialStat(material);
                }
            }
        }
    }
}
