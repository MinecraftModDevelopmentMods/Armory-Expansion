package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.alloys;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.tconstruct.TConstruct;

public final class Alloy implements IAlloy {
    private AlloyComponent output;
    private AlloyComponent[] inputs;

    @Override
    public void registerTiCAlloy(){
        NBTTagList tagList = new NBTTagList();
        tagList.appendTag(this.output.getFluidTag());
        for (AlloyComponent input : this.inputs) {
            tagList.appendTag(input.getFluidTag());
        }

        NBTTagCompound message = new NBTTagCompound();
        message.setTag("alloy", tagList);
        FMLInterModComms.sendMessage(TConstruct.modID, "alloy", message);
    }

    @Override
    public AlloyComponent getOutput() {
        return this.output;
    }

    @Override
    public AlloyComponent[] getInputs() {
        return this.inputs.clone();
    }

    public Alloy(AlloyComponent out, AlloyComponent[] in) {
        this.output = out;
        this.inputs = in.clone();
    }

    @Override
    public String getName(){
        return this.output.getFluid();
    }
}
