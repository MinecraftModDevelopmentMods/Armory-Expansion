package org.softc.armoryexpansion.integration.util.constructs_armory;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
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

    public static void registerMaterialArmorStats(String identifier){
        registerMaterialArmorStats(TinkerRegistry.getMaterial(identifier));
    }

    public static void registerMaterialArmorStats(String identifier, int color){
        registerMaterialArmorStats(new Material(identifier, color));
    }
}
