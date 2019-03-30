package org.softc.armoryexpansion.integration;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.integration.tinkers_construct.TiCMaterial;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractIntegration implements IIntegration {
    private String modId;
    protected List<TiCMaterial> materials = new LinkedList<>();

    public AbstractIntegration(String modId){
        this.modId = modId;
        this.addToIntegrationList();
    }

    public String getModId(){
        return modId;
    }

    protected abstract void setMaterials();

    private void oredictMaterials() {
        this.materials.forEach(TiCMaterial::registerOreDict);
    }

    private void registerMaterials() {
        this.materials.forEach(TiCMaterial::registerTinkersMaterial);
    }

    private void registerMaterialStats() {
        this.materials.forEach(TiCMaterial::registerTinkersMaterialStats);
    }

    private void updateMaterials() {
        this.materials.forEach(TiCMaterial::updateTinkersMaterial);
    }

    private void registerMaterialTraits() {
        this.materials.forEach(TiCMaterial::registerTinkersMaterialTraits);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        registerMaterials();
        registerMaterialStats();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        oredictMaterials();
        updateMaterials();
        registerMaterialTraits();
    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event) {

    }

    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {

    }

    @Override
    public void addToIntegrationList() {
        Integrations.integrationList.add(this);
    }
}
