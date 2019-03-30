package org.softc.armoryexpansion.integration;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.ice_and_fire.IceAndFire;

import java.util.LinkedList;
import java.util.List;

import static net.minecraftforge.fml.common.Loader.isModLoaded;

@Mod(
        modid = Integrations.MODID,
        name = Integrations.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = Integrations.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class Integrations {
    static final String MODID = "armoryexpansionintegrations";
    static final String NAME = "Armory Expansion Integrations";
    static final String DEPENDENCIES =
            "required-after:tconstruct; " +
            "required-after:conarm; " +
            "required-after:mmdlib;";

    static List<IIntegration> integrationList = new LinkedList<>();

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(this);
        initIntegrations();
        for (IIntegration integration:integrationList) {
            if (isModLoaded(integration.getModId())){
                integration.preInit(event);
            }
        }
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event){
        for (IIntegration integration:integrationList) {
            if (isModLoaded(integration.getModId())){
                integration.init(event);
            }
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event){
        for (IIntegration integration:integrationList) {
            if (isModLoaded(integration.getModId())){
                integration.registerItems(event);
            }
        }
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event){
        for (IIntegration integration:integrationList) {
            if (isModLoaded(integration.getModId())){
                integration.registerRecipes(event);
            }
        }
    }

    private void initIntegrations() {
        new IceAndFire();
    }
}
