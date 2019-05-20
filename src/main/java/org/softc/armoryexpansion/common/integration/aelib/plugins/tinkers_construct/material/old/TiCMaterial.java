package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.old;

import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderHelper;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.stats.ConArmStats;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.fluids.TiCFluid;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.fluids.TiCFluidBlock;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.stats.TiCStats;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.LinkedList;
import java.util.List;

public class TiCMaterial extends AbstractTiCMaterial{
    private int durability = 0;
    private float miningSpeed = 0;
    private float damage = 0;
    private float magicAffinity = 0;
    private int harvestLevel = 0;
    private float range = 0;
    private float accuracy = 0;
    private float defense = 0;
    private float toughness = 0;

    private List<TraitHolder> traits = new LinkedList<>();

    class TraitHolder{
        private String traitName;
        private String traitPart;

        String getTraitName() {
            return traitName;
        }

        String getTraitPart() {
            return traitPart;
        }

        TraitHolder(String traitName, String traitPart) {
            this.traitName = traitName;
            this.traitPart = traitPart;
        }
    }

    public TiCMaterial(String identifier, String itemName, int color) {
        super(identifier, itemName, color);
    }

    public int getDurability() {
        return durability;
    }

    public TiCMaterial setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public float getMiningSpeed() {
        return miningSpeed;
    }

    public TiCMaterial setMiningSpeed(float miningSpeed) {
        this.miningSpeed = miningSpeed;
        return this;
    }

    public float getDamage() {
        return damage;
    }

    public TiCMaterial setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public float getMagicAffinity() {
        return magicAffinity;
    }

    public TiCMaterial setMagicAffinity(float magicAffinity) {
        this.magicAffinity = magicAffinity;
        return this;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public TiCMaterial setHarvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    public float getRange() {
        return range;
    }

    public TiCMaterial setRange(float range) {
        this.range = range;
        return this;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public TiCMaterial setAccuracy(float accuracy) {
        this.accuracy = accuracy;
        return this;
    }

    public float getDefense() {
        return defense;
    }

    public TiCMaterial setDefense(float defense) {
        this.defense = defense;
        return this;
    }

    public float getToughness() {
        return toughness;
    }

    public TiCMaterial setToughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    private TiCMaterial addTrait(String trait, String location) {
        this.traits.add(new TraitHolder(trait, location));
        return this;
    }

    public TiCMaterial addPrimaryArmorTrait(String trait) {
        return this.addTrait(trait, ArmorMaterialType.CORE);
    }

    public TiCMaterial addSecondaryArmorTrait(String trait) {
        return this.addTrait(trait, ArmorMaterialType.TRIM).addTrait(trait, ArmorMaterialType.PLATES);
    }

    public TiCMaterial addGlobalArmorTrait(String trait) {
        return this.addPrimaryArmorTrait(trait).addSecondaryArmorTrait(trait);
    }

    public TiCMaterial addArmorTrait(String trait1, String trait2) {
        return this.addPrimaryArmorTrait(trait1).addSecondaryArmorTrait(trait2);
    }

    public TiCMaterial addPrimaryToolTrait(String trait) {
        return this.addTrait(trait, MaterialTypes.HEAD);
    }

    public TiCMaterial addSecondaryToolTrait(String trait) {
        return this.addTrait(trait, MaterialTypes.HANDLE).addTrait(trait, MaterialTypes.EXTRA);
    }

    public TiCMaterial addGlobalToolTrait(String trait) {
        return this.addPrimaryToolTrait(trait).addSecondaryToolTrait(trait);
    }

    public TiCMaterial addToolTrait(String trait1, String trait2) {
        return this.addPrimaryToolTrait(trait1).addSecondaryToolTrait(trait2);
    }

    public TiCMaterial registerOreDict() {
        ItemStack stack = this.getItemStack();
        if(stack != null){
            OreDictionary.registerOre(this.identifier, this.getItemStack());
        }
        return this;
    }

    public boolean registerTinkersMaterial(boolean canRegister){
        if (!canRegister || !TinkerRegistry.getMaterial(this.identifier).identifier.equals("unknown")) {
            return false;
        }

        Material material = new Material(this.identifier, this.color);
        material.setCastable(this.isCastable)
                .setCraftable(this.isCraftable)
                .addItemIngot(this.identifier);
        if(FMLCommonHandler.instance().getSide().equals(Side.CLIENT)){
            MaterialRenderHelper.setMaterialRenderInfo(material, this);
        }
        this.registerTinkersFluid(true);
        TinkerRegistry.addMaterial(material);
        TinkerRegistry.integrate(material);
        return true;
    }

    public boolean registerTinkersFluid(boolean canRegister){
        if (!canRegister || !isCastable) {
            return false;
        }

        Fluid materialFluid = new TiCFluid(this.identifier, this.color);
        FluidRegistry.registerFluid(materialFluid);
        FluidRegistry.addBucketForFluid(materialFluid);
        return true;
    }

    public boolean registerTinkersFluidIMC(boolean canRegister){
        if (!canRegister || !isCastable) {
            return false;
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("fluid", this.getFluidName());
        tag.setString("ore", this.identifier);
        tag.setBoolean("toolforge", true);
        FMLInterModComms.sendMessage(TConstruct.modID, "integrateSmeltery", tag);
        return true;
    }

    public String getFluidName(){
        return "molten_" + this.identifier;
    }

    public Fluid getFluid(){
        return new TiCFluid(this.identifier, this.color);
    }

    public Block getFluidBlock(){
        return new TiCFluidBlock(getFluid());
    }

    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister){
        if(!canRegister){
            return false;
        }
        boolean retVal = false;
        if (this.isToolMaterial()) {
            TiCStats.registerMaterialToolStats(this, properties);
            retVal = true;
        }
        if (this.isBowMaterial()) {
            TiCStats.registerMaterialBowStats(this, properties);
            retVal = true;
        }
        if (this.isFletchingMaterial()) {
            TiCStats.registerMaterialFletchingStats(this, properties);
            retVal = true;
        }
        if (this.isProjectileMaterial()) {
            TiCStats.registerMaterialProjectileStats(this, properties);
            retVal = true;
        }
        if (this.isArmorMaterial()) {
            ConArmStats.registerMaterialArmorStats(this, properties);
            retVal = true;
        }
        return retVal;
    }

    public boolean updateTinkersMaterial(boolean canRegister){
        Material material = TinkerRegistry.getMaterial(this.identifier);
        if ("unknown".equals(material.identifier) || !canRegister) {
            return false;
        }
        material.addItem(this.getItem());
        material.setRepresentativeItem(this.getItemStack());
        return true;
    }

    public boolean registerTinkersMaterialTraits(boolean canRegister) {
        Material material = TinkerRegistry.getMaterial(this.identifier);
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
}
