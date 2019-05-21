package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import slimeknights.tconstruct.library.materials.*;

public class RangedMaterial implements IRangedMaterial {
    private BowMaterialStats bowMaterialStats;
    private BowStringMaterialStats bowStringMaterialStats;
    private ArrowShaftMaterialStats arrowShaftMaterialStats;
    private FletchingMaterialStats fletchingMaterialStats;
    private ProjectileMaterialStats projectileMaterialStats;

    @Override
    public BowMaterialStats getBowMaterialStats() {
        return bowMaterialStats;
    }

    @Override
    public BowStringMaterialStats getBowStringMaterialStats() {
        return bowStringMaterialStats;
    }

    @Override
    public ArrowShaftMaterialStats getArrowShaftMaterialStats() {
        return arrowShaftMaterialStats;
    }

    @Override
    public FletchingMaterialStats getFletchingMaterialStats() {
        return fletchingMaterialStats;
    }

    @Override
    public ProjectileMaterialStats getProjectileMaterialStats() {
        return projectileMaterialStats;
    }

    @Override
    public IRangedMaterial addPrimaryRangedTrait(String trait) {
        return null;
    }

    @Override
    public IRangedMaterial addSecondaryRangedTrait(String trait) {
        return null;
    }

    @Override
    public IRangedMaterial addGlobalRangedTrait(String trait) {
        return null;
    }

    @Override
    public IRangedMaterial addRangedTrait(String trait1, String trait2) {
        return null;
    }

    @Override
    public boolean isRangedMaterial() {
        return this.getArrowShaftMaterialStats() != null
                || this.getBowMaterialStats() != null
                || this.getBowStringMaterialStats() != null
                || this.getFletchingMaterialStats() != null
                || this.getProjectileMaterialStats() != null;
    }
}
