package org.softc.armoryexpansion.integration.plugins.tinkers_construct;

import net.minecraftforge.common.config.Property;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import java.util.Map;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

class TiCStats {
    private static HeadMaterialStats getHeadMaterialStats(int durability, float miningSpeed, float damage, int harvestLevel) {
        return new HeadMaterialStats(durability * 8, miningSpeed * 0.65f, damage, harvestLevel);
    }

    private static HandleMaterialStats getHandledMaterialStats(int durability, float magicaffinity) {
        return new HandleMaterialStats(magicaffinity  / 6 + 0.1f, durability * 10 / 7);
    }

    private static ExtraMaterialStats getExtraMaterialStats(int durability) {
        return new ExtraMaterialStats(durability * 5);
    }

    private static void registerMaterialToolStats(Material material, int durability, float miningSpeed, float damage, float magicaffinity, int harvestLevel, Map<String, Property> properties){
        if (!material.hasStats(HEAD) && properties.get(HEAD).getBoolean() && (durability > 0 || miningSpeed > 0 || damage > 0)) {
            TinkerRegistry.addMaterialStats(material, getHeadMaterialStats(durability, miningSpeed, damage, harvestLevel));
        }
        if (!material.hasStats(HANDLE) && properties.get(HANDLE).getBoolean() && (durability > 0 || magicaffinity > 0)) {
            TinkerRegistry.addMaterialStats(material, getHandledMaterialStats(durability, magicaffinity));
        }
        if (!material.hasStats(EXTRA) && properties.get(EXTRA).getBoolean() && durability > 0) {
            TinkerRegistry.addMaterialStats(material, getExtraMaterialStats(durability));
        }
    }

    private static void registerMaterialToolStats(String identifier, int durability, float miningSpeed, float damage, float magicaffinity, int harvestLevel, Map<String, Property> properties){
        Material material = TinkerRegistry.getMaterial(identifier);
        if ("unknown".equals(material.identifier)){
            return;
        }
        registerMaterialToolStats(material, durability, miningSpeed, damage, magicaffinity, harvestLevel, properties);
    }

    static void registerMaterialToolStats(TiCMaterial material, Map<String, Property> properties) {
        registerMaterialToolStats(material.getIdentifier(), material.getDurability(), material.getMiningSpeed(), material.getDamage(), material.getMagicAffinity(), material.getHarvestLevel(), properties);
    }

//    private static BowStringMaterialStats getBowStringMaterialStats() {
//        return null;
//    }

    private static Float calcDrawSpeed(final int durability) {
        float val;
        if (durability < 25) {
            val = 2.0f;
        } else {
            val = 30.0f / durability;
            val -= Math.floor(val);
        }
        return val;
    }

    private static BowMaterialStats getBowMaterialStats(int durability, float range, float damage) {
        return new BowMaterialStats(calcDrawSpeed(durability), range,damage / 3);
    }

    private static ArrowShaftMaterialStats getArrowShaftMaterialStats(float magicaffinity) {
        return new ArrowShaftMaterialStats(magicaffinity * 2 / 9 + 0.1f, (int)magicaffinity / 5);
    }

    private static void registerMaterialBowStats(Material material, int durability, float range, float damage, float magicaffinity, Map<String, Property> properties){
        if (!material.hasStats(BOW) && properties.get(BOW).getBoolean() && (durability > 0 || range > 0 || damage > 0)) {
            TinkerRegistry.addMaterialStats(material, getBowMaterialStats(durability, range, damage));
        }
        if (!material.hasStats(SHAFT) && properties.get(SHAFT).getBoolean() && magicaffinity > 0) {
            TinkerRegistry.addMaterialStats(material, getArrowShaftMaterialStats(magicaffinity));
        }
    }

    private static void registerMaterialBowStats(String identifier, int durability, float range, float damage, float magicaffinity, Map<String, Property> properties){
        Material material = TinkerRegistry.getMaterial(identifier);
        if ("unknown".equals(material.identifier)){
            return;
        }
        registerMaterialBowStats(material, durability, range, damage, magicaffinity, properties);
    }

    static void registerMaterialBowStats(TiCMaterial material,Map<String, Property> properties) {
        registerMaterialBowStats(material.getIdentifier(), material.getDurability(), material.getRange(), material.getDamage(), material.getMagicAffinity(), properties);
    }

    private static FletchingMaterialStats getFletchlingMaterialStats(float accuracy, float magicaffinity) {
        return new FletchingMaterialStats(accuracy, magicaffinity);
    }

    private static void registerMaterialFletchingStats(Material material, float accuracy, float magicaffinity,Map<String, Property> properties){
        if (!material.hasStats(FLETCHING) && properties.get(FLETCHING).getBoolean() && (accuracy > 0 || magicaffinity > 0)) {
            TinkerRegistry.addMaterialStats(material, TiCStats.getFletchlingMaterialStats(accuracy, magicaffinity));
        }
    }

    private static void registerMaterialFletchingStats(String identifier, float accuracy, float magicaffinity,Map<String, Property> properties){
        Material material = TinkerRegistry.getMaterial(identifier);
        if ("unknown".equals(material.identifier)){
            return;
        }
        registerMaterialFletchingStats(material, accuracy, magicaffinity, properties);
    }

    static void registerMaterialFletchingStats(TiCMaterial material, Map<String, Property> properties) {
        registerMaterialFletchingStats(material.getIdentifier(), 0, material.getMagicAffinity(), properties);
    }

    private static ProjectileMaterialStats getProjectileMaterialStats() {
        return new ProjectileMaterialStats();
    }

    private static void registerMaterialProjectileStats(Material material, Map<String, Property> properties) {
        if (!material.hasStats(PROJECTILE) && properties.get(PROJECTILE).getBoolean()){
            TinkerRegistry.addMaterialStats(material, getProjectileMaterialStats());
        }
    }

    private static void registerMaterialProjectileStats(String identifier, Map<String, Property> properties) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if ("unknown".equals(material.identifier)){
            return;
        }
        registerMaterialProjectileStats(material, properties);
    }

    static void registerMaterialProjectileStats(TiCMaterial material, Map<String, Property> properties) {
        registerMaterialProjectileStats(material.getIdentifier(), properties);
    }
}
