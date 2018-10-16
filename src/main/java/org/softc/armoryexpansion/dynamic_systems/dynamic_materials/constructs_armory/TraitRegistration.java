package org.softc.armoryexpansion.dynamic_systems.dynamic_materials.constructs_armory;

import org.softc.armoryexpansion.dynamic_systems.dynamic_materials.Config;
import slimeknights.tconstruct.library.materials.Material;

public class TraitRegistration {
    public static Boolean handleArmorTraits(int index, Material material){
        if (Config.hasMaterialProperty(index) && Config.getMaterialProperty(index).getBoolean()){
            //TODO Auto assign armor traits based on tool traits
            return true;
        }
        return false;
    }
}
