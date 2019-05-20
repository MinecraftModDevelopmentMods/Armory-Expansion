package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.stats.ToolMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public interface IToolMaterial {
    ToolMaterialStats getToolMaterialStats();

    HeadMaterialStats getHeadMaterialStats();

    HandleMaterialStats getHandleMaterialStats();

    ExtraMaterialStats getExtraMaterialStats();

    IToolMaterial addPrimaryToolTrait(String trait);

    IToolMaterial addSecondaryToolTrait(String trait);

    IToolMaterial addGlobalToolTrait(String trait);

    IToolMaterial addToolTrait(String trait1, String trait2);

    boolean isToolMaterial();
}
