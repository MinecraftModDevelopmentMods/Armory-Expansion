package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;

public interface IArmorMaterial {
    CoreMaterialStats getCoreMaterialStats();

    PlatesMaterialStats getPlatesMaterialStats();

    TrimMaterialStats getTrimMaterialStats();

    IArmorMaterial addPrimaryArmorTrait(String trait);

    IArmorMaterial addSecondaryArmorTrait(String trait);

    IArmorMaterial addGlobalArmorTrait(String trait);

    IArmorMaterial addArmorTrait(String trait1, String trait2);

    boolean isArmorMaterial();
}
