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

public class MaterialRegistration
{
    private static void registerCoreMaterialStat(Material material)
    {
        HeadMaterialStats materialHead = material.getStats("head");
        HeadMaterialStats ironHead = TinkerMaterials.iron.getStats("head");
        CoreMaterialStats ironCore = TinkerMaterials.iron.getStats("core");

        TinkerRegistry.addMaterialStats(
                material,
                new CoreMaterialStats(
                        ironCore.durability * materialHead.durability / ironHead.durability,
                        1.5f * ironCore.defense * materialHead.attack / ironHead.attack));
    }

    private static void registerPlatesMaterialStat(Material material)
    {
        HandleMaterialStats materialHandle = material.getStats("handle");
        HandleMaterialStats ironHandle = TinkerMaterials.iron.getStats("handle");
        PlatesMaterialStats ironPlates = TinkerMaterials.iron.getStats("plates");

        float ironPlatesToughness = ironPlates.toughness > 0f ? ironPlates.toughness : 1;

        TinkerRegistry.addMaterialStats(
                material,
                new PlatesMaterialStats(
                        materialHandle.modifier,
                        ironPlates.durability * materialHandle.durability / ironHandle.durability,
                        3 * ironPlatesToughness * materialHandle.durability / ironHandle.durability));
    }

    private static void registerTrimMaterialStat(Material material)
    {
        ExtraMaterialStats materialExtra = material.getStats("extra");
        ExtraMaterialStats ironExtra = TinkerMaterials.iron.getStats("extra");
        TrimMaterialStats ironTrim = TinkerMaterials.iron.getStats("trim");

        TinkerRegistry.addMaterialStats(
                material,
                new TrimMaterialStats((
                        2 * ironTrim.extraDurability * materialExtra.extraDurability / ironExtra.extraDurability)));
    }

    public static void registerFromToolMaterialStat()
    {
        for (int i = 0; i < Config.propertiesMaterials.size(); i++) {
            Material material = TinkerRegistry.getMaterial(Config.propertiesMaterials.get(i).getName());
            if (Config.propertiesMaterials.get(i).getBoolean()  && material != null) {
                ArmoryExpansion.logger.info("Registering parts for " + material.getLocalizedName());
                if (Config.propertiesCore.get(i) != null && Config.propertiesCore.get(i).getBoolean()) {
                    if (!material.hasStats("core")) registerCoreMaterialStat(material);
                }
                if (Config.propertiesPlates.get(i) != null && Config.propertiesPlates.get(i).getBoolean()) {
                    if (!material.hasStats("plates")) registerPlatesMaterialStat(material);
                }
                if (Config.propertiesTrim.get(i) != null && Config.propertiesTrim.get(i).getBoolean()) {
                    if (!material.hasStats("trim")) registerTrimMaterialStat(material);
                }
            }
        }
    }
}
