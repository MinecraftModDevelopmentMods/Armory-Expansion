package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.*;

import java.util.*;

public class IntegrationConfig {
    private final Map<String, MaterialConfigOptions> integrationMaterials = new HashMap<>();

    private enum DefaultMaterialConfigOptionsHolder {
        ;
        static final MaterialConfigOptions defaultMaterialConfigOptions = new MaterialConfigOptions();
    }

    private static MaterialConfigOptions getDefault(){
        return DefaultMaterialConfigOptionsHolder.defaultMaterialConfigOptions;
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
