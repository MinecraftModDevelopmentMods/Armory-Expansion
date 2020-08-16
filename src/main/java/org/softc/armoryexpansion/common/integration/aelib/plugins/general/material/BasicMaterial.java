package org.softc.armoryexpansion.common.integration.aelib.plugins.general.material;

import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.*;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tconstruct.material.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.fluid.*;
import slimeknights.tconstruct.smeltery.block.*;

import java.util.*;

public abstract class BasicMaterial implements IBasicMaterial {
    protected String identifier;
    protected int color;
    protected MaterialRenderType type = MaterialRenderType.DEFAULT;
    protected ResourceLocation texture;

    private boolean castable;
    private boolean craftable;

//    protected List<TraitHolder> traits = new LinkedList<>();

    protected BasicMaterial() {
    }

    protected BasicMaterial(String identifier, int color, MaterialRenderType type) {
        this.identifier = identifier;
        this.color = color;
        this.type = type;
    }

    public void setCastable(boolean castable) {
        this.castable = castable;
    }

    public void setCraftable(boolean craftable) {
        this.craftable = craftable;
    }

    @Override
    public boolean isCastable() {
        return this.castable;
    }

    @Override
    public boolean isCraftable() {
        return this.craftable;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public MaterialRenderType getType() {
        return this.type;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }

    @Override
    public boolean registerTinkersMaterial(boolean canRegister){
        if (!canRegister || !"unknown".equals(TinkerRegistry.getMaterial(this.identifier).getIdentifier())) {
            return false;
        }

        slimeknights.tconstruct.library.materials.Material material = new slimeknights.tconstruct.library.materials.Material(this.identifier, this.color);
        material.setCastable(this.castable)
                .setCraftable(this.craftable)
                .addItemIngot(this.identifier);
        if(Side.CLIENT == FMLCommonHandler.instance().getSide()){
            MaterialRenderHelper.setMaterialRenderInfo(material, this);
        }
        this.registerTinkersFluid(true);
        TinkerRegistry.addMaterial(material);
        TinkerRegistry.integrate(material);
        return true;
    }

    @Override
    public boolean registerTinkersFluid(boolean canRegister){
        if (!canRegister || !this.castable) {
            return false;
        }

        Fluid materialFluid = new FluidMolten(this.getFluidName(), this.color);
        materialFluid.setUnlocalizedName(this.getFluidName());
        FluidRegistry.registerFluid(materialFluid);
        FluidRegistry.addBucketForFluid(materialFluid);
        materialFluid.setBlock(this.getFluidBlock());
        return true;
    }

    @Override
    public boolean registerTinkersFluidIMC(boolean canRegister){
        if (!canRegister || !this.castable) {
            return false;
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("fluid", this.getFluidName());
        tag.setString("ore", this.identifier);
        tag.setBoolean("toolforge", true);
        FMLInterModComms.sendMessage(TConstruct.modID, "integrateSmeltery", tag);
        return true;
    }

    @Override
    public String getFluidName(){
        return "molten_" + this.identifier;
    }

    @Override
    public FluidMolten getFluid(){
        return new FluidMolten(this.getFluidName(), this.color);
    }

    @Override
    public Block getFluidBlock(){
        return new BlockMolten(this.getFluid());
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BasicMaterial && this.identifier.equals(((IBasicMaterial) obj).getIdentifier());
    }

    @Override
    public int hashCode() {
        //noinspection NonFinalFieldReferencedInHashCode
        return this.identifier.hashCode();
    }
}
