package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.stats.ToolMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public class ToolMaterial implements IToolMaterial {
    private IMaterial material;
    private ToolMaterialStats toolMaterialStats;

    public ToolMaterialStats getToolMaterialStats() {
        return this.toolMaterialStats;
    }

    public HeadMaterialStats getHeadMaterialStats() {
        return this.getToolMaterialStats().getHeadMaterialStats();
    }

    public HandleMaterialStats getHandleMaterialStats() {
        return this.getToolMaterialStats().getHandleMaterialStats();
    }

    public ExtraMaterialStats getExtraMaterialStats() {
        return this.getToolMaterialStats().getExtraMaterialStats();
    }

    @Override
    public IToolMaterial addPrimaryToolTrait(String trait) {
        return null;
    }

    @Override
    public IToolMaterial addSecondaryToolTrait(String trait) {
        return null;
    }

    @Override
    public IToolMaterial addGlobalToolTrait(String trait) {
        return null;
    }

    @Override
    public IToolMaterial addToolTrait(String trait1, String trait2) {
        return null;
    }

    @Override
    public boolean isToolMaterial() {
        return true;
    }
}
