package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.material;

import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.BasicMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.*;

public class RangedMaterial extends BasicMaterial implements IRangedMaterial {
    protected BowMaterialStats bowMaterialStats;
    protected BowStringMaterialStats bowStringMaterialStats;
    protected ArrowShaftMaterialStats arrowShaftMaterialStats;
    protected FletchingMaterialStats fletchingMaterialStats;
    protected ProjectileMaterialStats projectileMaterialStats;

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
    public boolean isToolMaterial() {
        return false;
    }

    @Override
    public boolean isArmorMaterial() {
        return false;
    }

    @Override
    public boolean isRangedMaterial() {
        return !(null == this.arrowShaftMaterialStats && null == this.bowMaterialStats && null == this.bowStringMaterialStats && null == this.fletchingMaterialStats && null == this.projectileMaterialStats);
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties) {
        if (properties.materialEnabled()) {
            Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())){
                this.registerRangedStats(material, properties);
                return true;
//            }
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

    protected void registerBowStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.BOW) && null != this.bowMaterialStats && properties.isBowEnabled()){
            TinkerRegistry.addMaterialStats(material, this.bowMaterialStats);
        }
    }

    protected void registerBowStringStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.BOWSTRING) && null != this.bowStringMaterialStats && properties.isBowStringEnabled()){
            TinkerRegistry.addMaterialStats(material, this.bowStringMaterialStats);
        }
    }

    protected void registerFletchingStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.FLETCHING) && null != this.fletchingMaterialStats && properties.isFletchingEnabled()){
            TinkerRegistry.addMaterialStats(material, this.fletchingMaterialStats);
        }
    }

    protected void registerProjectileStats(Material material, MaterialConfigOptions properties){
        if(null == material.getStats(MaterialTypes.PROJECTILE) && null != this.projectileMaterialStats && properties.isProjectileEnabled()){
            TinkerRegistry.addMaterialStats(material, this.projectileMaterialStats);
        }
    }
}
