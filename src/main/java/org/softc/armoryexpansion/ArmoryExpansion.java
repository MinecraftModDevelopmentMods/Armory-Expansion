package org.softc.armoryexpansion;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.softc.armoryexpansion.dynamic_systems.dynamic_materials.Config;
import org.softc.armoryexpansion.dynamic_systems.dynamic_materials.MaterialRegistration;
import org.softc.armoryexpansion.integration.Integrations;

@Mod(
        modid = ArmoryExpansion.MODID,
        name = ArmoryExpansion.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = "required-after:tconstruct; " +
                "required-after:conarm; " +
                "after:basemetals; " +
                "after:modernmetals; " +
                "after:fantasymetals; " +
                "after:plustic; " +
                "after:moartinkers; " +
                "after:taiga;" +
                "after:acintegration;" +
                "after:bloodarsenal;" +
                "after:enderiointegrationtic;" +
                "after:integrationforegoing;" +
                "after:twilightforest;" +
                "after:pewter;" +
                "after:extrautils2;" +
                "after:mysticalagriculture;" +
                "after:mysticalagradditions;" +
                "after:silentgems;" +
                "after:botania;" +
                "after:contenttweaker;" +
                "after:tinkersaether"
)
@Mod.EventBusSubscriber
public final class ArmoryExpansion {
    static final String MODID = "armoryexpansion";
    static final String NAME = "Armory Expansion";
    static final String VERSION = "0.2.0";

    public static Configuration config;
    public static Logger logger;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(event.getSuggestedConfigurationFile());
        Integrations.preInit(event);

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Config.syncConfig();
        MaterialRegistration.registerFromToolMaterialStat();
        Integrations.integrate();
    }
}
