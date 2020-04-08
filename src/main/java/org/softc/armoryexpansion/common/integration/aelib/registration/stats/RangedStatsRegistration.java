package org.softc.armoryexpansion.common.integration.aelib.registration.stats;

import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public enum RangedStatsRegistration {
    ;

    public static void registerRangedStats(IRangedMaterial rangedMaterial, Material material, MaterialConfigOptions properties){
        if(rangedMaterial.isRangedMaterial()){
            registerBowStats(rangedMaterial, material, properties);
            registerBowStringStats(rangedMaterial, material, properties);
            registerFletchingStats(rangedMaterial, material, properties);
            registerProjectileStats(rangedMaterial, material, properties);
        }
    }

    private static void registerBowStats(IRangedMaterial rangedMaterial, Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.BOW) && null != rangedMaterial.getBowMaterialStats() && properties.isBowEnabled()){
            TinkerRegistry.addMaterialStats(material, rangedMaterial.getBowMaterialStats());
        }
    }

    private static void registerBowStringStats(IRangedMaterial rangedMaterial, Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.BOWSTRING) && null != rangedMaterial.getBowStringMaterialStats() && properties.isBowStringEnabled()){
            TinkerRegistry.addMaterialStats(material, rangedMaterial.getBowStringMaterialStats());
        }
    }

    private static void registerFletchingStats(IRangedMaterial rangedMaterial, Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.FLETCHING) && null != rangedMaterial.getFletchingMaterialStats() && properties.isFletchingEnabled()){
            TinkerRegistry.addMaterialStats(material, rangedMaterial.getFletchingMaterialStats());
        }
    }

    private static void registerProjectileStats(IRangedMaterial rangedMaterial, Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.PROJECTILE) && null != rangedMaterial.getProjectileMaterialStats() && properties.isProjectileEnabled()){
            TinkerRegistry.addMaterialStats(material, rangedMaterial.getProjectileMaterialStats());
        }
    }
}
