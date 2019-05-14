package org.softc.armoryexpansion.integration.plugins.tinkers_construct.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import slimeknights.tconstruct.TConstruct;

public final class TiCAlloy {
    class TiCAlloyComponent{
        private String fluid;
        private int amount;

        public TiCAlloyComponent(String fluid, int amount) {
            this.fluid = fluid;
            this.amount = amount;
        }

        String getFluid() {
            return fluid;
        }

        int getAmount() {
            return amount;
        }

        NBTTagCompound getFluidTag(){
            NBTTagCompound fluid = new NBTTagCompound();
            fluid.setString("FluidName", this.fluid);
            fluid.setInteger("Amount", this.amount);
            return fluid;
        }
    }

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
