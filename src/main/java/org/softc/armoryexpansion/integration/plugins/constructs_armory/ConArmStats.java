package org.softc.armoryexpansion.integration.plugins.constructs_armory;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraftforge.common.config.Property;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.Map;

import static c4.conarm.lib.materials.ArmorMaterialType.*;

public class ConArmStats {
    private static CoreMaterialStats getCoreMaterialStats(float durability, float defense) {
        return new CoreMaterialStats(durability, defense * 2);
    }

    private static PlatesMaterialStats getPlatesMaterialStats(float magicaffinity, float durability, float toughness) {
        return new PlatesMaterialStats(magicaffinity / 8f, durability, toughness * 1.5f);
    }

    private static TrimMaterialStats getTrimMaterialStats(float extra) {
        return new TrimMaterialStats(extra / 8f);
    }

    private static void registerMaterialArmorStats(Material material, float durability, float defense, float toughness, float magicaffinity, float extra, Map<String, Property> properties){
        if (!material.hasStats(CORE) && properties.get(CORE).getBoolean() && durability > 0 || defense > 0){
            TinkerRegistry.addMaterialStats(material, getCoreMaterialStats(durability, defense));
        }
        if (!material.hasStats(TRIM) && properties.get(TRIM).getBoolean() && extra > 0) {
            TinkerRegistry.addMaterialStats(material, getTrimMaterialStats(extra));
        }
        if (!material.hasStats(PLATES) && properties.get(PLATES).getBoolean() && (durability > 0 || magicaffinity > 0)) {
            TinkerRegistry.addMaterialStats(material, getPlatesMaterialStats(magicaffinity, durability, toughness));
        }
    }

    private static void registerMaterialArmorStats(String identifier, float durability, float defense, float toughness, float magicaffinity, float extra, Map<String, Property> properties) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if ("unknown".equals(material.identifier)) {
            return;
        }
        registerMaterialArmorStats(material, durability, defense, toughness, magicaffinity, extra, properties);
    }

    public static void registerMaterialArmorStats(TiCMaterial material, Map<String, Property> properties) {
        registerMaterialArmorStats(material.getIdentifier(), material.getDurability(), material.getDefense(), material.getToughness(), material.getMagicAffinity(), material.getDurability(), properties);
    }
}
