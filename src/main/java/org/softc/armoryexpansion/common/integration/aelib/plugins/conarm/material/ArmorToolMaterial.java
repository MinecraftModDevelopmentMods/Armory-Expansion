package org.softc.armoryexpansion.common.integration.aelib.plugins.conarm.material;

import org.softc.armoryexpansion.common.integration.aelib.config.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tconstruct.material.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class ArmorToolMaterial extends ArmorMaterial implements IToolMaterial {
    protected HeadMaterialStats headMaterialStats;
    protected HandleMaterialStats handleMaterialStats;
    protected ExtraMaterialStats extraMaterialStats;

    ArmorToolMaterial(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public HeadMaterialStats getHeadMaterialStats() {
        return this.headMaterialStats;
    }

    @Override
    public HandleMaterialStats getHandleMaterialStats() {
        return this.handleMaterialStats;
    }

    @Override
    public ExtraMaterialStats getExtraMaterialStats() {
        return this.extraMaterialStats;
    }

    @Override
    public boolean isToolMaterial() {
        return null != this.headMaterialStats
                || null != this.handleMaterialStats
                || null != this.extraMaterialStats;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//        if (!"unknown".equals(material.getIdentifier())) {
            if (properties.materialEnabled()) {
                this.registerArmorStats(material, properties);
                this.registerToolStats(material, properties);
                return true;
            }
//        }
        return false;
    }

    void registerToolStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(this.isToolMaterial()){
            this.registerHeadStats(material, properties);
            this.registerHandleStats(material, properties);
            this.registerExtraStats(material, properties);
        }
    }

    private void registerHeadStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.HEAD) && null != this.headMaterialStats && properties.isHeadEnabled()){
            TinkerRegistry.addMaterialStats(material, this.headMaterialStats);
        }
    }

    private void registerHandleStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.HANDLE) && null != this.handleMaterialStats && properties.isHandleEnabled()){
            TinkerRegistry.addMaterialStats(material, this.handleMaterialStats);
        }
    }

    private void registerExtraStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.EXTRA) && null != this.extraMaterialStats && properties.isExtraEnabled()){
            TinkerRegistry.addMaterialStats(material, this.extraMaterialStats);
        }
    }
}
