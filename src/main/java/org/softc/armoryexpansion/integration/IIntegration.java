package org.softc.armoryexpansion.integration;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IIntegration {
    void addToIntegrationList();

    String getModId();

    void preInit(final FMLPreInitializationEvent event);

    void registerItems(RegistryEvent.Register<Item> event);

    void registerRecipes(RegistryEvent.Register<IRecipe> event);
}
