package org.softc.armoryexpansion.common.integration.aelib.plugins.general.ore_dictionary;

public class ItemHolder implements IItemHolder {
    protected String itemName;
    protected int meta;

    public String getItemName() {
        return this.itemName;
    }

    public int getMeta() {
        return this.meta;
    }
}
