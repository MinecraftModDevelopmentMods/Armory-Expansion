package org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material;

import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material.*;
import org.softc.armoryexpansion.common.integration.aelib.registration.stats.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ArmorToolRangedMaterial extends ArmorToolMaterial implements IRangedMaterial {
    protected BowMaterialStats bowMaterialStats;
    protected BowStringMaterialStats bowStringMaterialStats;
    protected ArrowShaftMaterialStats arrowShaftMaterialStats;
    protected FletchingMaterialStats fletchingMaterialStats;
    protected ProjectileMaterialStats projectileMaterialStats;

    public ArmorToolRangedMaterial(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public BowMaterialStats getBowMaterialStats() {
        return this.bowMaterialStats;
    }

    @Override
    public BowStringMaterialStats getBowStringMaterialStats() {
        return this.bowStringMaterialStats;
    }

    @Override
    public ArrowShaftMaterialStats getArrowShaftMaterialStats() {
        return this.arrowShaftMaterialStats;
    }

    @Override
    public FletchingMaterialStats getFletchingMaterialStats() {
        return this.fletchingMaterialStats;
    }

    @Override
    public ProjectileMaterialStats getProjectileMaterialStats() {
        return this.projectileMaterialStats;
    }

    @Override
    public boolean isRangedMaterial() {
        return !(null == this.arrowShaftMaterialStats && null == this.bowMaterialStats && null == this.bowStringMaterialStats && null == this.fletchingMaterialStats && null == this.projectileMaterialStats);
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties) {
        if (properties.materialEnabled()) {
            Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
                ArmorStatsRegistration.registerArmorStats(this, material, properties);
                ToolStatsRegistration.registerToolStats(this, material, properties);
                RangedStatsRegistration.registerRangedStats(this, material, properties);
                return true;
//            }
        }
        return false;
    }

    @Override
    public boolean registerTinkersMaterialCasting(MaterialConfigOptions properties) {
        return super.registerTinkersMaterialCasting(properties);
    }
}
