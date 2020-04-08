package org.softc.armoryexpansion.common.integration.aelib.integration;

import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.event.*;

public interface IIntegration {
    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void registerItems(RegistryEvent<Item> event);

    void registerBlocks(RegistryEvent.Register<? super Block> event);

    void registerFluidBlocks(RegistryEvent.Register<? super Block> event);

    void oredictMaterials();

    void registerMaterials();

    void registerMaterialFluids();

    void registerMaterialFluidsIMC();

    void registerAlloys();

    void registerMaterialStats();

    void registerMaterialCasting();

    void updateMaterials();

    void registerMaterialTraits();

    boolean isLoadable();

    boolean isMaterialEnabled(String material);

    boolean isMaterialFluidEnabled(String material);
}
