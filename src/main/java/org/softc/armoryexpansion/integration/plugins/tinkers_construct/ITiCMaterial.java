package org.softc.armoryexpansion.integration.plugins.tinkers_construct;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fluids.Fluid;

import java.util.Map;

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

    ITiCMaterial registerOreDict();

    Fluid getFluid();

    Block getFluidBlock();

    boolean registerTinkersMaterial();

    boolean registerTinkersFluid();

    boolean registerTinkersMaterialStats(Map<String, Property> properties);

    boolean updateTinkersMaterial();

    boolean registerTinkersMaterialTraits();
}
