package org.softc.armoryexpansion.dynamic_systems.dynamic_materials;

import net.minecraftforge.common.config.Property;
import org.softc.armoryexpansion.ArmoryExpansion;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.ArrayList;

public class Config
{
    static ArrayList<Property> propertiesMaterials = new ArrayList<>(),
            propertiesCore = new ArrayList<>(),
            propertiesPlates = new ArrayList<>(),
            propertiesTrim = new ArrayList<>();

    private static void addMaterialProperty(Material material, String part) {
        ArrayList<Property> properties = new ArrayList<>();
        switch (part) {
            case "cores":
                properties = propertiesCore;
                break;
            case "plates":
                properties = propertiesPlates;
                break;
            case "trims":
                properties = propertiesTrim;
                break;
        }
        if (material != null) {
            properties.add(
                    ArmoryExpansion.config.get(
                            material.getIdentifier(),
                            "enable_" + part,
                            "true",
                            "Whether " + material.getLocalizedName() + " should be used for " + part
                    )
            );
        }
        else {
            properties.add(null);
        }
    }

    public static void syncConfig() { // Gets called from preInit
        try {
            // Load config
            ArmoryExpansion.config.load();

            for (Material material:TinkerRegistry.getAllMaterials())
            {
                boolean core = !material.hasStats("core") && material.hasStats("head"),
                        plates = !material.hasStats("plates") && material.hasStats("handle"),
                        trim = !material.hasStats("trim") && material.hasStats("extra"),
                        mat = core || plates || trim;
                if (mat){
                    ArmoryExpansion.config.getCategory(material.getIdentifier());
                    propertiesMaterials.add(ArmoryExpansion.config.get(material.getIdentifier(), "enable_" + material.getIdentifier(), "true", "Global toggle for the " + material.getLocalizedName() + " material"));
                    if (core){
                        addMaterialProperty(material, "cores");
                    }
                    else {
                        addMaterialProperty(null, "cores");
                    }
                    if (plates) {
                        addMaterialProperty(material, "plates");
                    }
                    else {
                        addMaterialProperty(null, "plates");
                    }
                    if (trim) {
                        addMaterialProperty(material, "trims");
                    }
                    else {
                        addMaterialProperty(null, "trims");
                    }
                }
            }
        } catch (Exception e) {
            // Failed reading/writing, just continue
        } finally {
            // Save props to config IF config changed
            if (ArmoryExpansion.config.hasChanged()) ArmoryExpansion.config.save();
        }
    }
}