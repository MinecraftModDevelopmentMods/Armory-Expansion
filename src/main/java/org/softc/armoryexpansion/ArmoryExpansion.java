package org.softc.armoryexpansion;

import c4.conarm.ConstructsArmory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.TConstruct;

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
    public static final String VERSION = "1.3.0";
    static final String DEPENDENCIES =
            "required-after:" + TConstruct.modID + "; " +
            "required-after:" + ConstructsArmory.MODID + "; ";

    public static Configuration config;
    public static Logger logger;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());
    }

    public static String[] getWebServerList(){
        return config.get("web server", "server list", new String[]{"localhost:8080"}).getStringList();
    }

    public static boolean useServersForJsons(){
        return config.get("web server", "use servers", false).getBoolean();
    }
}
