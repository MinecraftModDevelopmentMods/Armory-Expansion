package org.softc.armoryexpansion.common.integration.modsupport;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.common.integration.aelib.integration.JsonIntegration;

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
    static final String DEPENDENCIES =
            "required-after:" + ArmoryExpansion.MODID + "; " +
            "after:matteroverdrive; ";

    public MatterOverdriveIntegration() {
        super("matteroverdrive", ArmoryExpansion.MODID, "matteroverdrive");
    }

//    public MatterOverdriveIntegration() {
//        super("matteroverdrive");
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = "matteroverdrive";
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){
        super.registerBlocks(event);
    }
}
