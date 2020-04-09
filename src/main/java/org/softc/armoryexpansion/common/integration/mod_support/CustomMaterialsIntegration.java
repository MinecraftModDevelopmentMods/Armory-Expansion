package org.softc.armoryexpansion.common.integration.mod_support;

import com.google.gson.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.softc.armoryexpansion.*;
import org.softc.armoryexpansion.common.integration.aelib.integration.*;
import org.softc.armoryexpansion.common.util.*;
import slimeknights.tconstruct.library.events.*;
import slimeknights.tconstruct.library.traits.*;

import java.io.*;
import java.util.*;

@Mod(
        modid = CustomMaterialsIntegration.MODID,
        name = CustomMaterialsIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = CustomMaterialsIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class CustomMaterialsIntegration extends IndependentJsonIntegration {
    private static final String INTEGRATION_ID = "custommaterials";
    private static final String INTEGRATION_NAME = "Custom Materials";

    static final String MODID = ArmoryExpansion.MODID + "-" + INTEGRATION_ID;
    static final String NAME = ArmoryExpansion.NAME + " - " + INTEGRATION_NAME;
    static final String DEPENDENCIES = ""
            + "required-after:" + ArmoryExpansion.MODID + "; "
            ;

    private static File configDirFile;

    private Collection<String> traitIdentifierList = new HashSet<>();

    public CustomMaterialsIntegration() {
        super(INTEGRATION_ID, ArmoryExpansion.MODID, INTEGRATION_ID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modId = INTEGRATION_ID;
        this.enableForceJsonCreation();
        configDirFile = event.getModConfigurationDirectory();
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        this.exportAllTraitsToJson(configDirFile);
        this.exportAllPartsToJson(configDirFile);
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<? super Block> event){
        super.registerBlocks(event);
    }

    @SubscribeEvent
    public void registerTraits(MaterialEvent.TraitRegisterEvent<? super AbstractTrait> event) {
        this.traitIdentifierList.add(event.trait.getIdentifier());
    }

    private void exportAllTraitsToJson(File configDir){
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting().setLenient();
        Gson gson = builder.create();
        File output = new File(configDir.getPath() + "/armoryexpansion/traits.txt");
        try (FileWriter writer = new FileWriter(output)) {
            writer.write(gson.toJson(this.traitIdentifierList));
        } catch (IOException e) {
            this.logger.error("Could not write to " + configDir.getPath() + "/armoryexpansion/traits.txt", e);
        }
    }

    private void exportAllPartsToJson(File configDir){
//        // TODO This needs to somehow export both tool and armor part identifiers
//        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
//        Gson gson = builder.create();
//        File output = new File(configDir.getPath() + "/armoryexpansion/parts.txt");
//        try (FileWriter writer = new FileWriter(output)) {
//            writer.write(gson.toJson(TinkerRegistry.getToolParts()));
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

    @Override
    protected void saveAlloysToJson(File dir, String fileName, boolean forceCreate){
        if(!this.alloys.values().isEmpty() || forceCreate) {
            Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
            File output = new File(this.getFilePath(dir, fileName, ConfigFileSuffixEnum.ALLOYS_SUFFIX));
            output.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(output)) {
                writer.write(this.returnAlloyExample());
                writer.write(gson.toJson(this.alloys.values()));
            } catch (IOException e) {
                this.logger.error("Could not write to " + this.getFilePath(dir, fileName, ConfigFileSuffixEnum.ALLOYS_SUFFIX), e);
            }
        }
    }

    @Override
    protected boolean enableForceJsonCreation(){
        return true;
    }

    protected String returnAlloyExample() {
        return "//  {\n" +
                "//    \"output\": {\n" +
                "//      \"fluid\": \"iron\",\n" +
                "//      \"amount\": 144\n" +
                "//    },\n" +
                "//    \"inputs\": [\n" +
                "//      {\n" +
                "//        \"fluid\": \"copper\",\n" +
                "//        \"amount\": 108\n" +
                "//      },\n" +
                "//      {\n" +
                "//        \"fluid\": \"lead\",\n" +
                "//        \"amount\": 36\n" +
                "//      }\n" +
                "//    ]\n" +
                "//  }\n";
    }
}
