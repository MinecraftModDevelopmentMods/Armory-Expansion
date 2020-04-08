package org.softc.armoryexpansion.common.integration.aelib.plugins.general.material;

import net.minecraft.block.*;
import net.minecraft.util.*;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tconstruct.material.*;
import org.softc.armoryexpansion.common.integration.aelib.config.*;
import slimeknights.tconstruct.library.fluid.*;

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

    boolean registerTinkersMaterialCasting(MaterialConfigOptions properties);
}
