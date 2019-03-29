package org.softc.armoryexpansion.integration.ice_and_fire;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.integration.AbstractIntegration;
import org.softc.armoryexpansion.integration.util.tinkers_construct.TiCMaterial;

public class IceAndFire extends AbstractIntegration {
    private IceAndFire(){
        super(com.github.alexthe666.iceandfire.IceAndFire.MODID);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        setMaterials();
        registerMaterials();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        oredictMaterials();
        registerMaterialStats();
    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event) {

    }

    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @Override
    protected void setMaterials() {
        this.materials.add(
                new TiCMaterial("scalereddragon", "iceandfire:dragonscales_red", 11141120)
                        .setArmorMaterial(true)
                        .setDurability(100)
                        .setHardness(3)
                        .setDamage(10)
                        .setMagicaffinity(5)
                        .setHarvestLevel(4)
                        .setRange(20));
    }
}
