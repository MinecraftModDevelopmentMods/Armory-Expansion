package org.softc.armoryexpansion.integration.tinkers_construct;

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
            TinkerRegistry.addMaterialStats(material, getHeadMaterialStats(durability, hardness, damage, harvestLevel));
        if (!material.hasStats(HANDLE))
            TinkerRegistry.addMaterialStats(material, getHandledMaterialStats(durability, magicaffinity));
        if (!material.hasStats(EXTRA))
            TinkerRegistry.addMaterialStats(material, getExtraMaterialStats(durability));
    }

    private static void registerMaterialToolStats(String identifier, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialToolStats(material, durability, hardness, damage, magicaffinity, harvestLevel);
    }

    static void registerMaterialToolStats(TiCMaterial material) {
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

//    private static BowStringMaterialStats getBowStringMaterialStats() {
//        return null;
//    }

    private static BowMaterialStats getBowMaterialStats(int durability, float range, float damage) {
        return new BowMaterialStats(calcDrawSpeed(durability), range,damage + 3);
    }

    private static ArrowShaftMaterialStats getArrowShaftMaterialStats(float magicaffinity) {
        return new ArrowShaftMaterialStats(magicaffinity * 2 / 9 + 0.1f, (int)magicaffinity / 5);
    }

    private static void registerMaterialBowStats(Material material, int durability, float range, float damage, float magicaffinity){
        if (!material.hasStats(BOW))
            TinkerRegistry.addMaterialStats(material, getBowMaterialStats(durability, range, damage));
        if (!material.hasStats(SHAFT))
            TinkerRegistry.addMaterialStats(material, getArrowShaftMaterialStats(magicaffinity));
    }

    private static void registerMaterialBowStats(String identifier, int durability, float range, float damage, float magicaffinity){
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialBowStats(material, durability, range, damage, magicaffinity);
    }

    static void registerMaterialBowStats(TiCMaterial material) {
        registerMaterialBowStats(material.getIdentifier(), material.getDurability(), material.getRange(), material.getDamage(), material.getMagicaffinity());
    }

    private static FletchingMaterialStats getFletchlingMaterialStats(float accuracy, float magicaffinity) {
        return new FletchingMaterialStats(accuracy, magicaffinity);
    }

    private static void registerMaterialFletchingStats(Material material, float accuracy, float magicaffinity){
        if (!material.hasStats(FLETCHING))
            TinkerRegistry.addMaterialStats(material, TiCStats.getFletchlingMaterialStats(accuracy, magicaffinity));
    }

    private static void registerMaterialFletchingStats(String identifier, float accuracy, float magicaffinity){
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialFletchingStats(material, accuracy, magicaffinity);
    }

    static void registerMaterialFletchingStats(TiCMaterial material) {
        registerMaterialFletchingStats(material.getIdentifier(), 0, material.getMagicaffinity());
    }

    private static ProjectileMaterialStats getProjectileMaterialStats() {
        return new ProjectileMaterialStats();
    }

    private static void registerMaterialProjectileStats(Material material) {
        if (!material.hasStats(PROJECTILE))
            TinkerRegistry.addMaterialStats(material, getProjectileMaterialStats());
    }

    private static void registerMaterialProjectileStats(String identifier) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        registerMaterialProjectileStats(material);
    }

    static void registerMaterialProjectileStats(TiCMaterial material) {
        registerMaterialProjectileStats(material.getIdentifier());
    }
}
