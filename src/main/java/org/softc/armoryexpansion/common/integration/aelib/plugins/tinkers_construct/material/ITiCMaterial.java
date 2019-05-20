package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkers_construct.material;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material.MaterialRenderType;
import org.softc.armoryexpansion.common.integration.aelib.config.MaterialConfigOptions;

public interface ITiCMaterial {

    String getIdentifier();

    ITiCMaterial setDurability(int durability);

    float getMiningSpeed();

    ITiCMaterial setMiningSpeed(float miningSpeed);

    float getDamage();

    ITiCMaterial setDamage(float damage);

    float getMagicAffinity();

    ITiCMaterial setMagicAffinity(float magicAffinity);

    int getHarvestLevel();

    ITiCMaterial setHarvestLevel(int harvestLevel);

    float getRange();

    ITiCMaterial setRange(float range);

    float getAccuracy();

    ITiCMaterial setAccuracy(float accuracy);

    float getDefense();

    ITiCMaterial setDefense(float defense);

    float getToughness();

    ITiCMaterial setToughness(float toughness);

    ITiCMaterial addPrimaryArmorTrait(String trait);

    ITiCMaterial addSecondaryArmorTrait(String trait);

    ITiCMaterial addGlobalArmorTrait(String trait);

    ITiCMaterial addArmorTrait(String trait1, String trait2);

    ITiCMaterial addPrimaryToolTrait(String trait);

    ITiCMaterial addSecondaryToolTrait(String trait);

    ITiCMaterial addGlobalToolTrait(String trait);

    ITiCMaterial addToolTrait(String trait1, String trait2);

    boolean isToolMaterial();

    boolean isBowMaterial();

    boolean isFletchingMaterial();

    boolean isProjectileMaterial();

    boolean isArmorMaterial();

    boolean isCastable();

    boolean isCraftable();

    ITiCMaterial registerOreDict();

    Fluid getFluid();

    String getFluidName();

    Block getFluidBlock();

    boolean registerTinkersMaterial(boolean canRegister);

    boolean registerTinkersFluid(boolean canRegister);

    boolean registerTinkersFluidIMC(boolean canRegister);

    boolean registerTinkersMaterialStats(MaterialConfigOptions properties, boolean canRegister);

    boolean updateTinkersMaterial(boolean canRegister);

    boolean registerTinkersMaterialTraits(boolean canRegister);

    MaterialRenderType getType();

    AbstractTiCMaterial setType(MaterialRenderType type);
}
