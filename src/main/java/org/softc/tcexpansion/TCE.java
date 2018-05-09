package org.softc.tcexpansion;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mod(
        modid = TCE.MODID,
        name = TCE.NAME,
        version = TCE.VERSION,
        dependencies = "required-after:tconstruct; required-after:conarm; after:basemetals; after:modernmetals; after:fantasymetals; after:plustic; after:moartinkers; after:taiga;"
)
public class TCE
{
    static final String MODID = "tcexpansion";
    static final String NAME = "Tinker's Construct Expansion";
    static final String VERSION = "0.0.2";

    private static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();

        String[] ignoredMaterialsArray =
                {
                        "_internal_render1",
                        "_internal_render2",
                        "_internal_render3",
                        "_internal_render4",
                        "_internal_renderstring",
                        "string",
                        "slimevine_blue",
                        "slimevine_purple",
                        "vine",
                        "endrod",
                        "feather",
                        "slimeleaf_blue",
                        "slimeleaf_orange",
                        "slimeleaf_purple",
                        "leaf",
                        "ice"
                };
        List<String> ignoredMaterialsList = Arrays.stream(ignoredMaterialsArray).collect(Collectors.toList());

        /* TODO Add traits for Nickel, Antimony, Cupronickel, Platinum, Tin, Invar, Zinc, Bismuth, Chromium, Titanium, Magnesium, Osmium, Aluminum, Manganese, Plutonium, Iridium, Tungsten, Thorium, Aluminum Brass, Beryllium, Cadmium, Nichrome, Stainless Steel, Uranium, Galvanized Steel, Tantalum, Zirconium, Boron, Rutile*/

        for (Material material:TinkerRegistry.getAllMaterials())
        {
            if (!ignoredMaterialsList.contains(material.identifier))
            {
                if (!material.hasStats("core") && material.hasStats("head"))
                {
                    // logger.info("DEBUG: Material Identifier: " + material.identifier);
                    HeadMaterialStats materialHead = material.getStats("head");
                    HeadMaterialStats ironHead = TinkerMaterials.iron.getStats("head");
                    CoreMaterialStats ironCore = TinkerMaterials.iron.getStats("core");

                    TinkerRegistry.addMaterialStats(
                            material,
                            new CoreMaterialStats(
                                    ironCore.durability * materialHead.durability / ironHead.durability,
                                    ironCore.defense * materialHead.attack / ironHead.attack));
                }
                if (!material.hasStats("plates") && material.hasStats("handle"))
                {
                    // logger.info("DEBUG: Material Identifier: " + material.identifier);
                    HandleMaterialStats materialHandle = material.getStats("handle");
                    HandleMaterialStats ironHandle = TinkerMaterials.iron.getStats("handle");
                    PlatesMaterialStats ironPlates = TinkerMaterials.iron.getStats("plates");

                    float ironPlatesToughness = ironPlates.toughness > 0f ? ironPlates.toughness : 1;

                    TinkerRegistry.addMaterialStats(
                            material,
                            new PlatesMaterialStats(
                                    materialHandle.modifier,
                                    ironPlates.durability * materialHandle.durability / ironHandle.durability,
                                    ironPlatesToughness * materialHandle.durability / ironHandle.durability));
                }
                if (!material.hasStats("trim") && material.hasStats("extra"))
                {
                    // logger.info("DEBUG: Material Identifier: " + material.identifier);
                    ExtraMaterialStats materialExtra = material.getStats("extra");
                    ExtraMaterialStats ironExtra = TinkerMaterials.iron.getStats("extra");
                    TrimMaterialStats ironTrim = TinkerMaterials.iron.getStats("trim");

                    TinkerRegistry.addMaterialStats(
                            material,
                            new TrimMaterialStats((
                                    ironTrim.extraDurability * materialExtra.extraDurability / ironExtra.extraDurability)));
                }
            }
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}