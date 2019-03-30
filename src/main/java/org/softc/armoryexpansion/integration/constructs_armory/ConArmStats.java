package org.softc.armoryexpansion.integration.constructs_armory;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import org.softc.armoryexpansion.integration.tinkers_construct.TiCMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import static c4.conarm.lib.materials.ArmorMaterialType.*;

public class ConArmStats {
    private ConArmStats(){

    }

    private static CoreMaterialStats getCoreMaterialStats(float durability, float defense) {
        return new CoreMaterialStats(durability, defense * 2);
    }

    private static PlatesMaterialStats getPlatesMaterialStats(float magicaffinity, float durability, float toughness) {
        return new PlatesMaterialStats(magicaffinity / 8f, durability, toughness * 1.5f);
    }

    private static TrimMaterialStats getTrimMaterialStats(float extra) {
        return new TrimMaterialStats(extra / 8f);
    }

    private static void registerMaterialArmorStats(Material material, float durability, float defense, float toughness, float magicaffinity, float extra){
        if (!material.hasStats(CORE))
            TinkerRegistry.addMaterialStats(material, getCoreMaterialStats(durability, defense));
        if (!material.hasStats(TRIM))
            TinkerRegistry.addMaterialStats(material, getTrimMaterialStats(extra));
        if (!material.hasStats(PLATES))
            TinkerRegistry.addMaterialStats(material, getPlatesMaterialStats(magicaffinity, durability, toughness));
    }

    private static void registerMaterialArmorStats(String identifier, float durability, float defense, float toughness, float magicaffinity, float extra) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if (material.identifier.equals("unknown")) {
            return;
        }
        registerMaterialArmorStats(material, durability, defense, toughness, magicaffinity, extra);
    }

    public static void registerMaterialArmorStats(TiCMaterial material) {
        registerMaterialArmorStats(material.getIdentifier(), material.getDurability(), material.getDefense(), material.getToughness(), material.getMagicaffinity(), material.getDurability());
    }
}
