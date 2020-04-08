package org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material;

import c4.conarm.lib.materials.*;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tconstruct.material.*;
import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.*;
import org.softc.armoryexpansion.common.integration.aelib.registration.stats.*;
import slimeknights.tconstruct.library.*;

public class ArmorMaterial extends BasicMaterial implements IArmorMaterial{
    protected CoreMaterialStats coreMaterialStats;
    protected PlatesMaterialStats platesMaterialStats;
    protected TrimMaterialStats trimMaterialStats;

    ArmorMaterial(String identifier, int color) {
        super(identifier, color, MaterialRenderType.DEFAULT);
    }

    public ArmorMaterial(String identifier, int color, MaterialRenderType type, CoreMaterialStats coreMaterialStats, PlatesMaterialStats platesMaterialStats, TrimMaterialStats trimMaterialStats) {
        super(identifier, color, type);
        this.coreMaterialStats = coreMaterialStats;
        this.platesMaterialStats = platesMaterialStats;
        this.trimMaterialStats = trimMaterialStats;
    }

    @Override
    public CoreMaterialStats getCoreMaterialStats() {
        return this.coreMaterialStats;
    }

    @Override
    public PlatesMaterialStats getPlatesMaterialStats() {
        return this.platesMaterialStats;
    }

    @Override
    public TrimMaterialStats getTrimMaterialStats() {
        return this.trimMaterialStats;
    }

    @Override
    public boolean isToolMaterial() {
        return false;
    }

    @Override
    public boolean isArmorMaterial() {
        return null != this.coreMaterialStats
                || null != this.platesMaterialStats
                || null != this.trimMaterialStats;
    }

    @Override
    public boolean isRangedMaterial() {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        if (properties.materialEnabled() && properties.armorEnabled()) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
                ArmorStatsRegistration.registerArmorStats(this, material, properties);
                return true;
//            }
        }
        return false;
    }

    @Override
    public boolean registerTinkersMaterialCasting(MaterialConfigOptions properties) {
        if (properties.materialEnabled() && properties.armorEnabled() && this.isCastable()) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
//            TODO ArmorCastingRegistration.registerArmorCasting(this, material, properties);
            return true;
//            }
        }
        return false;
    }
}
