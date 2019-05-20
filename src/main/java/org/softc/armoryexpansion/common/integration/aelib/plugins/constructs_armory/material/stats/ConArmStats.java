package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.stats;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.old.TiCMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

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

    private static void registerMaterialArmorStats(Material material, float durability, float defense, float toughness, float magicaffinity, float extra, MaterialConfigOptions properties){
        if (!material.hasStats(CORE) && properties.isCoreEnabled() && durability > 0 || defense > 0){
            TinkerRegistry.addMaterialStats(material, getCoreMaterialStats(durability, defense));
        }
        if (!material.hasStats(TRIM) && properties.isTrimEnabled() && extra > 0) {
            TinkerRegistry.addMaterialStats(material, getTrimMaterialStats(extra));
        }
        if (!material.hasStats(PLATES) && properties.isPlatesEnabled() && (durability > 0 || magicaffinity > 0)) {
            TinkerRegistry.addMaterialStats(material, getPlatesMaterialStats(magicaffinity, durability, toughness));
        }
    }

    private static void registerMaterialArmorStats(String identifier, float durability, float defense, float toughness, float magicaffinity, float extra, MaterialConfigOptions properties) {
        Material material = TinkerRegistry.getMaterial(identifier);
        if ("unknown".equals(material.identifier)) {
            return;
        }
        registerMaterialArmorStats(material, durability, defense, toughness, magicaffinity, extra, properties);
    }

    public static void registerMaterialArmorStats(TiCMaterial material, MaterialConfigOptions properties) {
        registerMaterialArmorStats(material.getIdentifier(), material.getDurability(), material.getDefense(), material.getToughness(), material.getMagicAffinity(), material.getDurability(), properties);
    }
}
