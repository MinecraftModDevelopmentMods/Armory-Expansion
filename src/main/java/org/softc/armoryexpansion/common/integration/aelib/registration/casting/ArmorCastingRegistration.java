package org.softc.armoryexpansion.common.integration.aelib.registration.casting;

import c4.conarm.lib.materials.*;
import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material.*;

public enum ArmorCastingRegistration {
    ;

    public static void registerArmorCasting(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if (armorMaterial.isArmorMaterial()) {
            registerCoreCasting(armorMaterial, material, properties);
            registerPlatesCasting(armorMaterial, material, properties);
            registerTrimCasting(armorMaterial, material, properties);
        }
    }

    private static void registerCoreCasting(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.CORE) && null != armorMaterial.getCoreMaterialStats() && properties.coreEnabled()){
            // TODO
//            TinkerRegistry.registerTableCasting();
        }
    }

    private static void registerPlatesCasting(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.PLATES) && null != armorMaterial.getPlatesMaterialStats() && properties.platesEnabled()){
            // TODO
//            TinkerRegistry.registerTableCasting();
        }
    }

    private static void registerTrimCasting(IArmorMaterial armorMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.TRIM) && null != armorMaterial.getTrimMaterialStats() && properties.trimEnabled()){
            // TODO
//            TinkerRegistry.registerTableCasting();
        }
    }
}
