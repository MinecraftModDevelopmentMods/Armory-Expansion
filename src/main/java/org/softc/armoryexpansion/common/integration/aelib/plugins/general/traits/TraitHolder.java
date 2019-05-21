package org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits;

public class TraitHolder{
    private String traitName;
    private String traitPart;

    public String getTraitName() {
        return traitName;
    }

    public String getTraitPart() {
        return traitPart;
    }

    public TraitHolder(String traitName, String traitPart) {
        this.traitName = traitName;
        this.traitPart = traitPart;
    }
}
