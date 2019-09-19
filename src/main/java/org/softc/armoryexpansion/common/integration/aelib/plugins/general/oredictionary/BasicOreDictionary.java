package org.softc.armoryexpansion.common.integration.aelib.plugins.general.oredictionary;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.util.Arrays;

public class BasicOreDictionary implements IOreDictionary {
    protected String identifier;
    protected ItemHolder[] entries;

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public @Nullable Item getItem(ItemHolder itemHolder) {
        return null != itemHolder ? Item.getByNameOrId(itemHolder.getItemName()) : null;
    }

    @Override
    public @Nullable ItemStack getItemStack(ItemHolder itemHolder) {
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
