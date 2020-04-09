package org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.alloys;

import net.minecraft.nbt.*;

public interface IAlloyComponent {
    String getFluid();

    int getAmount();

    NBTTagCompound getFluidTag();
}
