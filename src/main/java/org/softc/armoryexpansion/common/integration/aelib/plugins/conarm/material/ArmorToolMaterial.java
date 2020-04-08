package org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material;

import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material.*;
import org.softc.armoryexpansion.common.integration.aelib.registration.casting.*;
import org.softc.armoryexpansion.common.integration.aelib.registration.stats.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ArmorToolMaterial extends ArmorMaterial implements IToolMaterial {
    protected HeadMaterialStats headMaterialStats;
    protected HandleMaterialStats handleMaterialStats;
    protected ExtraMaterialStats extraMaterialStats;

    ArmorToolMaterial(String identifier, int color) {
        super(identifier, color);
    }

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
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//        if (!"unknown".equals(material.getIdentifier())) {
            if (properties.materialEnabled()) {
                ArmorStatsRegistration.registerArmorStats(this, material, properties);
                ToolStatsRegistration.registerToolStats(this, material, properties);
                return true;
            }
//        }
        return false;
    }

    @Override
    public boolean registerTinkersMaterialCasting(MaterialConfigOptions properties) {
        if (properties.materialEnabled() && properties.armorEnabled() && this.isCastable()) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
            ArmorCastingRegistration.registerArmorCasting(this, material, properties);
//            TODO ToolCastingRegistration.registerToolCasting(this, material, properties);
            return true;
//            }
        }
        return false;
    }
}
