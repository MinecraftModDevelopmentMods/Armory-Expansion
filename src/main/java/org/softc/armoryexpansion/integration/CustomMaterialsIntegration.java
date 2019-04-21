package org.softc.armoryexpansion.integration;

import c4.conarm.ConstructsArmory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mcmoddev.lib.integration.plugins.TinkersConstruct;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.aelib.AbstractIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

    static File configDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = INTEGRATION_ID;
        super.preInit(event);
        configDir = event.getModConfigurationDirectory();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // TODO Figure out how to print a list of all trait identifiers
        exportAllTraitsToJson(configDir);
        // TODO Figure out how to print a list of all part identifiers
        exportAllPartsToJson(configDir);
    }

    private void exportAllTraitsToJson(File configDir){
        Set<String> traits = new HashSet<>();
        TinkerRegistry.getAllMaterials().forEach(m -> m.getAllTraits().forEach(t -> traits.add(t.getIdentifier())));

        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();
        File output = new File(configDir.getPath() + "/armoryexpansion/traits.txt");
        try {
            FileWriter writer = new FileWriter(output);
            writer.write(gson.toJson(traits));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportAllPartsToJson(File configDir){
        // TODO This needs to somehow export both tool and armor part identifiers
//        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
//        Gson gson = builder.create();
//        File output = new File(configDir.getPath() + "\\armoryexpansion\\parts.txt");
//        try {
//            FileWriter writer = new FileWriter(output);
//            writer.write(gson.toJson(traits));
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void loadMaterialsFromSource() {
        // Left empty on purpose
        // All the materials should be added through the JSON file
    }
}
