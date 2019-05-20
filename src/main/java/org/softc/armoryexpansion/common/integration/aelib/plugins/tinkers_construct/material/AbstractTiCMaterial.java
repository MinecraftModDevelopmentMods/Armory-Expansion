package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;

public abstract class AbstractTiCMaterial implements ITiCMaterial {
    // TODO Add a localization key
    protected String identifier;
    protected String itemName;
    protected int meta;
    public int color;
    public MaterialRenderType type = MaterialRenderType.DEFAULT;
    public ResourceLocation texture;

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

    public int getColor() {
        return color;
    }

    public String getIdentifier() {
        return identifier;
    }

    protected Item getItem() {
        return itemName != null ? Item.getByNameOrId(itemName) : null;
    }

    protected ItemStack getItemStack() {
        return this.getItem() != null ? new ItemStack(this.getItem(), 1, this.meta) : null;
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

    @Override
    public boolean isCastable() {
        return isCastable;
    }

    @Override
    public boolean isCraftable() {
        return isCraftable;
    }

    @Override
    public boolean isToolMaterial() {
        return isToolMaterial;
    }

    @Override
    public boolean isBowMaterial() {
        return isBowMaterial;
    }

    @Override
    public boolean isFletchingMaterial() {
        return isFletchingMaterial;
    }

    @Override
    public boolean isProjectileMaterial() {
        return isProjectileMaterial;
    }

    @Override
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
