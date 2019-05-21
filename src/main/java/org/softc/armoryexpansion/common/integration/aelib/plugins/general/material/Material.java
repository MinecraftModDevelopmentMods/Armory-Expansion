package org.softc.armoryexpansion.common.integration.aelib.plugins.general.material;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderHelper;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.fluids.TiCFluid;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.fluids.TiCFluidBlock;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits.TraitHolder;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.LinkedList;
import java.util.List;

public abstract class Material implements IMaterial {
    protected String identifier;
    protected String itemName;
    protected int meta;
    protected int color;
    protected MaterialRenderType type = MaterialRenderType.DEFAULT;
    protected ResourceLocation texture;

    protected boolean isCastable = false;
    protected boolean isCraftable = false;

    protected List<TraitHolder> traits = new LinkedList<>();

    public void setCastable(boolean castable) {
        isCastable = castable;
    }

    public void setCraftable(boolean craftable) {
        isCraftable = craftable;
    }

    public boolean isCastable() {
        return this.isCastable;
    }

    public boolean isCraftable() {
        return this.isCraftable;
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
    public Item getItem() {
        return this.itemName != null ? Item.getByNameOrId(this.itemName) : null;
    }

    @Override
    public ItemStack getItemStack() {
        return this.getItem() != null ? new ItemStack(this.getItem(), 1, this.meta) : null;
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
    public IMaterial registerOreDict() {
        ItemStack stack = this.getItemStack();
        if(stack != null){
            OreDictionary.registerOre(this.getIdentifier(), this.getItemStack());
        }
        return this;
    }

    @Override
    public boolean registerTinkersMaterial(boolean canRegister){
        if (!canRegister || !TinkerRegistry.getMaterial(this.getIdentifier()).getIdentifier().equals("unknown")) {
            return false;
        }

        slimeknights.tconstruct.library.materials.Material material = new slimeknights.tconstruct.library.materials.Material(this.getIdentifier(), this.getColor());
        material.setCastable(this.isCastable())
                .setCraftable(this.isCraftable())
                .addItemIngot(this.getIdentifier());
        if(FMLCommonHandler.instance().getSide().equals(Side.CLIENT)){
            MaterialRenderHelper.setMaterialRenderInfo(material, this);
        }
        this.registerTinkersFluid(true);
        TinkerRegistry.addMaterial(material);
        TinkerRegistry.integrate(material);
        return true;
    }

    @Override
    public boolean registerTinkersFluid(boolean canRegister){
        if (!canRegister || !this.isCastable()) {
            return false;
        }

        Fluid materialFluid = new TiCFluid(this.getIdentifier(), this.getColor());
        FluidRegistry.registerFluid(materialFluid);
        FluidRegistry.addBucketForFluid(materialFluid);
        return true;
    }

    @Override
    public boolean registerTinkersFluidIMC(boolean canRegister){
        if (!canRegister || !this.isCastable()) {
            return false;
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("fluid", this.getFluidName());
        tag.setString("ore", this.getIdentifier());
        tag.setBoolean("toolforge", true);
        FMLInterModComms.sendMessage(TConstruct.modID, "integrateSmeltery", tag);
        return true;
    }

    @Override
    public String getFluidName(){
        return "molten_" + this.getIdentifier();
    }

    @Override
    public FluidMolten getFluid(){
        return new FluidMolten(this.getIdentifier(), this.getColor());
    }

    @Override
    public Block getFluidBlock(){
        return new TiCFluidBlock(getFluid());
    }

    @Override
    public abstract boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister);

    @Override
    public boolean updateTinkersMaterial(boolean canRegister){
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
        if ("unknown".equals(material.identifier) || !canRegister) {
            return false;
        }
        material.addItem(this.getItem());
        material.setRepresentativeItem(this.getItemStack());
        return true;
    }

    @Override
    public boolean registerTinkersMaterialTraits(boolean canRegister) {
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
        if ("unknown".equals(material.identifier) || !canRegister) {
            return false;
        }
        this.traits.forEach( t -> {
            ITrait trait = TinkerRegistry.getTrait(t.getTraitName());
            if(trait != null){
                material.addTrait(trait, t.getTraitPart());
            }
        });
        TinkerRegistry.integrate(material);
        return this.traits.size() > 0;
    }

    @Override
    public IMaterial addTrait(String trait, String location) {
        this.traits.add(new TraitHolder(trait, location));
        return this;
    }
}
