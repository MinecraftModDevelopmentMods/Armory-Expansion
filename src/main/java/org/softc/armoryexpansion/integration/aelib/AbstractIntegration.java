package org.softc.armoryexpansion.integration.aelib;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.softc.armoryexpansion.integration.aelib.Config.CATEGORY_MATERIAL;

public abstract class AbstractIntegration implements IIntegration {
    protected Config configHelper;

    protected List<TiCMaterial> materials = new LinkedList<>();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        setMaterials();
        configHelper = new Config(new Configuration(event.getSuggestedConfigurationFile()));
        configHelper.syncConfig(materials);
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
        // TODO Write better documentation
    }

    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        // TODO Write better documentation
    }

    public Configuration getConfiguration() {
        return configHelper.getConfiguration();
    }

    public Config getConfigHelper() {
        return configHelper;
    }

    protected abstract void setMaterials();

    private void oredictMaterials() {
        materials.forEach(m -> m.registerOreDict());
    }

    private void registerMaterials() {
        materials.forEach(m -> {
            if(isMaterialEnabled(m)){
                m.registerTinkersMaterial();
            }
        });
    }

    private void registerMaterialStats() {
        materials.forEach(m -> {
            if(isMaterialEnabled(m)){
                m.registerTinkersMaterialStats(getProperties(m));
            }
        });
    }

    private void updateMaterials() {
        materials.forEach(m -> {
            if(isMaterialEnabled(m)){
                m.updateTinkersMaterial();
            }
        });
    }

    private void registerMaterialTraits() {
        materials.forEach(m -> {
            if(isMaterialEnabled(m)){
                m.registerTinkersMaterialTraits();
            }
        });
    }

    private Map<String, Property> getProperties(TiCMaterial material){
        return this.getConfigHelper().getProperties(material.getIdentifier());
    }

    private Property getProperty(TiCMaterial material, String property){
        return getProperties(material).get(property);
    }

    public boolean isEnabled(TiCMaterial material, String property){
        return getProperty(material, property).getBoolean();
    }

    private boolean isMaterialEnabled(TiCMaterial material){
        return getProperty(material, CATEGORY_MATERIAL).getBoolean();
    }
}
