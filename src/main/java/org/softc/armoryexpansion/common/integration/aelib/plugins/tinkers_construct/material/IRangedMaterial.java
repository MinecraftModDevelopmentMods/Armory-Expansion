package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import slimeknights.tconstruct.library.materials.*;

public interface IRangedMaterial {
    BowMaterialStats getBowMaterialStats();

    BowStringMaterialStats getBowStringMaterialStats();

    ArrowShaftMaterialStats getArrowShaftMaterialStats();

    FletchingMaterialStats getFletchingMaterialStats();

    ProjectileMaterialStats getProjectileMaterialStats();

    IRangedMaterial addPrimaryRangedTrait(String trait);

    IRangedMaterial addSecondaryRangedTrait(String trait);

    IRangedMaterial addGlobalRangedTrait(String trait);

    IRangedMaterial addRangedTrait(String trait1, String trait2);

    boolean isRangedMaterial();
}
