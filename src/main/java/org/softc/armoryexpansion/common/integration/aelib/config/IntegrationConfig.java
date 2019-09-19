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

    public boolean materialEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).materialEnabled();
    }

    public boolean fluidEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).fluidEnabled();
    }

    public boolean traitsEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).traitsEnabled();
    }

//    public boolean armorEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).armorEnabled();
//    }
//
//    public boolean coreEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).coreEnabled();
//    }
//
//    public boolean platesEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).platesEnabled();
//    }
//
//    public boolean trimEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).trimEnabled();
//    }
//
//    public boolean toolEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isToolEnabled();
//    }
//
//    public boolean headEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isHeadEnabled();
//    }
//
//    public boolean handleEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isHandleEnabled();
//    }
//
//    public boolean extraEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isExtraEnabled();
//    }
//
//    public boolean rangedEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isRangedEnabled();
//    }
//
//    public boolean bowEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isBowEnabled();
//    }
//
//    public boolean bowStringEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isBowStringEnabled();
//    }
//
//    public boolean shaftEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isShaftEnabled();
//    }
//
//    public boolean fletchingEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isFletchingEnabled();
//    }
//
//    public boolean projectileEnabled(IBasicMaterial material){
//        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isProjectileEnabled();
//    }
}
