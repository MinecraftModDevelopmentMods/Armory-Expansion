package org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits;

public class TraitHolder implements ITraitHolder {
    private String traitName;
    private String traitPart;

    public String getTraitName() {
        return this.traitName;
    }

    public String getTraitPart() {
        return this.traitPart;
    }

    public TraitHolder(String name, String part) {
        this.traitName = name;
        this.traitPart = part;
    }
}
