package org.softc.armoryexpansion.common.integration.mod_support;

import com.github.alexthe666.iceandfire.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.softc.armoryexpansion.*;
import org.softc.armoryexpansion.common.integration.aelib.integration.*;

@Mod(
        modid = IceAndFireIntegration.MODID,
        name = IceAndFireIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = IceAndFireIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class IceAndFireIntegration extends JsonIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-" + IceAndFire.MODID;
    static final String NAME = ArmoryExpansion.NAME + " - " + IceAndFire.NAME;
    static final String DEPENDENCIES = ""
            + "required-after:" + ArmoryExpansion.MODID + "; "
            + "after:" + IceAndFire.MODID + "; "
            ;

    public IceAndFireIntegration() {
        super(IceAndFire.MODID, ArmoryExpansion.MODID, IceAndFire.MODID);
        MinecraftForge.EVENT_BUS.register(this);
    }

//    public IceAndFireIntegration() {
//        super(IceAndFire.MODID);
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = IceAndFire.MODID;
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<? super Block> event){
        super.registerBlocks(event);
    }
}
