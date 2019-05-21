package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.IToolMaterial;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public class ArmorToolMaterial extends ArmorMaterial implements IToolMaterial {
    private HeadMaterialStats headMaterialStats;
    private HandleMaterialStats handleMaterialStats;
    private ExtraMaterialStats extraMaterialStats;

    public ArmorToolMaterial(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public HeadMaterialStats getHeadMaterialStats() {
        return headMaterialStats;
    }

    @Override
    public HandleMaterialStats getHandleMaterialStats() {
        return handleMaterialStats;
    }

    @Override
    public ExtraMaterialStats getExtraMaterialStats() {
        return extraMaterialStats;
    }

    @Override
    public IToolMaterial addPrimaryToolTrait(String trait) {
        return null;
    }

    @Override
    public IToolMaterial addSecondaryToolTrait(String trait) {
        return null;
    }

    @Override
    public IToolMaterial addGlobalToolTrait(String trait) {
        return null;
    }

    @Override
    public IToolMaterial addToolTrait(String trait1, String trait2) {
        return null;
    }

    @Override
    public boolean isToolMaterial() {
        return this.getHeadMaterialStats() != null
                || this.getHandleMaterialStats() != null
                || this.getExtraMaterialStats() != null;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister) {
        return false;
    }
}
