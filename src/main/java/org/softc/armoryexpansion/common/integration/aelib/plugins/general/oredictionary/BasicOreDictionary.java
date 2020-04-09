package org.softc.armoryexpansion.common.integration.aelib.plugins.general.oredictionary;

import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import slimeknights.tconstruct.library.*;

import java.util.*;

public class BasicOreDictionary implements IOreDictionary {
    protected String identifier;
    protected ItemHolder[] entries;

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public Item getItem(ItemHolder itemHolder) {
        //noinspection ReturnOfNull
        return null != itemHolder ? Item.getByNameOrId(itemHolder.getItemName()) : null;
    }

    @Override
    public ItemStack getItemStack(ItemHolder itemHolder) {
        //noinspection ReturnOfNull
        return null != this.getItem(itemHolder) ? new ItemStack(this.getItem(itemHolder), 1, itemHolder.getMeta()) : null;
    }

    @Override
    public void registerOreDict() {
        for (ItemHolder itemHolder : this.entries) {
            ItemStack stack = this.getItemStack(itemHolder);
            if (null != stack) {
                OreDictionary.registerOre(this.identifier, stack);
            }
        }
    }

    @Override
    public boolean updateTinkersMaterial(boolean canRegister){
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.identifier);
        if (!"unknown".equals(material.identifier) && canRegister) {
            for (ItemHolder itemHolder : this.entries) {
                material.addItem(this.getItem(itemHolder));
            }
            material.setRepresentativeItem(this.getItemStack(Arrays.stream(this.entries).findFirst().orElse(null)));
            return true;
        }
        return false;
    }
}
