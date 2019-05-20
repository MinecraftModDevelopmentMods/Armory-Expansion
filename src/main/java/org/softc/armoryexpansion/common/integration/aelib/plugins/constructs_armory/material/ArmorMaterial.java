package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;

public class ArmorMaterial implements IArmorMaterial{
    private CoreMaterialStats coreMaterialStats;
    private PlatesMaterialStats platesMaterialStats;
    private TrimMaterialStats trimMaterialStats;

    public CoreMaterialStats getCoreMaterialStats() {
        return coreMaterialStats;
    }

    public PlatesMaterialStats getPlatesMaterialStats() {
        return platesMaterialStats;
    }

    public TrimMaterialStats getTrimMaterialStats() {
        return trimMaterialStats;
    }

    @Override
    public IArmorMaterial addPrimaryArmorTrait(String trait) {
        return null;
    }

    @Override
    public IArmorMaterial addSecondaryArmorTrait(String trait) {
        return null;
    }

    @Override
    public IArmorMaterial addGlobalArmorTrait(String trait) {
        return null;
    }

    @Override
    public IArmorMaterial addArmorTrait(String trait1, String trait2) {
        return null;
    }

    @Override
    public boolean isArmorMaterial() {
        return true;
    }
}
