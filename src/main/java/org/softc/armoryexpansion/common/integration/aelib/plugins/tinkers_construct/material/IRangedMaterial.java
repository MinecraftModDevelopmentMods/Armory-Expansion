package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import slimeknights.tconstruct.library.materials.*;

public interface IRangedMaterial {
    BowMaterialStats getBowMaterialStats();

    BowStringMaterialStats getBowStringMaterialStats();

    ArrowShaftMaterialStats getArrowShaftMaterialStats();

    FletchingMaterialStats getFletchingMaterialStats();

    ProjectileMaterialStats getProjectileMaterialStats();

    IToolMaterial addPrimaryRangedTrait(String trait);

    IToolMaterial addSecondaryRangedTrait(String trait);

    IToolMaterial addGlobalRangedTrait(String trait);

    IToolMaterial addRangedTrait(String trait1, String trait2);

    boolean isRangedMaterial();
}
