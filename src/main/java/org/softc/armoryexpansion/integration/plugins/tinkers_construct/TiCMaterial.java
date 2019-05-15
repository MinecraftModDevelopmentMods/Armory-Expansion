package org.softc.armoryexpansion.integration.plugins.tinkers_construct;

import c4.conarm.lib.materials.ArmorMaterialType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.softc.armoryexpansion.integration.plugins.constructs_armory.ConArmStats;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.fluids.TiCFluid;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.fluids.TiCFluidBlock;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public TiCMaterial(String identifier, String itemName, int meta, int color) {
        super(identifier, itemName, meta, color);
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
        this.addTrait(trait, ArmorMaterialType.CORE);
        return this;
    }

    public TiCMaterial addSecondaryArmorTrait(String trait) {
        this.addTrait(trait, ArmorMaterialType.TRIM);
        this.addTrait(trait, ArmorMaterialType.PLATES);
        return this;
    }

    public TiCMaterial addGlobalArmorTrait(String trait) {
        this.addPrimaryArmorTrait(trait);
        this.addSecondaryArmorTrait(trait);
        return this;
    }

    public TiCMaterial addArmorTrait(String trait1, String trait2) {
        this.addPrimaryArmorTrait(trait1);
        this.addSecondaryArmorTrait(trait2);
        return this;
    }

    public TiCMaterial addPrimaryToolTrait(String trait) {
        this.addTrait(trait, MaterialTypes.HEAD);
        return this;
    }

    public TiCMaterial addSecondaryToolTrait(String trait) {
        this.addTrait(trait, MaterialTypes.HANDLE);
        this.addTrait(trait, MaterialTypes.EXTRA);
        return this;
    }

    public TiCMaterial addGlobalToolTrait(String trait) {
        this.addPrimaryToolTrait(trait);
        this.addSecondaryToolTrait(trait);
        return this;
    }

    public TiCMaterial addToolTrait(String trait1, String trait2) {
        this.addPrimaryToolTrait(trait1);
        this.addSecondaryToolTrait(trait2);
        return this;
    }

    public TiCMaterial registerOreDict() {
        ItemStack stack = this.getItemStack();
        if(stack != null){
            OreDictionary.registerOre(this.identifier, this.getItemStack());
        }
        return this;
    }

    @SideOnly(Side.CLIENT)
    protected void setMaterialRenderInfo(Material material) {
            MaterialRenderInfo materialRenderInfo = new MaterialRenderInfo.Default(this.color);
            switch (this.type) {
                case METAL:
                    materialRenderInfo = new MaterialRenderInfo.Metal(this.color);
                    break;
                case METALTEXTURED:
                    materialRenderInfo = new MaterialRenderInfo.MetalTextured(this.texture, this.color, 0.4f, 0.4f, 0.1f);
                    break;
                default:
                    break;
            }
            material.setRenderInfo(materialRenderInfo);
    }

    public boolean registerTinkersMaterial(){
        if (!TinkerRegistry.getMaterial(this.identifier).identifier.equals("unknown")){
            return false;
        }
        Material material = new Material(this.identifier, this.color);
        material.setCastable(this.isCastable)
                .setCraftable(this.isCraftable)
                .addItemIngot(this.identifier);
//        this.setMaterialRenderInfo(material);
        this.registerTinkersFluid();
        TinkerRegistry.addMaterial(material);
        TinkerRegistry.integrate(material);
        return true;
    }

    public boolean registerTinkersFluid(){
        if(!isCastable){
            return false;
        }
        Fluid materialFluid = new TiCFluid(this.identifier, this.color);
        FluidRegistry.registerFluid(materialFluid);
        FluidRegistry.addBucketForFluid(materialFluid);

        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("fluid", materialFluid.getName());
        tag.setString("ore", this.identifier);
        tag.setBoolean("toolforge", true);

        FMLInterModComms.sendMessage(TConstruct.modID, "integrateSmeltery", tag);
        return true;
    }

    public Fluid getFluid(){
        return new TiCFluid(this.identifier, this.color);
    }

    public Block getFluidBlock(){
        return new TiCFluidBlock(getFluid());
    }

    public boolean registerTinkersMaterialStats(Map<String, Property> properties){
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

    public boolean updateTinkersMaterial(){
        Material material = TinkerRegistry.getMaterial(this.identifier);
        if ("unknown".equals(material.identifier)){
            return false;
        }
        material.addItem(this.getItem());
        material.setRepresentativeItem(this.getItemStack());
        return true;
    }

    public boolean registerTinkersMaterialTraits() {
        Material material = TinkerRegistry.getMaterial(this.identifier);
        if ("unknown".equals(material.identifier)) {
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
