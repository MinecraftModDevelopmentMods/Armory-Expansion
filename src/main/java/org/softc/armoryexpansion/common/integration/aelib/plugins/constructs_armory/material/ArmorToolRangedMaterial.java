package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.IRangedMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

import static slimeknights.tconstruct.library.materials.MaterialTypes.*;
import static slimeknights.tconstruct.library.materials.MaterialTypes.PROJECTILE;

public class ArmorToolRangedMaterial extends ArmorToolMaterial implements IRangedMaterial {
    private BowMaterialStats bowMaterialStats;
    private BowStringMaterialStats bowStringMaterialStats;
    private ArrowShaftMaterialStats arrowShaftMaterialStats;
    private FletchingMaterialStats fletchingMaterialStats;
    private ProjectileMaterialStats projectileMaterialStats;

    public ArmorToolRangedMaterial(String identifier, int color) {
        super(identifier, color);
    }

    @Override
    public BowMaterialStats getBowMaterialStats() {
        return bowMaterialStats;
    }

    @Override
    public BowStringMaterialStats getBowStringMaterialStats() {
        return bowStringMaterialStats;
    }

    @Override
    public ArrowShaftMaterialStats getArrowShaftMaterialStats() {
        return arrowShaftMaterialStats;
    }

    @Override
    public FletchingMaterialStats getFletchingMaterialStats() {
        return fletchingMaterialStats;
    }

    @Override
    public ProjectileMaterialStats getProjectileMaterialStats() {
        return projectileMaterialStats;
    }

    @Override
    public IRangedMaterial addPrimaryRangedTrait(String trait) {
        // TODO Figure out where to apply traits
        return (IRangedMaterial) this.addTrait(trait, BOW);
    }

    @Override
    public IRangedMaterial addSecondaryRangedTrait(String trait) {
        // TODO Figure out where to apply traits
        return (IRangedMaterial) this.addTrait(trait, BOWSTRING);
    }

    @Override
    public IRangedMaterial addGlobalRangedTrait(String trait) {
        // TODO Figure out where to apply traits
        return this.addPrimaryRangedTrait(trait).addSecondaryRangedTrait(trait);
    }

    @Override
    public IRangedMaterial addRangedTrait(String trait1, String trait2) {
        // TODO Figure out where to apply traits
        return this.addPrimaryRangedTrait(trait1).addSecondaryRangedTrait(trait2);
    }

    @Override
    public boolean isRangedMaterial() {
        return this.getArrowShaftMaterialStats() != null
                || this.getBowMaterialStats() != null
                || this.getBowStringMaterialStats() != null
                || this.getFletchingMaterialStats() != null
                || this.getProjectileMaterialStats() != null;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister) {
        if (canRegister) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
            if ("unknown".equals(material.getIdentifier())){
                return false;
            }
            this.registerArmorStats(material);
            this.registerToolStats(material);
            this.registerRangedStats(material);
            return true;
        }
        return false;
    }

    void registerRangedStats(slimeknights.tconstruct.library.materials.Material material){
        if(this.isRangedMaterial()){
            if(material.getStats(BOW) == null && this.getBowMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getBowMaterialStats());
            }
            if(material.getStats(BOWSTRING) == null && this.getBowStringMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getBowStringMaterialStats());
            }
            if(material.getStats(FLETCHING) == null && this.getFletchingMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getFletchingMaterialStats());
            }
            if(material.getStats(PROJECTILE) == null && this.getProjectileMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getProjectileMaterialStats());
            }
        }
    }
}
