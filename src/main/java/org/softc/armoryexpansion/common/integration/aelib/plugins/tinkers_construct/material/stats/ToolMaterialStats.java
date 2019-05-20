package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.stats;

import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public class ToolMaterialStats {
    private HeadMaterialStats headMaterialStats;
    private HandleMaterialStats handleMaterialStats;
    private ExtraMaterialStats extraMaterialStats;

    public HeadMaterialStats getHeadMaterialStats() {
        return headMaterialStats;
    }

    public HandleMaterialStats getHandleMaterialStats() {
        return handleMaterialStats;
    }

    public ExtraMaterialStats getExtraMaterialStats() {
        return extraMaterialStats;
    }
}
