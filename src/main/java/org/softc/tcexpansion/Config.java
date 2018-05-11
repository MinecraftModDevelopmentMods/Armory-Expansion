package org.softc.tcexpansion;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.ArrayList;

class Config
{
    static ArrayList<Property> properties = new ArrayList<>();

    static void syncConfig() { // Gets called from preInit
        try {
            // Load config
            TCE.config.load();

            for (Material material:TinkerRegistry.getAllMaterials())
            {
                if (!material.hasStats("core") && material.hasStats("head") && !material.hasStats("plates") && material.hasStats("handle") && !material.hasStats("trim") && material.hasStats("extra"))
                {
                    properties.add(
                            TCE.config.get(
                                    Configuration.CATEGORY_GENERAL,
                                    material.identifier,
                                    "true",
                                    "Whether " + material.getLocalizedName() + " should be used for armor parts"
                            )
                    );
                }
            }
        } catch (Exception e) {
            // Failed reading/writing, just continue
        } finally {
            // Save props to config IF config changed
            if (TCE.config.hasChanged()) TCE.config.save();
        }
    }
}