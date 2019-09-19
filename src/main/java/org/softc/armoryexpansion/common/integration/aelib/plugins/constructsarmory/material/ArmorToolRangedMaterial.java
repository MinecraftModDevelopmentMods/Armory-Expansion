package org.softc.armoryexpansion.common.integration.aelib.plugins.constructsarmory.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.material.IRangedMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

public class ArmorToolRangedMaterial extends ArmorToolMaterial implements IRangedMaterial {
    protected BowMaterialStats bowMaterialStats;
    protected BowStringMaterialStats bowStringMaterialStats;
    protected ArrowShaftMaterialStats arrowShaftMaterialStats;
    protected FletchingMaterialStats fletchingMaterialStats;
    protected ProjectileMaterialStats projectileMaterialStats;

    public ArmorToolRangedMaterial(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public BowMaterialStats getBowMaterialStats() {
        return this.bowMaterialStats;
    }

    @Override
    public BowStringMaterialStats getBowStringMaterialStats() {
        return this.bowStringMaterialStats;
    }

    @Override
    public ArrowShaftMaterialStats getArrowShaftMaterialStats() {
        return this.arrowShaftMaterialStats;
    }

    @Override
    public FletchingMaterialStats getFletchingMaterialStats() {
        return this.fletchingMaterialStats;
    }

    @Override
    public ProjectileMaterialStats getProjectileMaterialStats() {
        return this.projectileMaterialStats;
    }

    @Override
    public IRangedMaterial addPrimaryRangedTrait(String trait) {
        // TODO Figure out where to apply traits
        return (IRangedMaterial) this.addTrait(trait, MaterialTypes.BOW);
    }

    @Override
    public IRangedMaterial addSecondaryRangedTrait(String trait) {
        // TODO Figure out where to apply traits
        return (IRangedMaterial) this.addTrait(trait, MaterialTypes.BOWSTRING);
    }

    @Override
    public IRangedMaterial addGlobalRangedTrait(String trait) {
        // TODO Figure out where to apply traits
        this.addPrimaryRangedTrait(trait);
        return this.addSecondaryRangedTrait(trait);
    }

    @Override
    public IRangedMaterial addRangedTrait(String trait1, String trait2) {
        // TODO Figure out where to apply traits
        this.addPrimaryRangedTrait(trait1);
        return this.addSecondaryRangedTrait(trait2);
    }

    @Override
    public boolean isRangedMaterial() {
        return !(null == this.arrowShaftMaterialStats && null == this.bowMaterialStats && null == this.bowStringMaterialStats && null == this.fletchingMaterialStats && null == this.projectileMaterialStats);
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties) {
        if (properties.materialEnabled()) {
            Material material = TinkerRegistry.getMaterial(this.getIdentifier());
            if (!"unknown".equals(material.getIdentifier())) {
                this.registerArmorStats(material, properties);
                this.registerToolStats(material, properties);
                this.registerRangedStats(material, properties);
                return true;
            }
        }
        return false;
    }

    protected void registerRangedStats(Material material, MaterialConfigOptions properties){
        if(this.isRangedMaterial()){
            this.registerBowStats(material, properties);
            this.registerBowStringStats(material, properties);
            this.registerFletchingStats(material, properties);
            this.registerProjectileStats(material, properties);
        }
    }

    private void registerBowStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.BOW) && null != this.bowMaterialStats && properties.isBowEnabled()){
            TinkerRegistry.addMaterialStats(material, this.bowMaterialStats);
        }
    }

    private void registerBowStringStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.BOWSTRING) && null != this.bowStringMaterialStats && properties.isBowStringEnabled()){
            TinkerRegistry.addMaterialStats(material, this.bowStringMaterialStats);
        }
    }

    private void registerFletchingStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.FLETCHING) && null != this.fletchingMaterialStats && properties.isFletchingEnabled()){
            TinkerRegistry.addMaterialStats(material, this.fletchingMaterialStats);
        }
    }

    private void registerProjectileStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.PROJECTILE) && null != this.projectileMaterialStats && properties.isProjectileEnabled()){
            TinkerRegistry.addMaterialStats(material, this.projectileMaterialStats);
        }
    }
}
