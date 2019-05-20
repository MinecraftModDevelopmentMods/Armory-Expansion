package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import net.minecraft.block.Block;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.old.AbstractTiCMaterial;
import slimeknights.tconstruct.library.fluid.FluidMolten;

public class Material implements IMaterial {
    @Override
    public boolean isCastable() {
        return false;
    }

    @Override
    public boolean isCraftable() {
        return false;
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public IMaterial registerOreDict() {
        return null;
    }

    @Override
    public FluidMolten getFluid() {
        return null;
    }

    @Override
    public String getFluidName() {
        return null;
    }

    @Override
    public Block getFluidBlock() {
        return null;
    }

    @Override
    public boolean registerTinkersMaterial(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersFluid(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersFluidIMC(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister) {
        return false;
    }

    @Override
    public boolean updateTinkersMaterial(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialTraits(boolean canRegister) {
        return false;
    }

    @Override
    public MaterialRenderType getType() {
        return null;
    }

    @Override
    public AbstractTiCMaterial setType(MaterialRenderType type) {
        return null;
    }
}
