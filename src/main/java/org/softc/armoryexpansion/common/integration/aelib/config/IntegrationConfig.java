package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;

import java.util.HashMap;
import java.util.Map;

public class IntegrationConfig {
    private static MaterialConfigOptions DEFAULT;
    private Map<String, MaterialConfigOptions> integrationMaterials = new HashMap<>();

    private static MaterialConfigOptions getDefault(){
        if (DEFAULT == null) {
            DEFAULT = new MaterialConfigOptions();
        }
        return DEFAULT;
    }

    private void insertMaterialConfigOptions(MaterialConfigOptions materialConfigOptions){
        this.integrationMaterials.putIfAbsent(materialConfigOptions.getName(), materialConfigOptions);
    }

    public MaterialConfigOptions getSafeMaterialConfigOptions(String identifier){
        return this.integrationMaterials.getOrDefault(identifier, getDefault());
    }

    public Map<String, MaterialConfigOptions> getIntegrationMaterials() {
        return integrationMaterials == null ? new HashMap<>() : integrationMaterials;
    }

    public void syncConfig(Map<String, IBasicMaterial> materials) {
        materials.values().forEach(m -> this.insertMaterialConfigOptions(new MaterialConfigOptions(m)));
    }

    public boolean isMaterialEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isMaterialEnabled();
    }

    public boolean isFluidEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isFluidEnabled();
    }

    public boolean isTraitsEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isTraitsEnabled();
    }

    public boolean isArmorEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isArmorEnabled();
    }

    public boolean isCoreEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isCoreEnabled();
    }

    public boolean isPlatesEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isPlatesEnabled();
    }

    public boolean isTrimEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isTrimEnabled();
    }

    public boolean isToolEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isToolEnabled();
    }

    public boolean isHeadEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isHeadEnabled();
    }

    public boolean isHandleEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isHandleEnabled();
    }

    public boolean isExtraEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isExtraEnabled();
    }

    public boolean isRangedEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isRangedEnabled();
    }

    public boolean isBowEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isBowEnabled();
    }

    public boolean isBowStringEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isBowStringEnabled();
    }

    public boolean isShaftEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isShaftEnabled();
    }

    public boolean isFletchingEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isFletchingEnabled();
    }

    public boolean isProjectileEnabled(IBasicMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isProjectileEnabled();
    }
}
