package org.softc.armoryexpansion.integration;

import c4.conarm.ConstructsArmory;
import com.mcmoddev.lib.integration.plugins.TinkersConstruct;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.aelib.AbstractIntegration;

@Mod(
        modid = CustomMaterialsIntegration.MODID,
        name = CustomMaterialsIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = CustomMaterialsIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class CustomMaterialsIntegration extends AbstractIntegration {
    private static final String INTEGRATION_ID = "custommaterials";
    private static final String INTEGRATION_NAME = "Custom Materials";

    static final String MODID = ArmoryExpansion.MODID + "-" + INTEGRATION_ID;
    static final String NAME = ArmoryExpansion.NAME + " - " + INTEGRATION_NAME;
    static final String DEPENDENCIES =
            "required-after:" + TinkersConstruct.PLUGIN_MODID + "; " +
            "required-after:" + ConstructsArmory.MODID + "; " +
            "required-after:" + ArmoryExpansion.MODID + "; ";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = INTEGRATION_ID;
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // TODO Figure out how to print a list of all trait identifiers
    }

    @Override
    protected void loadMaterialsFromSource() {
        // Left empty on purpose
        // All the materials should be added through the JSON file
    }
}
