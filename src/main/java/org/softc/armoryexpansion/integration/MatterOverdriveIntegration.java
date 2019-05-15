package org.softc.armoryexpansion.integration;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.aelib.JsonIntegration;

@Mod(
        modid = IceAndFireIntegration.MODID,
        name = IceAndFireIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = IceAndFireIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class MatterOverdriveIntegration extends JsonIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-matteroverdrive";
    static final String NAME = ArmoryExpansion.NAME + " - MatterOverdrive";
    static final String DEPENDENCIES =
            "required-after:" + ArmoryExpansion.MODID + "; " +
                    "after:matteroverdrive; ";

    public MatterOverdriveIntegration() {
        super("matteroverdrive", "assets/" + ArmoryExpansion.MODID + "/data/matteroverdrive");
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = IceAndFire.MODID;
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
}
