package org.softc.armoryexpansion.integration;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.integration.ice_and_fire.IceAndFire;

public class Integrations {
    public static void preInit(final FMLPreInitializationEvent event){
        IceAndFire.preInit(event);
    }

    public static void integrate(){
        IceAndFire.integrate();
    }
}
