package org.softc.armoryexpansion.common.integration.aelib.plugins.general.oredictionary;

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
