package org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material;

import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.*;
import org.softc.armoryexpansion.common.integration.aelib.registration.stats.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ToolMaterial extends BasicMaterial implements IToolMaterial {
    protected HeadMaterialStats headMaterialStats;
    protected HandleMaterialStats handleMaterialStats;
    protected ExtraMaterialStats extraMaterialStats;

    @Override
    public HeadMaterialStats getHeadMaterialStats() {
        return this.headMaterialStats;
    }

    @Override
    public HandleMaterialStats getHandleMaterialStats() {
        return this.handleMaterialStats;
    }

    @Override
    public ExtraMaterialStats getExtraMaterialStats() {
        return this.extraMaterialStats;
    }

    @Override
    public boolean isToolMaterial() {
        return null != this.headMaterialStats
                || null != this.handleMaterialStats
                || null != this.extraMaterialStats;
    }

    @Override
    public boolean isArmorMaterial() {
        return false;
    }

    @Override
    public boolean isRangedMaterial() {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        if (properties.materialEnabled() && properties.isToolEnabled()) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
                ToolStatsRegistration.registerToolStats(this, material, properties);
                return true;
//            }
        }
        return false;
    }

    @Override
    public boolean registerTinkersMaterialCasting(MaterialConfigOptions properties) {
        return false;
    }
}
