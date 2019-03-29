package org.softc.armoryexpansion.integration.util.tinkers_construct;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

public class TiCMaterial {
    private String identifier;
    private String itemName;
    private int color;
    private int durability;
    private float hardness;
    private float damage;
    private float magicaffinity;
    private int harvestLevel;
    private float range;

    private boolean isToolMaterial = false;
    private boolean isBowMaterial = false;
    private boolean isFletchingMaterial = false;
    private boolean isArmorMaterial = false;

    public TiCMaterial(String identifier, String itemName, int color) {
        this.identifier = identifier;
        this.itemName = itemName;
        this.color = color;
    }

    String getIdentifier() {
        return identifier;
    }

    public String getItemName() {
        return itemName;
    }

    private Item getItem() {
        return Item.getByNameOrId(itemName);
    }

    public int getColor() {
        return color;
    }

    int getDurability() {
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

    float getMagicaffinity() {
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

    public boolean isToolMaterial() {
        return isToolMaterial;
    }

    public TiCMaterial setToolMaterial(boolean toolMaterial) {
        isToolMaterial = toolMaterial;
        return this;
    }

    public boolean isBowMaterial() {
        return isBowMaterial;
    }

    public TiCMaterial setBowMaterial(boolean bowMaterial) {
        isBowMaterial = bowMaterial;
        return this;
    }

    public boolean isFletchingMaterial() {
        return isFletchingMaterial;
    }

    public TiCMaterial setFletchingMaterial(boolean fletchingMaterial) {
        isFletchingMaterial = fletchingMaterial;
        return this;
    }

    public boolean isArmorMaterial() {
        return isArmorMaterial;
    }

    public TiCMaterial setArmorMaterial(boolean armorMaterial) {
        isArmorMaterial = armorMaterial;
        return this;
    }

    public TiCMaterial registerOreDict() {
        OreDictionary.registerOre(this.identifier, this.getItem());
        return this;
    }

    public void registerTinkersMaterial(){
        if (!TinkerRegistry.getMaterial(this.identifier).identifier.equals("unknown")){
            return;
        }
        Material material = new Material(this.identifier, this.color);
        material.setCastable(false)
                .setCraftable(true)
                .addItemIngot(this.identifier);
        TinkerRegistry.addMaterial(material);
    }

    public void updateTinkersMaterial(){
        Material material = TinkerRegistry.getMaterial(this.identifier);
        if (material.identifier.equals("unknown")){
            return;
        }
        material.addItem(this.getItem());
        material.setRepresentativeItem(this.getItem());
    }
}
