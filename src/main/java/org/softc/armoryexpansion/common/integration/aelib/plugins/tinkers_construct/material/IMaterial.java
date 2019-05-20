package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import net.minecraft.block.Block;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.old.AbstractTiCMaterial;
import slimeknights.tconstruct.library.fluid.FluidMolten;

public interface IMaterial {
    boolean isCastable();

    boolean isCraftable();

    String getIdentifier();

    IMaterial registerOreDict();

    FluidMolten getFluid();

    String getFluidName();

    Block getFluidBlock();

    boolean registerTinkersMaterial(boolean canRegister);

    boolean registerTinkersFluid(boolean canRegister);

    boolean registerTinkersFluidIMC(boolean canRegister);

    boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister);

    boolean updateTinkersMaterial(boolean canRegister);

    boolean registerTinkersMaterialTraits(boolean canRegister);

    MaterialRenderType getType();

    AbstractTiCMaterial setType(MaterialRenderType type);
}
