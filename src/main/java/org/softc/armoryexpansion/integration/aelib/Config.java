package org.softc.armoryexpansion.integration.aelib;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.ITiCMaterial;

import java.util.HashMap;
import java.util.Map;

import static c4.conarm.lib.materials.ArmorMaterialType.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

public class Config{
    private static final String TRAIT = "trait";
    static final String CATEGORY_MATERIAL = "material";
    private static final String CATEGORY_ARMOR = "armor";
    private static final String CATEGORY_TOOL = "tool";
    private static final String CATEGORY_BOW = "bow";

    private Map<String,Map<String, Property>> properties = new HashMap<>();
    private Configuration configuration;

    Config(Configuration configuration) {
        this.configuration = configuration;
    }

    Configuration getConfiguration() {
        return configuration;
    }

    public Property getProperty(String material, String property){
        return getProperties(material).get(property);
    }

    Map<String, Property> getProperties(String material){
        return properties.get(material);
    }

    private Property addMaterialProperty(final ITiCMaterial material) {
        return configuration.get(
                material.getIdentifier(),
                "enable_material",
                "true",
                "Whether " + material.getIdentifier() + " should be available"
        );
    }

    private Property addMaterialPartProperty(final ITiCMaterial material, final String part, final String category) {
        return configuration.get(
                material.getIdentifier() + "." + category,
                "enable_" + part,
                "true",
                "Whether " + material.getIdentifier() + " should be available for " + part
        );
    }

    private Property addMaterialTraitProperty(final ITiCMaterial material){
        return configuration.get(
                material.getIdentifier(),
                "enable_traits",
                "true",
                "Whether " + material.getIdentifier() + " should be assigned traits"
        );
    }

    private void putSubcategoryProperty(Map<String, Property> materialProperties, String subcategory, String part, ITiCMaterial material){
        configuration.getCategory(material.getIdentifier() + "." + subcategory);
        materialProperties.put(part, addMaterialPartProperty(material, part, subcategory));
    }

    void syncConfig(Map<String, ITiCMaterial> materials) { // Gets called from preInit
        try {
            // Load config
            configuration.load();
            for (ITiCMaterial material:materials.values())
            {
                Map<String, Property> materialProperties = new HashMap<>();
                if(!properties.containsKey(material.getIdentifier())){
                    configuration.getCategory(material.getIdentifier());
                    materialProperties.put(CATEGORY_MATERIAL, addMaterialProperty(material));
                    materialProperties.put(TRAIT, addMaterialTraitProperty(material));

                    if(material.isArmorMaterial()){
                        putSubcategoryProperty(materialProperties, CATEGORY_ARMOR, CORE, material);
                        putSubcategoryProperty(materialProperties, CATEGORY_ARMOR, TRIM, material);
                        putSubcategoryProperty(materialProperties, CATEGORY_ARMOR, PLATES, material);
                    }

                    if(material.isToolMaterial()){
                        putSubcategoryProperty(materialProperties, CATEGORY_TOOL, HEAD, material);
                        putSubcategoryProperty(materialProperties, CATEGORY_TOOL, HANDLE, material);
                        putSubcategoryProperty(materialProperties, CATEGORY_TOOL, EXTRA, material);
                    }

                    if(material.isFletchingMaterial()){
                        putSubcategoryProperty(materialProperties, CATEGORY_BOW, FLETCHING, material);
                    }

                    if(material.isBowMaterial()){
                        putSubcategoryProperty(materialProperties, CATEGORY_BOW, BOW, material);
                        putSubcategoryProperty(materialProperties, CATEGORY_BOW, SHAFT, material);
                    }

                    if(material.isProjectileMaterial()){
                        putSubcategoryProperty(materialProperties, CATEGORY_BOW, PROJECTILE, material);
                    }
                }
                properties.put(material.getIdentifier(), materialProperties);
            }
        } catch (final Exception e) {
            // Failed reading/writing, just continue
        } finally {
            // Save props to config IF config changed
            if (configuration.hasChanged()) configuration.save();
        }
    }
}
