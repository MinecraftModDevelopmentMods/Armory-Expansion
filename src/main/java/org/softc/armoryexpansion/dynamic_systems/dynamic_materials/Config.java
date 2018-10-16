package org.softc.armoryexpansion.dynamic_systems.dynamic_materials;

import net.minecraftforge.common.config.Property;
import org.softc.armoryexpansion.ArmoryExpansion;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.ArrayList;

public final class Config
{
    private static final ArrayList<Property> propertiesMaterials = new ArrayList<>();
    private static final ArrayList<Property> propertiesCore = new ArrayList<>();
    private static final ArrayList<Property> propertiesPlates = new ArrayList<>();
    private static final ArrayList<Property> propertiesTrim = new ArrayList<>();
    private static final ArrayList<Property> propertiesTrait = new ArrayList<>();

    private static final String CORES = "cores";
    private static final String EXTRA = "extra";
    private static final String HANDLE = "handle";
    private static final String HEAD = "head";
    private static final String PLATES = "plates";
    private static final String TRIMS = "trims";

    public static ArrayList<Property> getPropertiesMaterials(){
        return propertiesMaterials;
    }

    private static Property getProperty(ArrayList<Property> list, int index){
        return list.get(index);
    }

    private static Boolean hasProperty(ArrayList<Property> list, int index){
        return list.size() >= index && getProperty(list, index) != null;
    }

    public static Property getMaterialProperty(int index){
        return getProperty(propertiesMaterials, index);
    }

    public static Boolean hasMaterialProperty(int index){
        return hasProperty(propertiesMaterials, index);
    }

    public static Property getCoreProperty(int index){
        return getProperty(propertiesCore, index);
    }

    public static Boolean hasCoreProperty(int index){
        return hasProperty(propertiesCore, index);
    }

    public static Property getPlatesProperty(int index){
        return getProperty(propertiesPlates, index);
    }

    public static Boolean hasPlatesProperty(int index){
        return hasProperty(propertiesPlates, index);
    }

    public static Property getTrimProperty(int index){
        return getProperty(propertiesTrim, index);
    }

    public static Boolean hasTrimProperty(int index){
        return hasProperty(propertiesTrim, index);
    }

    public static Property getTraitProperty(int index){
        return getProperty(propertiesTrait, index);
    }

    public static Boolean hasTraitProperty(int index){
        return hasProperty(propertiesTrait, index);
    }

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

    private static void addTraitProperty(final Material material){

        if (material != null) {
            propertiesTrait.add(
                    ArmoryExpansion.config.get(
                            material.getIdentifier(),
                            "enable_traits",
                            "true",
                            "Whether " + material.getLocalizedName() + " should be assigned traits"
                    )
            );
        }
        else {
            propertiesTrait.add(null);
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
                    addTraitProperty(material);
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
