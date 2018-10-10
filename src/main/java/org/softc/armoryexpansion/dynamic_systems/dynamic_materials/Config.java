package org.softc.armoryexpansion.dynamic_systems.dynamic_materials;

import net.minecraftforge.common.config.Property;
import org.softc.armoryexpansion.ArmoryExpansion;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.ArrayList;

public final class Config
{
    static final ArrayList<Property> propertiesMaterials = new ArrayList<>();
    static final ArrayList<Property> propertiesCore = new ArrayList<>();
    static final ArrayList<Property> propertiesPlates = new ArrayList<>();
    static final ArrayList<Property> propertiesTrim = new ArrayList<>();

    private static final String CORES = "cores";
    private static final String EXTRA = "extra";
    private static final String HANDLE = "handle";
    private static final String HEAD = "head";
    private static final String PLATES = "plates";
    private static final String TRIMS = "trims";
    
    private static void addMaterialProperty(final Material material, final String part) {
    	ArrayList<Property> properties = new ArrayList<>();
        switch (part) {
            case CORES:
                properties = propertiesCore;
                break;
            case PLATES:
                properties = propertiesPlates;
                break;
            case TRIMS:
                properties = propertiesTrim;
                break;
            default:
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
                final boolean core = !material.hasStats("core") && material.hasStats(HEAD);
                final boolean plates = !material.hasStats(PLATES) && material.hasStats(HANDLE);
                final boolean trim = !material.hasStats("trim") && material.hasStats(EXTRA);
                final boolean mat = core || plates || trim;
                if (mat) {
                    ArmoryExpansion.config.getCategory(material.getIdentifier());
                    propertiesMaterials.add(ArmoryExpansion.config.get(material.getIdentifier(), "enable_" + material.getIdentifier(), "true", "Global toggle for the " + material.getLocalizedName() + " material"));
                    if (core) {
                        addMaterialProperty(material, CORES);
                    }
                    else {
                        addMaterialProperty(null, CORES);
                    }
                    if (plates) {
                        addMaterialProperty(material, PLATES);
                    }
                    else {
                        addMaterialProperty(null, PLATES);
                    }
                    if (trim) {
                        addMaterialProperty(material, TRIMS);
                    }
                    else {
                        addMaterialProperty(null, TRIMS);
                    }
                }
            }
        } catch (final Exception e) {
            // Failed reading/writing, just continue
        } finally {
            // Save props to config IF config changed
            if (ArmoryExpansion.config.hasChanged()) ArmoryExpansion.config.save();
        }
    }
}
