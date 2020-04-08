package org.softc.armoryexpansion.common.integration.aelib.registration.stats;

import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public enum ToolStatsRegistration {
    ;

    public static void registerToolStats(IToolMaterial toolMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(toolMaterial.isToolMaterial()){
            registerHeadStats(toolMaterial, material, properties);
            registerHandleStats(toolMaterial, material, properties);
            registerExtraStats(toolMaterial, material, properties);
        }
    }

    private static void registerHeadStats(IToolMaterial toolMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.HEAD) && null != toolMaterial.getHeadMaterialStats() && properties.isHeadEnabled()){
            TinkerRegistry.addMaterialStats(material, toolMaterial.getHeadMaterialStats());
        }
    }

    private static void registerHandleStats(IToolMaterial toolMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.HANDLE) && null != toolMaterial.getHandleMaterialStats() && properties.isHandleEnabled()){
            TinkerRegistry.addMaterialStats(material, toolMaterial.getHandleMaterialStats());
        }
    }

    private static void registerExtraStats(IToolMaterial toolMaterial, slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.EXTRA) && null != toolMaterial.getExtraMaterialStats() && properties.isExtraEnabled()){
            TinkerRegistry.addMaterialStats(material, toolMaterial.getExtraMaterialStats());
        }
    }
}
