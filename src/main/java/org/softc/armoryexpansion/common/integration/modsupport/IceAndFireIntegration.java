package org.softc.armoryexpansion.common.integration.modsupport;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.common.integration.aelib.integration.JsonIntegration;

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
    static final String DEPENDENCIES =
            "required-after:" + ArmoryExpansion.MODID + "; " +
            "after:" + IceAndFire.MODID + "; ";

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

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<? super Block> event){
        super.registerBlocks(event);
    }
}
