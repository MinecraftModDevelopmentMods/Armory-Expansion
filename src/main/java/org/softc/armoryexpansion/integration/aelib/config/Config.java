package org.softc.armoryexpansion.integration.aelib.config;

import org.softc.armoryexpansion.integration.plugins.tinkers_construct.material.ITiCMaterial;

import java.util.HashMap;
import java.util.Map;

public class Config{
    private Map<String, MaterialConfigOptions> integrationMaterials = new HashMap<>();

    public Config() {
        this.integrationMaterials.put("DEFAULT", new MaterialConfigOptions());
    }

    private void insertMaterialConfigOptions(MaterialConfigOptions materialConfigOptions){
        this.integrationMaterials.putIfAbsent(materialConfigOptions.getName(), materialConfigOptions);
    }

    public MaterialConfigOptions getSafeMaterialConfigOptions(String identifier){
        return this.integrationMaterials.getOrDefault(identifier, this.integrationMaterials.get("DEFAULT"));
    }

    public void syncConfig(Map<String, ITiCMaterial> materials) {
        materials.values().forEach(m -> this.insertMaterialConfigOptions(new MaterialConfigOptions(m.getIdentifier())));
    }

    public boolean isMaterialEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isMaterialEnabled();
    }

    public boolean isFluidEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isFluidEnabled();
    }

    public boolean isTraitsEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isTraitsEnabled();
    }

    public boolean isArmorEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isArmorEnabled();
    }

    public boolean isCoreEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isCoreEnabled();
    }

    public boolean isPlatesEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isPlatesEnabled();
    }

    public boolean isTrimEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isTrimEnabled();
    }

    public boolean isToolEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isToolEnabled();
    }

    public boolean isHeadEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isHeadEnabled();
    }

    public boolean isHandleEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isHandleEnabled();
    }

    public boolean isExtraEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isExtraEnabled();
    }

    public boolean isRangedEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isRangedEnabled();
    }

    public boolean isBowEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isBowEnabled();
    }

    public boolean isShaftEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isShaftEnabled();
    }

    public boolean isFletchingEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isFletchingEnabled();
    }

    public boolean isProjectileEnabled(String identifier){
        return this.getSafeMaterialConfigOptions(identifier).isProjectileEnabled();
    }
}
