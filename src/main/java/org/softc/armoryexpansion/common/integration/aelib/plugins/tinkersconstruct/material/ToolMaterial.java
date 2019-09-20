package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.BasicMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.MaterialTypes;

public class ToolMaterial extends BasicMaterial implements IToolMaterial {
    protected HeadMaterialStats headMaterialStats;
    protected HandleMaterialStats handleMaterialStats;
    protected ExtraMaterialStats extraMaterialStats;

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
    public boolean isArmorMaterial() {
        return false;
    }

    @Override
    public boolean isRangedMaterial() {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        if (properties.materialEnabled() && properties.isToolEnabled()) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
                this.registerToolStats(material, properties);
                return true;
//            }
        }
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
