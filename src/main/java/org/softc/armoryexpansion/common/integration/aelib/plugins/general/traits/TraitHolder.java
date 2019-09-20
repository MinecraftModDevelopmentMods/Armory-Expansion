package org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits;

import java.util.List;

public class TraitHolder implements ITraitHolder {
    private List<String> traitNames;
    private String traitPart;

    public List<String> getTraitNames() {
        return this.traitNames;
    }

    public String getTraitPart() {
        return this.traitPart;
    }

    public TraitHolder(List<String> names, String part) {
        this.traitNames = names;
        this.traitPart = part;
    }
}
