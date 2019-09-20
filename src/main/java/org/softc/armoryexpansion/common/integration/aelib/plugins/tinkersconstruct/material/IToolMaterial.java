package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.material;

import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public interface IToolMaterial {
    HeadMaterialStats getHeadMaterialStats();

    HandleMaterialStats getHandleMaterialStats();

    ExtraMaterialStats getExtraMaterialStats();

    boolean isToolMaterial();
}
