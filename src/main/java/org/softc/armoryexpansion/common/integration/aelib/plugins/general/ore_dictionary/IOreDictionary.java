package org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary;

import net.minecraft.item.*;

public interface IOreDictionary {
    String getIdentifier();

    Item getItem(ItemHolder itemHolder);

    ItemStack getItemStack(ItemHolder itemHolder);

    void registerOreDict();

    boolean updateTinkersMaterial(boolean canRegister);
}
