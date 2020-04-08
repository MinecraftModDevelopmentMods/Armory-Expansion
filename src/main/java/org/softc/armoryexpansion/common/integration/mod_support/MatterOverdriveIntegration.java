package org.softc.armoryexpansion.common.integration.mod_support;

import net.minecraft.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.softc.armoryexpansion.*;
import org.softc.armoryexpansion.common.integration.aelib.integration.*;

@Mod(
        modid = MatterOverdriveIntegration.MODID,
        name = MatterOverdriveIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = MatterOverdriveIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class MatterOverdriveIntegration extends JsonIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-matteroverdrive";
    static final String NAME = ArmoryExpansion.NAME + " - MatterOverdrive";
    static final String DEPENDENCIES = ""
            + "required-after:" + ArmoryExpansion.MODID + "; "
            + "after:matteroverdrive; "
            ;

    public MatterOverdriveIntegration() {
        super("matteroverdrive", ArmoryExpansion.MODID, "matteroverdrive");
        MinecraftForge.EVENT_BUS.register(this);
    }

//    public MatterOverdriveIntegration() {
//        super("matteroverdrive");
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = "matteroverdrive";
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
