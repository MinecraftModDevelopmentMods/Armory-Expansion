package org.softc.armoryexpansion;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ArmoryExpansion.MODID,
        name = ArmoryExpansion.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = ArmoryExpansion.DEPENDENCIES
)
@Mod.EventBusSubscriber
public final class ArmoryExpansion {
    public static final String MODID = "armoryexpansion";
    public static final String NAME = "Armory Expansion";
    public static final String VERSION = "1.1.9";
    static final String DEPENDENCIES =
            "required-after:tconstruct; " +
            "required-after:conarm; ";

    public static Configuration config;
    private static Logger logger;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(event.getSuggestedConfigurationFile());
    }
}
