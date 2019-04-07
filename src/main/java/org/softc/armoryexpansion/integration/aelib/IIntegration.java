package org.softc.armoryexpansion.integration.aelib;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IIntegration {

    void preInit(final FMLPreInitializationEvent event);

    void init(final FMLInitializationEvent event);

    void registerItems(RegistryEvent.Register<Item> event);

    void registerRecipes(RegistryEvent.Register<IRecipe> event);
}
