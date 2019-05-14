package org.softc.armoryexpansion.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.fluids.TiCAlloy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Mod(
        modid = AlloyIntegration.MODID,
        name = AlloyIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = AlloyIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class AlloyIntegration{
    private static final String INTEGRATION_ID = "customalloys";
    private static final String INTEGRATION_NAME = "Custom Alloys";

    static final String MODID = ArmoryExpansion.MODID + "-" + INTEGRATION_ID;
    static final String NAME = ArmoryExpansion.NAME + " - " + INTEGRATION_NAME;
    static final String DEPENDENCIES =
            "required-after:" + ArmoryExpansion.MODID + "; ";

    protected String modid = "";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = INTEGRATION_ID;
        Logger logger = event.getModLog();
        Property property = ArmoryExpansion.config
                .get("integrations", modid, true, "Whether integration with " + modid + " should be enabled");
        boolean isEnabled = property == null || property.getBoolean();
        ArmoryExpansion.config.save();
        if(isEnabled){
            for (TiCAlloy alloy:loadAlloysFromJson(event.getModConfigurationDirectory(), modid)) {
                alloy.registerTiCAlloy();
                logger.info("Registered tinker's alloy {" + alloy.getName() + "};");
            }
        }
    }

    private TiCAlloy[] loadAlloysFromJson(String path){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();

        TiCAlloy[] jsonAlloys = new TiCAlloy[0];
        try {
            File input = new File(path);
            jsonAlloys = gson.fromJson(new FileReader(input), TiCAlloy[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return jsonAlloys;
    }

    private TiCAlloy[] loadAlloysFromJson(File configDir, String root, String modid){
        return this.loadAlloysFromJson(configDir.getPath() + "/" + root + "/" + modid + ".json");
    }

    private TiCAlloy[] loadAlloysFromJson(File configDir, String modid){
        return this.loadAlloysFromJson(configDir, "armoryexpansion", modid);
    }
}
