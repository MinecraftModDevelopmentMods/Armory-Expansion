package org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material;

import c4.conarm.lib.materials.ArmorMaterialType;
import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.Material;
import slimeknights.tconstruct.library.TinkerRegistry;

import static c4.conarm.lib.materials.ArmorMaterialType.*;

public class ArmorMaterial extends Material implements IArmorMaterial{
    private CoreMaterialStats coreMaterialStats;
    private PlatesMaterialStats platesMaterialStats;
    private TrimMaterialStats trimMaterialStats;

    ArmorMaterial(String identifier, int color) {
        this(identifier, color, MaterialRenderType.DEFAULT);
    }

    private ArmorMaterial(String identifier, int color, MaterialRenderType type) {
        this.identifier = identifier;
        this.color = color;
        this.type = type;
    }

    public ArmorMaterial(String identifier, int color, MaterialRenderType type, CoreMaterialStats coreMaterialStats, PlatesMaterialStats platesMaterialStats, TrimMaterialStats trimMaterialStats) {
        this.identifier = identifier;
        this.color = color;
        this.type = type;

        this.coreMaterialStats = coreMaterialStats;
        this.platesMaterialStats = platesMaterialStats;
        this.trimMaterialStats = trimMaterialStats;
    }

    @Override
    public CoreMaterialStats getCoreMaterialStats() {
        return coreMaterialStats;
    }

    @Override
    public PlatesMaterialStats getPlatesMaterialStats() {
        return platesMaterialStats;
    }

    @Override
    public TrimMaterialStats getTrimMaterialStats() {
        return trimMaterialStats;
    }

    @Override
    public IArmorMaterial addPrimaryArmorTrait(String trait) {
        return (IArmorMaterial) this.addTrait(trait, CORE);
    }

    @Override
    public IArmorMaterial addSecondaryArmorTrait(String trait) {
        return (IArmorMaterial) this.addTrait(trait, TRIM).addTrait(trait, ArmorMaterialType.PLATES);
    }

    @Override
    public IArmorMaterial addGlobalArmorTrait(String trait) {
        return this.addPrimaryArmorTrait(trait).addSecondaryArmorTrait(trait);
    }

    @Override
    public IArmorMaterial addArmorTrait(String trait1, String trait2) {
        return this.addPrimaryArmorTrait(trait1).addSecondaryArmorTrait(trait2);
    }

    @Override
    public boolean isToolMaterial() {
        return false;
    }

    @Override
    public boolean isArmorMaterial() {
        return this.getCoreMaterialStats() != null
                || this.getPlatesMaterialStats() != null
                || this.getTrimMaterialStats() != null;
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
            this.registerArmorStats(material);
            return true;
        }
        return false;
    }

    void registerArmorStats(slimeknights.tconstruct.library.materials.Material material){
        if (this.isArmorMaterial()) {
            if(material.getStats(CORE) == null && this.getCoreMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getCoreMaterialStats());
            }
            if(material.getStats(PLATES) == null && this.getPlatesMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getPlatesMaterialStats());
            }
            if(material.getStats(TRIM) == null && this.getTrimMaterialStats() != null){
                TinkerRegistry.addMaterialStats(material, this.getTrimMaterialStats());
            }
        }
    }
}
