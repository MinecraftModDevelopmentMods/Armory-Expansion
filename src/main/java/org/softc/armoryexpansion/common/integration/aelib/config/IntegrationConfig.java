package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;

import java.util.HashMap;
import java.util.Map;

public class IntegrationConfig {
    private static MaterialConfigOptions defaultMaterialConfigOptions;
    private Map<String, MaterialConfigOptions> integrationMaterials = new HashMap<>();

    private static MaterialConfigOptions getDefault(){
        if (null == defaultMaterialConfigOptions) {
            defaultMaterialConfigOptions = new MaterialConfigOptions();
        }
        return defaultMaterialConfigOptions;
    }

    public void insertMaterialConfigOptions(MaterialConfigOptions materialConfigOptions){
//        this.integrationMaterials.putIfAbsent(materialConfigOptions.getName(), materialConfigOptions);
        if(!this.integrationMaterials.containsKey(materialConfigOptions.name)){
            this.integrationMaterials.put(materialConfigOptions.getName(), materialConfigOptions);
        }
    }

    public MaterialConfigOptions getSafeMaterialConfigOptions(String identifier){
        return this.integrationMaterials.getOrDefault(identifier, getDefault());
    }

    public Map<String, MaterialConfigOptions> getIntegrationMaterials() {
        return null == this.integrationMaterials ? new HashMap<>() : this.integrationMaterials;
    }

    public void syncConfig(Map<String, ? extends IBasicMaterial> materials) {
        materials.values().forEach(material -> this.insertMaterialConfigOptions(new MaterialConfigOptions(material)));
    }

    public boolean materialEnabled(String material){
        return this.getSafeMaterialConfigOptions(material).materialEnabled();
    }

    public boolean fluidEnabled(String material){
        return this.getSafeMaterialConfigOptions(material).fluidEnabled();
    }

    public boolean traitsEnabled(String material){
        return this.getSafeMaterialConfigOptions(material).traitsEnabled();
    }
}
