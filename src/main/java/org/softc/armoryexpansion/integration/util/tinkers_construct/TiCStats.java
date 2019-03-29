package org.softc.armoryexpansion.integration.util.tinkers_construct;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

public class TiCStats {
    private static HeadMaterialStats getHeadMaterialStats(int durability, float hardness, float damage, int harvestLevel) {
        return new HeadMaterialStats(durability, hardness * 0.85f, damage * 2, harvestLevel);
    }

    private static HandleMaterialStats getHandledMaterialStats(int durability, float magicaffinity) {
        return new HandleMaterialStats(magicaffinity * 2 / 9 + 0.1f, durability / 7);
    }

    private static ExtraMaterialStats getExtraMaterialStats(int durability) {
        return new ExtraMaterialStats(durability / 10);
    }

    private static void registerMaterialToolStats(Material material, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
        if (!material.hasStats(HEAD))
            TinkerRegistry.addMaterialStats(material, TiCStats.getHeadMaterialStats(durability, hardness, damage, harvestLevel));
        if (!material.hasStats(HANDLE))
            TinkerRegistry.addMaterialStats(material, TiCStats.getHandledMaterialStats(durability, magicaffinity));
        if (!material.hasStats(EXTRA))
            TinkerRegistry.addMaterialStats(material, TiCStats.getExtraMaterialStats(durability));
    }

    private static void registerMaterialToolStats(String identifier, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialToolStats(material, durability, hardness, damage, magicaffinity, harvestLevel);
    }

    public static void registerMaterialToolStats(TiCMaterial material) {
        registerMaterialToolStats(material.getIdentifier(), material.getDurability(), material.getHardness(), material.getDamage(), material.getMagicaffinity(), material.getHarvestLevel());
    }


    //TODO Credit the MMD team for this section
    private static Float calcDrawSpeed(final int durability) {
        float val;
        if (durability < 204) {
            val = 1.0f;
        } else {
            val = ((durability - 200) + 1) / 10.0f;
            val -= Math.floor(val);
        }
        return val;
    }

    private static BowMaterialStats getBowMaterialStats(int durability, float range, float damage) {
        return new BowMaterialStats(calcDrawSpeed(durability), range,damage + 3);
    }

    private static ArrowShaftMaterialStats getArrowShaftMaterialStats(float magicaffinity) {
        return new ArrowShaftMaterialStats(magicaffinity * 2 / 9 + 0.1f, (int)magicaffinity / 5);
    }

//    private static BowStringMaterialStats getBowStringMaterialStats() {
//        return null;
//    }

//    private static ProjectileMaterialStats getProjectileMaterialStats() {
//        return null;
//    }

    private static void registerMaterialBowStats(Material material, int durability, float range, float damage, float magicaffinity){
        if (!material.hasStats(BOW))
            TinkerRegistry.addMaterialStats(material, TiCStats.getBowMaterialStats(durability, range, damage));
        if (!material.hasStats(SHAFT))
            TinkerRegistry.addMaterialStats(material, TiCStats.getArrowShaftMaterialStats(magicaffinity));
    }

    private static void registerMaterialBowStats(String identifier, int durability, float range, float damage, float magicaffinity){
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialBowStats(material, durability, range, damage, magicaffinity);
    }

    public static void registerMaterialBowStats(TiCMaterial material) {
        registerMaterialBowStats(material.getIdentifier(), material.getDurability(), material.getRange(), material.getDamage(), material.getMagicaffinity());
    }

    private static FletchingMaterialStats getFletchlingMaterialStats() {
        return null;
    }

    private static void registerMaterialFletchingStats(Material material){
        if (!material.hasStats(FLETCHING))
            TinkerRegistry.addMaterialStats(material, TiCStats.getFletchlingMaterialStats());
    }

    private static void registerMaterialFletchingStats(String identifier){
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialFletchingStats(material);
    }

    public static void registerMaterialFletchingStats(TiCMaterial material) {
        registerMaterialFletchingStats(material.getIdentifier());
    }
}
