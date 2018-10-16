package org.softc.armoryexpansion.dynamic_systems.dynamic_materials.constructs_armory;

import org.softc.armoryexpansion.dynamic_systems.dynamic_materials.Config;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import static org.softc.armoryexpansion.dynamic_systems.dynamic_materials.constructs_armory.StatRegistration.handleArmorStats;
import static org.softc.armoryexpansion.dynamic_systems.dynamic_materials.constructs_armory.TraitRegistration.handleArmorTraits;

public class ConArmIntegration {
    public static void integrateMaterialsFromConfig() {
        for (int i = 0; i < Config.getPropertiesMaterials().size(); i++) {
            final Material material = TinkerRegistry.getMaterial(Config.getMaterialProperty(i).getName().replaceAll("enable_", ""));
            handleArmorStats(i, material);
            handleArmorTraits(i, material);
        }
    }
}
