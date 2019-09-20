package org.softc.armoryexpansion.common.integration.aelib.plugins.general.traits;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.List;

public class MaterialTraits {
    protected String identifier;
    protected List<TraitHolder> traits;

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<TraitHolder> getTraits() {
        return this.traits;
    }

    public void setTraits(List<TraitHolder> traits) {
        this.traits = traits;
    }

    public boolean registerTinkersMaterialTraits(boolean canRegister) {
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.identifier);
        if ("unknown".equals(material.identifier) || !canRegister) {
            return false;
        }
        this.traits.forEach(t -> {
            t.getTraitNames().forEach(name -> {
                ITrait trait = TinkerRegistry.getTrait(name);
                if(null != trait){
                    material.addTrait(trait, t.getTraitPart());
                }
            });
        });
        TinkerRegistry.integrate(material);
        return !this.traits.isEmpty();
    }
}
