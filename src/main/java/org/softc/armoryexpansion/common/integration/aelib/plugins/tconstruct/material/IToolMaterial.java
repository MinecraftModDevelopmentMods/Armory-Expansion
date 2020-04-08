package org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material;

import slimeknights.tconstruct.library.materials.*;

public interface IToolMaterial {
    HeadMaterialStats getHeadMaterialStats();

    HandleMaterialStats getHandleMaterialStats();

    ExtraMaterialStats getExtraMaterialStats();

    boolean isToolMaterial();
}
