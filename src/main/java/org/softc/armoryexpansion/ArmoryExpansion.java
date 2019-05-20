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
    public static final String VERSION = "WebClient-beta1";
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
        return config.get("web server", "server list",
                new String[]{
                        "https://raw.githubusercontent.com/YaibaToKen/Armory-Expansion-WebJSON/master"
        }).getStringList();
    }

    public static boolean useServersForJsons(){
        return config.get("web server", "use servers", false).getBoolean();
    }

    public static int getConnectTimeout(){
        return config.get("web server", "connect timeout", 60000).getInt();
    }

    public static int getBoundedInputStreamMaxSize(){
        return config.get("web server", "input stream max size", 131072).getInt();
    }
}
