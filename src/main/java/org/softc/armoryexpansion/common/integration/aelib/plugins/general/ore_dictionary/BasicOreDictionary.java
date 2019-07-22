package org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;

public class BasicOreDictionary implements IOreDictionary {
    private String identifier;
    private String itemName;
    private int meta;

    @Override
    public String getIdentifier() {
        return this.identifier;
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
    public void registerOreDict() {
        ItemStack stack = this.getItemStack();
        if(stack != null){
            OreDictionary.registerOre(this.getIdentifier(), this.getItemStack());
        }
    }

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
}
