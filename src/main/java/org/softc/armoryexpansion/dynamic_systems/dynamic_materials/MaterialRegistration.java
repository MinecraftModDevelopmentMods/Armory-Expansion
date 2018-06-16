package org.softc.armoryexpansion.dynamic_systems.dynamic_materials;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraftforge.common.config.Property;
import org.softc.armoryexpansion.ArmoryExpansion;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.util.Collection;

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

    private static void registerFromToolMaterialStat(Material material)
    {
        if (!material.hasStats("core") && material.hasStats("head")) registerCoreMaterialStat(material);
        if (!material.hasStats("plates") && material.hasStats("handle")) registerPlatesMaterialStat(material);
        if (!material.hasStats("trim") && material.hasStats("extra")) registerTrimMaterialStat(material);
    }

    private static boolean registerFromToolMaterialStat(Property property)
    {
        Material material = TinkerRegistry.getMaterial(property.getName());
        if (property.getBoolean())
        {
            registerFromToolMaterialStat(material);
            return true;
        }
        return false;
    }

    public static void registerFromToolMaterialStat(Collection<Property> properties)
    {
        for (Property property:properties)
        {
            if (TinkerRegistry.getMaterial(property.getName())!=null) {
                ArmoryExpansion.logger.info("Attempting to add armor parts for " + property.getName() + " returned: " + registerFromToolMaterialStat(property));
            }
            else {
                properties.remove(property);
            }
        }
    }
}
