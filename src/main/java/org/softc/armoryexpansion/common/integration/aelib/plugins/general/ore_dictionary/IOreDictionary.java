package org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IOreDictionary {
    String getIdentifier();

    Item getItem(ItemHolder itemHolder);

    ItemStack getItemStack(ItemHolder itemHolder);

    void registerOreDict();

    boolean updateTinkersMaterial(boolean canRegister);
}
