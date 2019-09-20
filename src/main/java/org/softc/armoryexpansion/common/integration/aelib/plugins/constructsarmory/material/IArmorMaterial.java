package org.softc.armoryexpansion.common.integration.aelib.plugins.constructsarmory.material;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;

public interface IArmorMaterial {
    CoreMaterialStats getCoreMaterialStats();

    PlatesMaterialStats getPlatesMaterialStats();

    TrimMaterialStats getTrimMaterialStats();

    boolean isArmorMaterial();
}
