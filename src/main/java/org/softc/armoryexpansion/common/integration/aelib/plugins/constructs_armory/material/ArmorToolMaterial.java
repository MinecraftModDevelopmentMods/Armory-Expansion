package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.IToolMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;

public class ArmorToolMaterial extends ArmorMaterial implements IToolMaterial {
    private HeadMaterialStats headMaterialStats;
    private HandleMaterialStats handleMaterialStats;
    private ExtraMaterialStats extraMaterialStats;

    ArmorToolMaterial(String identifier, int color) {
        super(identifier, color);
    }

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
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
        if ("unknown".equals(material.getIdentifier())){
            return false;
        }
        if (properties.isMaterialEnabled()) {
            this.registerArmorStats(material, properties);
            this.registerToolStats(material, properties);
            return true;
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
        if(material.getStats(HEAD) == null && this.getHeadMaterialStats() != null && properties.isHeadEnabled()){
            TinkerRegistry.addMaterialStats(material, this.getHeadMaterialStats());
        }
    }

    private void registerHandleStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(material.getStats(HANDLE) == null && this.getHandleMaterialStats() != null && properties.isHandleEnabled()){
            TinkerRegistry.addMaterialStats(material, this.getHandleMaterialStats());
        }
    }

    private void registerExtraStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(material.getStats(EXTRA) == null && this.getExtraMaterialStats() != null && properties.isExtraEnabled()){
            TinkerRegistry.addMaterialStats(material, this.getExtraMaterialStats());
        }
    }
}
