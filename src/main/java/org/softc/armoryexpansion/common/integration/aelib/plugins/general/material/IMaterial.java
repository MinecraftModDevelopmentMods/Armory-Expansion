package org.softc.armoryexpansion.common.integration.aelib.plugins.general.material;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import slimeknights.tconstruct.library.fluid.FluidMolten;

public interface IMaterial {
    String getIdentifier();

    int getColor();

    Item getItem();

    ItemStack getItemStack();

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

    IMaterial registerOreDict();

    boolean registerTinkersMaterial(boolean canRegister);

    boolean registerTinkersFluid(boolean canRegister);

    boolean registerTinkersFluidIMC(boolean canRegister);

    boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister);

    boolean updateTinkersMaterial(boolean canRegister);

    boolean registerTinkersMaterialTraits(boolean canRegister);

    IMaterial addTrait(String trait, String location);
}
