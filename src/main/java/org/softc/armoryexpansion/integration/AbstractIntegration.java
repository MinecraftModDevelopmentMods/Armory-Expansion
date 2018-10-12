package org.softc.armoryexpansion.integration;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public abstract class AbstractIntegration implements IIntegration {
    String modId;

    public AbstractIntegration(String modId){
        this.modId = modId;
        //this.addToIntegrationList();
    }

    public String getModId(){
        return modId;
    }

    @Override
    public void addToIntegrationList() {
        Integrations.integrationList.add(this);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event) {

    }

    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }
}
