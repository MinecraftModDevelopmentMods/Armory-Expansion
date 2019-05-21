package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IMaterial;

import java.util.HashMap;
import java.util.Map;

public class IntegrationConfig {
    private static final MaterialConfigOptions DEFAULT = new MaterialConfigOptions();
    private Map<String, MaterialConfigOptions> integrationMaterials = new HashMap<>();

    private void insertMaterialConfigOptions(MaterialConfigOptions materialConfigOptions){
        this.integrationMaterials.putIfAbsent(materialConfigOptions.getName(), materialConfigOptions);
    }

    public MaterialConfigOptions getSafeMaterialConfigOptions(String identifier){
        return this.integrationMaterials.getOrDefault(identifier, DEFAULT);
    }

    public Map<String, MaterialConfigOptions> getIntegrationMaterials() {
        return integrationMaterials;
    }

    public void syncConfig(Map<String, IMaterial> materials) {
        materials.values().forEach(m -> this.insertMaterialConfigOptions(new MaterialConfigOptions(m)));
    }

    public boolean isMaterialEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isMaterialEnabled();
    }

    public boolean isFluidEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isFluidEnabled();
    }

    public boolean isTraitsEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isTraitsEnabled();
    }

    public boolean isArmorEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isArmorEnabled();
    }

    public boolean isCoreEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isCoreEnabled();
    }

    public boolean isPlatesEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isPlatesEnabled();
    }

    public boolean isTrimEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isTrimEnabled();
    }

    public boolean isToolEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isToolEnabled();
    }

    public boolean isHeadEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isHeadEnabled();
    }

    public boolean isHandleEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isHandleEnabled();
    }

    public boolean isExtraEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isExtraEnabled();
    }

    public boolean isRangedEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isRangedEnabled();
    }

    public boolean isBowEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isBowEnabled();
    }

    public boolean isBowStringEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isBowStringEnabled();
    }

    public boolean isShaftEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isShaftEnabled();
    }

    public boolean isFletchingEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isFletchingEnabled();
    }

    public boolean isProjectileEnabled(IMaterial material){
        return this.getSafeMaterialConfigOptions(material.getIdentifier()).isProjectileEnabled();
    }
}
