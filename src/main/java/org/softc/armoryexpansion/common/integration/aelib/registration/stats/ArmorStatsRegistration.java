package org.softc.armoryexpansion.common.integration.aelib.registration.stats;

import c4.conarm.lib.materials.*;
import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material.*;
import slimeknights.tconstruct.library.*;

public enum ArmorStatsRegistration {
    ;

    public static void registerArmorStats(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if (armorMaterial.isArmorMaterial()) {
            registerCoreStats(armorMaterial, material, properties);
            registerPlatesStats(armorMaterial, material, properties);
            registerTrimStats(armorMaterial, material, properties);
        }
    }

    private static void registerCoreStats(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.CORE) && null != armorMaterial.getCoreMaterialStats() && properties.coreEnabled()){
            TinkerRegistry.addMaterialStats(material, armorMaterial.getCoreMaterialStats());
        }
    }

    private static void registerPlatesStats(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.PLATES) && null != armorMaterial.getPlatesMaterialStats() && properties.platesEnabled()){
            TinkerRegistry.addMaterialStats(material, armorMaterial.getPlatesMaterialStats());
        }
    }

    private static void registerTrimStats(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.TRIM) && null != armorMaterial.getTrimMaterialStats() && properties.trimEnabled()){
            TinkerRegistry.addMaterialStats(material, armorMaterial.getTrimMaterialStats());
        }
    }
}
