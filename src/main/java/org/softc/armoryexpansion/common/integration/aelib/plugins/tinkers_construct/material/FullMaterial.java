package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraft.block.Block;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;
import org.softc.armoryexpansion.common.integration.aelib.plugins.constructs_armory.material.IArmorMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.old.AbstractTiCMaterial;
import org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material.stats.ToolMaterialStats;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;

public class FullMaterial implements IArmorMaterial, IToolMaterial, IMaterial{
    private IMaterial material;
    private IToolMaterial toolMaterial;
    private IArmorMaterial armorMaterial;

    @Override
    public ArmorMaterialStats getArmorMaterialStats() {
        return this.armorMaterial.getArmorMaterialStats();
    }

    @Override
    public CoreMaterialStats getCoreMaterialStats() {
        return this.getArmorMaterialStats().getCoreMaterialStats();
    }

    @Override
    public PlatesMaterialStats getPlatesMaterialStats() {
        return this.getArmorMaterialStats().getPlatesMaterialStats();
    }

    @Override
    public TrimMaterialStats getTrimMaterialStats() {
        return this.getArmorMaterialStats().getTrimMaterialStats();
    }

    @Override
    public IToolMaterial addPrimaryToolTrait(String trait) {
        return this.toolMaterial.addPrimaryToolTrait(trait);
    }

    @Override
    public IToolMaterial addSecondaryToolTrait(String trait) {
        return this.toolMaterial.addSecondaryToolTrait(trait);
    }

    @Override
    public IToolMaterial addGlobalToolTrait(String trait) {
        return this.toolMaterial.addGlobalToolTrait(trait);
    }

    @Override
    public IToolMaterial addToolTrait(String trait1, String trait2) {
        return this.toolMaterial.addToolTrait(trait1, trait2);
    }

    @Override
    public boolean isToolMaterial() {
        return this.toolMaterial != null;
    }

    @Override
    public IArmorMaterial addPrimaryArmorTrait(String trait) {
        return this.armorMaterial.addPrimaryArmorTrait(trait);
    }

    @Override
    public IArmorMaterial addSecondaryArmorTrait(String trait) {
        return this.armorMaterial.addSecondaryArmorTrait(trait);
    }

    @Override
    public IArmorMaterial addGlobalArmorTrait(String trait) {
        return this.armorMaterial.addGlobalArmorTrait(trait);
    }

    @Override
    public IArmorMaterial addArmorTrait(String trait1, String trait2) {
        return this.armorMaterial.addArmorTrait(trait1, trait2);
    }

    @Override
    public boolean isArmorMaterial() {
        return this.armorMaterial != null;
    }

    @Override
    public ToolMaterialStats getToolMaterialStats() {
        return this.toolMaterial.getToolMaterialStats();
    }

    @Override
    public HeadMaterialStats getHeadMaterialStats() {
        return this.toolMaterial.getHeadMaterialStats();
    }

    @Override
    public HandleMaterialStats getHandleMaterialStats() {
        return this.toolMaterial.getHandleMaterialStats();
    }

    @Override
    public ExtraMaterialStats getExtraMaterialStats() {
        return this.toolMaterial.getExtraMaterialStats();
    }

    @Override
    public boolean isCastable() {
        return false;
    }

    @Override
    public boolean isCraftable() {
        return false;
    }

    @Override
    public IMaterial registerOreDict() {
        return null;
    }

    @Override
    public FluidMolten getFluid() {
        return null;
    }

    @Override
    public String getFluidName() {
        return null;
    }

    @Override
    public Block getFluidBlock() {
        return null;
    }

    @Override
    public boolean registerTinkersMaterial(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersFluid(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersFluidIMC(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister) {
        return false;
    }

    @Override
    public boolean updateTinkersMaterial(boolean canRegister) {
        return false;
    }

    @Override
    public boolean registerTinkersMaterialTraits(boolean canRegister) {
        return false;
    }

    @Override
    public MaterialRenderType getType() {
        return null;
    }

    @Override
    public AbstractTiCMaterial setType(MaterialRenderType type) {
        return null;
    }
}
