package org.softc.armoryexpansion.common.integration.aelib.plugins.general.material;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import slimeknights.tconstruct.library.fluid.FluidMolten;

public interface IBasicMaterial {
    String getIdentifier();

    int getColor();

    MaterialRenderType getType();

    ResourceLocation getTexture();

    String getFluidName();

    FluidMolten getFluid();

    Block getFluidBlock();

    boolean isCastable();

    boolean isCraftable();

    boolean isToolMaterial();

    boolean isArmorMaterial();

    boolean isRangedMaterial();

    boolean registerTinkersMaterial(boolean canRegister);

    boolean registerTinkersFluid(boolean canRegister);

    boolean registerTinkersFluidIMC(boolean canRegister);

    boolean registerTinkersMaterialStats(MaterialConfigOptions properties);

    boolean registerTinkersMaterialTraits(boolean canRegister);

    IBasicMaterial addTrait(String trait, String location);
}
