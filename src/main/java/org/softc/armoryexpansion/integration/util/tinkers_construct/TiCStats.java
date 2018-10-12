package org.softc.armoryexpansion.integration.util.tinkers_construct;

import net.minecraft.item.Item;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

public class TiCStats {
    private TiCStats(){

    }

    private static HeadMaterialStats getHeadMaterialStats(int durability, float hardness, float damage, int harvestLevel) {
        return new HeadMaterialStats(durability, hardness * 0.85f, damage * 2, harvestLevel);
    }

    private static HandleMaterialStats getHandledMaterialStats(int durability, float magicaffinity) {
        return new HandleMaterialStats(magicaffinity * 2 / 9 + 0.1f,(int)durability / 7);
    }

    private static ExtraMaterialStats getExtraMaterialStats(int durability) {
        return new ExtraMaterialStats(durability / 10);
    }

    private static void registerMaterialToolStats(Material material, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
        if (!material.hasStats(TinkerToolParts.HEAD.toString()))
            TinkerRegistry.addMaterialStats(material, TiCStats.getHeadMaterialStats(durability, hardness, damage, harvestLevel));
        if (!material.hasStats(TinkerToolParts.HANDLE.toString()))
            TinkerRegistry.addMaterialStats(material, TiCStats.getHandledMaterialStats(durability, magicaffinity));
        if (!material.hasStats(TinkerToolParts.EXTRA.toString()))
            TinkerRegistry.addMaterialStats(material, TiCStats.getExtraMaterialStats(durability));
    }

    private static void registerMaterialToolStats(String identifier, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
        registerMaterialToolStats(TinkerRegistry.getMaterial(identifier), durability, hardness, damage, magicaffinity, harvestLevel);
    }

//    public static void registerMaterialToolStats(String identifier, int color, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
//        registerMaterialToolStats(new Material(identifier, color), durability, hardness, damage, magicaffinity, harvestLevel);
//    }

//    private static FletchingMaterialStats getFletchlingMaterialStats() {
//        return null;
//    }


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
        if (!material.hasStats(TinkerToolParts.BOW.toString()))
            TinkerRegistry.addMaterialStats(material, TiCStats.getBowMaterialStats(durability, range, damage));
        if (!material.hasStats(TinkerToolParts.SHAFT.toString()))
            TinkerRegistry.addMaterialStats(material, TiCStats.getArrowShaftMaterialStats(magicaffinity));
    }

    private static void registerMaterialBowStats(String identifier, int durability, float range, float damage, float magicaffinity){
        registerMaterialBowStats(TinkerRegistry.getMaterial(identifier), durability, range, damage, magicaffinity);
    }

//    public static void registerMaterialBowStats(String identifier, int color, int durability, float range, float damage, float magicaffinity){
//        registerMaterialBowStats(new Material(identifier, color), durability, range, damage, magicaffinity);
//    }

//    public static void setCraftable(String identifier, boolean craftable){
//        TinkerRegistry.getMaterial(identifier).setCraftable(craftable);
//    }
//
//    public static void setCastable(String identifier, boolean castable){
//        TinkerRegistry.getMaterial(identifier).setCastable(castable);
//    }

    public static void addToolMaterial(String identifier, int durability, float hardness, float damage, float magicaffinity, int harvestLevel, float range){
        registerMaterialToolStats(identifier, durability, hardness, damage, magicaffinity, harvestLevel);
        registerMaterialBowStats(identifier, durability, range, damage, magicaffinity);
    }

    public static void addTinkersMaterial(String identifier, int color, Item item){
        Material material = new Material(identifier, color);
        material.setCastable(false)
                .setCraftable(true)
                .addItemIngot(identifier);
        material.addItem(item);
    }

    public static void addTinkersMaterialItem(String identifier, Item item){
        Material material = TinkerRegistry.getMaterial(identifier);
        TinkerRegistry.addMaterial(material);
        material.setRepresentativeItem(item);
    }
}
