package org.softc.armoryexpansion.integration.plugins.tinkers_construct;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractTiCMaterial implements ITiCMaterial {
    // TODO Add a localization key
    protected String identifier;
    protected String itemName;
    protected int meta = 0;
    protected int color;
    protected MaterialRenderType type = MaterialRenderType.DEFAULT;
    protected ResourceLocation texture;

    protected boolean isCastable = false;
    protected boolean isCraftable = false;

    protected boolean isToolMaterial = false;
    protected boolean isBowMaterial = false;
    protected boolean isFletchingMaterial = false;
    protected boolean isProjectileMaterial = false;
    protected boolean isArmorMaterial = false;

    public AbstractTiCMaterial(String identifier, String itemName, int color) {
        this(identifier, itemName, 0, color);
    }

    public AbstractTiCMaterial(String identifier, String itemName, int meta, int color) {
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

    protected Item getItem() {
        return itemName != null ? Item.getByNameOrId(itemName) : null;
    }

    protected ItemStack getItemStack() {
        return this.getItem() != null ? new ItemStack(this.getItem(), 1, this.meta) : null;
    }

    public int getColor() {
        return color;
    }

    public MaterialRenderType getType() {
        return type;
    }

    public AbstractTiCMaterial setType(MaterialRenderType type) {
        this.type = type;
        return this;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public AbstractTiCMaterial setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    public AbstractTiCMaterial setTexture(String texture) {
        this.texture = new ResourceLocation(texture);
        return this;
    }

    public boolean isCastable() {
        return isCastable;
    }

    public AbstractTiCMaterial setCastable(boolean castable) {
        isCastable = castable;
        return this;
    }

    public boolean isCraftable() {
        return isCraftable;
    }

    public AbstractTiCMaterial setCraftable(boolean craftable) {
        isCraftable = craftable;
        return this;
    }

    public boolean isToolMaterial() {
        return isToolMaterial;
    }

    public AbstractTiCMaterial setToolMaterial(boolean toolMaterial) {
        isToolMaterial = toolMaterial;
        return this;
    }

    public boolean isBowMaterial() {
        return isBowMaterial;
    }

    public AbstractTiCMaterial setBowMaterial(boolean bowMaterial) {
        isBowMaterial = bowMaterial;
        return this;
    }

    public boolean isFletchingMaterial() {
        return isFletchingMaterial;
    }

    public AbstractTiCMaterial setFletchingMaterial(boolean fletchingMaterial) {
        isFletchingMaterial = fletchingMaterial;
        return this;
    }

    public boolean isProjectileMaterial() {
        return isProjectileMaterial;
    }

    public AbstractTiCMaterial setProjectileMaterial(boolean projectileMaterial) {
        isProjectileMaterial = projectileMaterial;
        return this;
    }

    public boolean isArmorMaterial() {
        return isArmorMaterial;
    }

    public AbstractTiCMaterial setArmorMaterial(boolean armorMaterial) {
        isArmorMaterial = armorMaterial;
        return this;
    }

    @Override
    public boolean equals(Object material) {
        return material instanceof AbstractTiCMaterial && this.getIdentifier().equals(((AbstractTiCMaterial) material).getIdentifier());
    }
}
