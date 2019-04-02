package org.softc.armoryexpansion.integration.tinkers_construct;

import c4.conarm.lib.materials.ArmorMaterialType;
import com.mcmoddev.lib.integration.plugins.tinkers.TinkerTraitLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.softc.armoryexpansion.integration.constructs_armory.ConArmStats;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.LinkedList;
import java.util.List;

public class TiCMaterial {
    private String identifier;
    private String itemName;
    private int meta = 0;
    private int color;
    private MaterialRenderType type = MaterialRenderType.DEFAULT;
    private ResourceLocation texture;
    private int durability = 0;
    private float hardness = 0;
    private float damage = 0;
    private float magicaffinity = 0;
    private int harvestLevel = 0;
    private float range = 0;
    private float accuracy = 0;
    private float defense = 0;
    private float toughness = 0;

    private List<Tuple<String, String>> traits = new LinkedList<>();

    private boolean isCastable = false;
    private boolean isCraftable = false;

    private boolean isToolMaterial = false;
    private boolean isBowMaterial = false;
    private boolean isFletchingMaterial = false;
    private boolean isProjectileMaterial = false;
    private boolean isArmorMaterial = false;


    public TiCMaterial(String identifier, String itemName, int color) {
        this(identifier, itemName, 0, color);
    }

    public TiCMaterial(String identifier, String itemName, int meta, int color) {
        this.identifier = identifier;
        this.itemName = itemName;
        this.meta = meta;
        this.color = color;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getItemName() {
        return itemName;
    }

    private Item getItem() {
        return Item.getByNameOrId(itemName);
    }

    private ItemStack getItemStack() {
        return new ItemStack(this.getItem(), 1, this.meta);
    }

    public int getColor() {
        return color;
    }

    public MaterialRenderType getType() {
        return type;
    }

    public TiCMaterial setType(MaterialRenderType type) {
        this.type = type;
        return this;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public TiCMaterial setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public TiCMaterial setTexture(String texture) {
        this.texture = new ResourceLocation(texture);
        return this;
    }

    public int getDurability() {
        return durability;
    }

    public TiCMaterial setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    float getHardness() {
        return hardness;
    }

    public TiCMaterial setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    float getDamage() {
        return damage;
    }

    public TiCMaterial setDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public float getMagicaffinity() {
        return magicaffinity;
    }

    public TiCMaterial setMagicaffinity(float magicaffinity) {
        this.magicaffinity = magicaffinity;
        return this;
    }

    int getHarvestLevel() {
        return harvestLevel;
    }

    public TiCMaterial setHarvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    float getRange() {
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
        this.traits.add(new Tuple<>(trait, location));
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
        this.addTrait(trait, TinkerTraitLocation.HEAD.name());
        return this;
    }

    public TiCMaterial addSecondaryToolTrait(String trait) {
        this.addTrait(trait, TinkerTraitLocation.HANDLE.name());
        this.addTrait(trait, TinkerTraitLocation.EXTRA.name());
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

    public boolean isCastable() {
        return isCastable;
    }

    public TiCMaterial setCastable(boolean castable) {
        isCastable = castable;
        return this;
    }

    public boolean isCraftable() {
        return isCraftable;
    }

    public TiCMaterial setCraftable(boolean craftable) {
        isCraftable = craftable;
        return this;
    }

    private boolean isToolMaterial() {
        return isToolMaterial;
    }

    public TiCMaterial setToolMaterial(boolean toolMaterial) {
        isToolMaterial = toolMaterial;
        return this;
    }

    private boolean isBowMaterial() {
        return isBowMaterial;
    }

    public TiCMaterial setBowMaterial(boolean bowMaterial) {
        isBowMaterial = bowMaterial;
        return this;
    }

    private boolean isFletchingMaterial() {
        return isFletchingMaterial;
    }

    public TiCMaterial setFletchingMaterial(boolean fletchingMaterial) {
        isFletchingMaterial = fletchingMaterial;
        return this;
    }

    private boolean isProjectileMaterial() {
        return isProjectileMaterial;
    }

    public TiCMaterial setProjectileMaterial(boolean projectileMaterial) {
        isProjectileMaterial = projectileMaterial;
        return this;
    }

    private boolean isArmorMaterial() {
        return isArmorMaterial;
    }

    public TiCMaterial setArmorMaterial(boolean armorMaterial) {
        isArmorMaterial = armorMaterial;
        return this;
    }

    public TiCMaterial registerOreDict() {
        OreDictionary.registerOre(this.identifier, this.getItemStack());
        return this;
    }

    @SideOnly(Side.CLIENT)
    private void setMaterialRenderInfo(Material material) {
            MaterialRenderInfo materialRenderInfo = new MaterialRenderInfo.Default(this.color);
            switch (this.type) {
                case METAL:
                    materialRenderInfo = new MaterialRenderInfo.Metal(this.color);
                    break;
                case METALTEXTURED:
                    materialRenderInfo = new MaterialRenderInfo.MetalTextured(this.texture, this.color, 0.4f, 0.4f, 0.1f);
                    break;
            }
            material.setRenderInfo(materialRenderInfo);
    }

    public void registerTinkersMaterial(){
        if (!TinkerRegistry.getMaterial(this.identifier).identifier.equals("unknown")){
            return;
        }
        Material material = new Material(this.identifier, this.color);
        material.setCastable(this.isCastable)
                .setCraftable(this.isCraftable)
                .addItemIngot(this.identifier);
//        this.setMaterialRenderInfo(material);
        TinkerRegistry.addMaterial(material);
        TinkerRegistry.integrate(material);
    }

    public void registerTinkersMaterialStats(){
        if (this.isToolMaterial()) {
            TiCStats.registerMaterialToolStats(this);
        }
        if (this.isBowMaterial()) {
            TiCStats.registerMaterialBowStats(this);
        }
        if (this.isFletchingMaterial()) {
            TiCStats.registerMaterialFletchingStats(this);
        }
        if (this.isProjectileMaterial()) {
            TiCStats.registerMaterialProjectileStats(this);
        }
        if (this.isArmorMaterial()) {
            ConArmStats.registerMaterialArmorStats(this);
        }
    }

    public void updateTinkersMaterial(){
        Material material = TinkerRegistry.getMaterial(this.identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        material.addItem(this.getItem());
        material.setRepresentativeItem(this.getItemStack());
    }

    public void registerTinkersMaterialTraits() {
        Material material = TinkerRegistry.getMaterial(this.identifier);
        if (material.identifier.equals("unknown")) {
            return;
        }
        this.traits.forEach( t -> {
            ITrait trait = TinkerRegistry.getTrait(t.getFirst());
            material.addTrait(trait, t.getSecond());
        });
        TinkerRegistry.integrate(material);
    }
}
