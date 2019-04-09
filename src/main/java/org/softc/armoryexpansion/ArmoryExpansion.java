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
import org.softc.armoryexpansion.dynamic_systems.dynamic_materials.constructs_armory.ConArmIntegration;

@Mod(
        modid = ArmoryExpansion.MODID,
        name = ArmoryExpansion.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = ArmoryExpansion.DEPENDENCIES
)
@Mod.EventBusSubscriber
public final class ArmoryExpansion {
    public static final String MODID = "armoryexpansion";
    static final String NAME = "Armory Expansion";
    public static final String VERSION = "1.1.7b";
    static final String DEPENDENCIES =
            "required-after:tconstruct; " +
            "required-after:conarm; " +
//            "required-after:mmdlib; " +
            "after:*";

    public static Configuration config;
    private static Logger logger;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(event.getSuggestedConfigurationFile());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        Config.syncConfig();
        ConArmIntegration.integrateMaterialsFromConfig();
    }
}
