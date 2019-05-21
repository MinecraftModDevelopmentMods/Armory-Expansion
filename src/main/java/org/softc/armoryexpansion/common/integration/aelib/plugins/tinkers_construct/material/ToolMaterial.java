package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.Material;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

public class ToolMaterial extends Material implements IToolMaterial {
    private HeadMaterialStats headMaterialStats;
    private HandleMaterialStats handleMaterialStats;
    private ExtraMaterialStats extraMaterialStats;

    @Override
    public HeadMaterialStats getHeadMaterialStats() {
        return headMaterialStats;
    }

    @Override
    public HandleMaterialStats getHandleMaterialStats() {
        return handleMaterialStats;
    }

    @Override
    public ExtraMaterialStats getExtraMaterialStats() {
        return extraMaterialStats;
    }

    @Override
    public IToolMaterial addPrimaryToolTrait(String trait) {
        return (IToolMaterial) this.addTrait(trait, HEAD);
    }

    @Override
    public IToolMaterial addSecondaryToolTrait(String trait) {
        return (IToolMaterial) this.addTrait(trait, HANDLE).addTrait(trait, EXTRA);
    }

    @Override
    public IToolMaterial addGlobalToolTrait(String trait) {
        return this.addPrimaryToolTrait(trait).addSecondaryToolTrait(trait);
    }

    @Override
    public IToolMaterial addToolTrait(String trait1, String trait2) {
        return this.addPrimaryToolTrait(trait1).addSecondaryToolTrait(trait2);
    }

    @Override
    public boolean isToolMaterial() {
        return this.getHeadMaterialStats() != null
                || this.getHandleMaterialStats() != null
                || this.getExtraMaterialStats() != null;
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
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister){
        if (canRegister) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
            if ("unknown".equals(material.getIdentifier())){
                return false;
            }
            this.registerToolStats(material);
            return true;
        }
        return false;
    }

    void registerToolStats(slimeknights.tconstruct.library.materials.Material material){
        if(this.isToolMaterial()){
            if(material.getStats(HEAD) == null){
                TinkerRegistry.addMaterialStats(material, this.getHeadMaterialStats());
            }
            if(material.getStats(HANDLE) == null){
                TinkerRegistry.addMaterialStats(material, this.getHandleMaterialStats());
            }
            if(material.getStats(EXTRA) == null){
                TinkerRegistry.addMaterialStats(material, this.getExtraMaterialStats());
            }
        }
    }
}
