package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.alloys;

import net.minecraft.nbt.NBTTagCompound;

public interface IAlloyComponent {
    String getFluid();

    int getAmount();

    NBTTagCompound getFluidTag();
}
