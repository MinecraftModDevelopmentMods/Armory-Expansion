package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.material;

import slimeknights.tconstruct.library.materials.*;

public interface IRangedMaterial {
    BowMaterialStats getBowMaterialStats();

    BowStringMaterialStats getBowStringMaterialStats();

    ArrowShaftMaterialStats getArrowShaftMaterialStats();

    FletchingMaterialStats getFletchingMaterialStats();

    ProjectileMaterialStats getProjectileMaterialStats();

    boolean isRangedMaterial();
}
