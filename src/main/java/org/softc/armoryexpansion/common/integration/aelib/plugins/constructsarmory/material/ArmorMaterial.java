package org.softc.armoryexpansion.common.integration.aelib.plugins.constructsarmory.material;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.BasicMaterial;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ArmorMaterial extends BasicMaterial implements IArmorMaterial{
    protected CoreMaterialStats coreMaterialStats;
    protected PlatesMaterialStats platesMaterialStats;
    protected TrimMaterialStats trimMaterialStats;

    ArmorMaterial(String identifier, int color) {
        super(identifier, color, MaterialRenderType.DEFAULT);
    }

    public ArmorMaterial(String identifier, int color, MaterialRenderType type, CoreMaterialStats coreMaterialStats, PlatesMaterialStats platesMaterialStats, TrimMaterialStats trimMaterialStats) {
        super(identifier, color, type);
        this.coreMaterialStats = coreMaterialStats;
        this.platesMaterialStats = platesMaterialStats;
        this.trimMaterialStats = trimMaterialStats;
    }

    @Override
    public CoreMaterialStats getCoreMaterialStats() {
        return this.coreMaterialStats;
    }

    @Override
    public PlatesMaterialStats getPlatesMaterialStats() {
        return this.platesMaterialStats;
    }

    @Override
    public TrimMaterialStats getTrimMaterialStats() {
        return this.trimMaterialStats;
    }

    @Override
    public boolean isToolMaterial() {
        return false;
    }

    @Override
    public boolean isArmorMaterial() {
        return null != this.coreMaterialStats
                || null != this.platesMaterialStats
                || null != this.trimMaterialStats;
    }

    @Override
    public boolean isRangedMaterial() {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties){
        if (properties.materialEnabled() && properties.armorEnabled()) {
            slimeknights.tconstruct.library.materials.Material material = TinkerRegistry.getMaterial(this.getIdentifier());
//            if (!"unknown".equals(material.getIdentifier())) {
                this.registerArmorStats(material, properties);
                return true;
//            }
        }
        return false;
    }

    void registerArmorStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if (this.isArmorMaterial()) {
            this.registerCoreStats(material, properties);
            this.registerPlatesStats(material, properties);
            this.registerTrimStats(material, properties);
        }
    }

    private void registerCoreStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.CORE) && null != this.coreMaterialStats && properties.coreEnabled()){
            TinkerRegistry.addMaterialStats(material, this.coreMaterialStats);
        }
    }

    private void registerPlatesStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.PLATES) && null != this.platesMaterialStats && properties.platesEnabled()){
            TinkerRegistry.addMaterialStats(material, this.platesMaterialStats);
        }
    }

    private void registerTrimStats(slimeknights.tconstruct.library.materials.Material material, MaterialConfigOptions properties){
        if(null == material.getStats(ArmorMaterialType.TRIM) && null != this.trimMaterialStats && properties.trimEnabled()){
            TinkerRegistry.addMaterialStats(material, this.trimMaterialStats);
        }
    }
}
