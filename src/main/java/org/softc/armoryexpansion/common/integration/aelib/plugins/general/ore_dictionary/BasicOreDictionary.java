package org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.util.Arrays;

public class BasicOreDictionary implements IOreDictionary {
    private String identifier;
    private ItemHolder[] entries;

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Item getItem(ItemHolder itemHolder) {
        return itemHolder != null ? Item.getByNameOrId(itemHolder.getItemName()) : null;
    }

    @Override
    public ItemStack getItemStack(ItemHolder itemHolder) {
        return this.getItem(itemHolder) != null ? new ItemStack(this.getItem(itemHolder), 1, itemHolder.getMeta()) : null;
    }

    @Override
    public void registerOreDict() {
        for (ItemHolder itemHolder : entries) {
            ItemStack stack = this.getItemStack(itemHolder);
            if (stack != null) {
                OreDictionary.registerOre(this.getIdentifier(), stack);
            }
        }
    }

    @Override
    public boolean updateTinkersMaterial(boolean canRegister){
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
        if ("unknown".equals(material.identifier) || !canRegister) {
            return false;
        }
        for (ItemHolder itemHolder : entries) {
            material.addItem(this.getItem(itemHolder));
        }
        material.setRepresentativeItem(this.getItemStack(Arrays.stream(entries).findFirst().orElse(null)));
        return true;
    }
}
