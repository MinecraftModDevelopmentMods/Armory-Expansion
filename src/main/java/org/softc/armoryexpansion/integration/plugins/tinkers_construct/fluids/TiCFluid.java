package org.softc.armoryexpansion.integration.plugins.tinkers_construct.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class TiCFluid extends Fluid {
    public TiCFluid(String name, int color) {
        super(
                name,
                new ResourceLocation("tconstruct:blocks/fluids/molten_metal"),
                new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"),
                color);
    }

    public TiCFluid(String name) {
        super(
                name,
                new ResourceLocation("tconstruct:blocks/fluids/molten_metal"),
                new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
    }

    @Override
    public int getColor(){
        return this.color;
    }
}
