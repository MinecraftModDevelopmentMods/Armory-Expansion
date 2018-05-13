package org.softc.tcexpansion;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraftforge.common.config.Property;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.util.Collection;

class MaterialRegistration
{
    private static void RegisterCoreMaterialStat(Material material)
    {
        HeadMaterialStats materialHead = material.getStats("head");
        HeadMaterialStats ironHead = TinkerMaterials.iron.getStats("head");
        CoreMaterialStats ironCore = TinkerMaterials.iron.getStats("core");

        TinkerRegistry.addMaterialStats(
                material,
                new CoreMaterialStats(
                        ironCore.durability * materialHead.durability / ironHead.durability,
                        ironCore.defense * materialHead.attack / ironHead.attack));
    }

    private static void RegisterPlatesMaterialStat(Material material)
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
                        ironPlatesToughness * materialHandle.durability / ironHandle.durability));
    }

    private static void RegisterTrimMaterialStat(Material material)
    {
        ExtraMaterialStats materialExtra = material.getStats("extra");
        ExtraMaterialStats ironExtra = TinkerMaterials.iron.getStats("extra");
        TrimMaterialStats ironTrim = TinkerMaterials.iron.getStats("trim");

        TinkerRegistry.addMaterialStats(
                material,
                new TrimMaterialStats((
                        ironTrim.extraDurability * materialExtra.extraDurability / ironExtra.extraDurability)));
    }

    private static void RegisterFromToolMaterialStat(Property property)
    {
        if (property.getBoolean())
        {
            Material material = TinkerRegistry.getMaterial(property.getName());
            ArmoryExpansion.logger.info("Adding armor parts for " + material.getLocalizedName());
            RegisterCoreMaterialStat(material);
            RegisterPlatesMaterialStat(material);
            RegisterTrimMaterialStat(material);
        }
    }

    static void RegisterFromToolMaterialStat(Collection<Property> properties)
    {
        for (Property property:properties)
        {
            RegisterFromToolMaterialStat(property);
        }
    }
}
