package org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material;

import c4.conarm.lib.materials.*;

public interface IArmorMaterial {
    CoreMaterialStats getCoreMaterialStats();

    PlatesMaterialStats getPlatesMaterialStats();

    TrimMaterialStats getTrimMaterialStats();

    boolean isArmorMaterial();
}
