package org.softc.armoryexpansion.integration.plugins.tinkers_construct.alloys;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.tconstruct.TConstruct;

public final class TiCAlloy {
    private TiCAlloyComponent output;
    private TiCAlloyComponent[] inputs;

    public void registerTiCAlloy(){
        NBTTagList tagList = new NBTTagList();
        tagList.appendTag(output.getFluidTag());
        for (TiCAlloyComponent input : this.inputs) {
            tagList.appendTag(input.getFluidTag());
        }

        NBTTagCompound message = new NBTTagCompound();
        message.setTag("alloy", tagList);
        FMLInterModComms.sendMessage(TConstruct.modID, "alloy", message);
    }

    public TiCAlloyComponent getOutput() {
        return output;
    }

    public TiCAlloyComponent[] getInputs() {
        return inputs;
    }

    public TiCAlloy(TiCAlloyComponent output, TiCAlloyComponent[] inputs) {
        this.output = output;
        this.inputs = inputs;
    }

    public String getName(){
        return this.output.getFluid();
    }
}
