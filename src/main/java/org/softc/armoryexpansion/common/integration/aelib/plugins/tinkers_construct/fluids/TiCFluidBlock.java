package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class TiCFluidBlock extends BlockFluidClassic {
    public TiCFluidBlock(Fluid fluid) {
        super(fluid, Material.LAVA);
        this.setRegistryName(this.getFluid().getName());
    }
}
