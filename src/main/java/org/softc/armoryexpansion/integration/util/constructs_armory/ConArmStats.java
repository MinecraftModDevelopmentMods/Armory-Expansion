package org.softc.armoryexpansion.integration.util.constructs_armory;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import org.softc.armoryexpansion.integration.util.tinkers_construct.TiCMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

public class ConArmStats {
    private ConArmStats(){

    }

    private static CoreMaterialStats getCoreMaterialStats() {
        return null;
    }

    private static PlatesMaterialStats getPlatesMaterialStats() {
        return null;
    }

    private static TrimMaterialStats getTrimMaterialStats() {
        return null;
    }

    private static void registerMaterialArmorStats(Material material){
        TinkerRegistry.addMaterialStats(material,
                getCoreMaterialStats(),
                getTrimMaterialStats(),
                getPlatesMaterialStats());
    }

    private static void registerMaterialArmorStats(String identifier) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")) {
            return;
        }
        registerMaterialArmorStats(material);
    }

    public static void registerMaterialArmorStats(TiCMaterial material) {
        //TODO Implement Armor Stats
    }
}
