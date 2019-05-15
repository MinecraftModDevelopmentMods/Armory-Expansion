package org.softc.armoryexpansion.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
            "required-after:" + ArmoryExpansion.MODID + "; ";

    private static File configDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = INTEGRATION_ID;
        this.enableForceJsonCreation();
        super.preInit(event);
        configDir = event.getModConfigurationDirectory();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        // TODO Figure out how to print a list of all trait identifiers
        this.exportAllTraitsToJson(configDir);
        // TODO Figure out how to print a list of all part identifiers
        this.exportAllPartsToJson(configDir);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event){
        super.registerBlocks(event);
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
//        File output = new File(configDir.getPath() + "/armoryexpansion/parts.txt");
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

    @Override
    protected void loadAlloysFromSource() {
        // Left empty on purpose
        // All the alloys should be added through the JSON file
    }
}
