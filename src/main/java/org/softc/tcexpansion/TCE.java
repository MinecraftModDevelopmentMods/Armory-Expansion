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
    public static final String MODID = "tcexpansion";
    public static final String NAME = "Tinker's Construct Expansion";
    public static final String VERSION = "0.0.1";

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
                    HeadMaterialStats stat = material.getStats("head");
                    TinkerRegistry.addMaterialStats(material, new CoreMaterialStats(12 * stat.durability / 204, 15 * stat.attack / 4));
                }
                if (!material.hasStats("plates") && material.hasStats("handle"))
                {
                    // logger.info("DEBUG: Material Identifier: " + material.identifier);
                    HandleMaterialStats stat = material.getStats("handle");
                    TinkerRegistry.addMaterialStats(material, new PlatesMaterialStats( stat.modifier, 5 * stat.durability / 60, 0 * stat.durability / 60));
                }
                if (!material.hasStats("trim") && material.hasStats("extra"))
                {
                    // logger.info("DEBUG: Material Identifier: " + material.identifier);
                    ExtraMaterialStats stat = material.getStats("extra");
                    TinkerRegistry.addMaterialStats(material, new TrimMaterialStats((3.5F * stat.extraDurability / 50)));
                }
            }
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}