package org.softc.armoryexpansion.integration;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.mcmoddev.lib.integration.plugins.TinkersConstruct;
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
public class IceAndFireIntegration extends JsonIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-" + IceAndFire.MODID;
    static final String NAME = ArmoryExpansion.NAME + " - " + IceAndFire.NAME;
    static final String DEPENDENCIES =
            "required-after:" + TinkersConstruct.PLUGIN_MODID + "; " +
            "required-after:" + c4.conarm.ConstructsArmory.MODID + "; " +
            "required-after:" + ArmoryExpansion.MODID + "; " +
            "after:" + IceAndFire.MODID + "; ";

    public IceAndFireIntegration() {
        super(IceAndFire.MODID, "assets/" + ArmoryExpansion.MODID + "/data/" + IceAndFire.MODID + ".json");
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
