package org.softc.armoryexpansion.common.integration.aelib.integration;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IMaterial;

public interface IIntegration {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void registerBlocks(RegistryEvent.Register<Block> event);

    void registerFluidBlocks(RegistryEvent.Register<Block> event);

    void oredictMaterials();

    void registerMaterials();

    void registerMaterialFluids();

    void registerMaterialFluidsIMC();

    void registerAlloys();

    void registerMaterialStats();

    void updateMaterials();

    void registerMaterialTraits();

    boolean isMaterialEnabled(IMaterial material);

    boolean isMaterialFluidEnabled(IMaterial material);

    void enableForceJsonCreation();
}
