package org.softc.tcexpansion;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
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

@Mod(
        modid = TCE.MODID,
        name = TCE.NAME,
        version = TCE.VERSION,
        dependencies = "required-after:tconstruct; " +
                "required-after:conarm; " +
                "after:basemetals; " +
                "after:modernmetals; " +
                "after:fantasymetals; " +
                "after:plustic; " +
                "after:moartinkers; " +
                "after:taiga;" +
                "after:acintegration;" +
                "after:BloodArsenal;" +
                "after:enderiointegrationtic;" +
                "after:integrationforegoing;" +
                "after:twilightforest;"
)
public class TCE
{
    static final String MODID = "armoryexpansion";
    static final String NAME = "Armory Expansion";
    static final String VERSION = "0.0.4";

    static Configuration config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Logger logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(event.getSuggestedConfigurationFile());
        Config.syncConfig();

        /* TODO Add traits for Nickel, Antimony, Cupronickel, Platinum, Tin, Invar, Zinc, Bismuth, Chromium, Titanium, Magnesium, Osmium, Aluminum, Manganese, Plutonium, Iridium, Tungsten, Thorium, Aluminum Brass, Beryllium, Cadmium, Nichrome, Stainless Steel, Uranium, Galvanized Steel, Tantalum, Zirconium, Boron, Rutile*/

        for (Property property:Config.properties)
        {
            if (property.getBoolean())
            {
                Material material = TinkerRegistry.getMaterial(property.getName());
                logger.info("Adding armor parts for " + material.getLocalizedName());

                HeadMaterialStats materialHead = material.getStats("head");
                HeadMaterialStats ironHead = TinkerMaterials.iron.getStats("head");
                CoreMaterialStats ironCore = TinkerMaterials.iron.getStats("core");

                TinkerRegistry.addMaterialStats(
                        material,
                        new CoreMaterialStats(
                                ironCore.durability * materialHead.durability / ironHead.durability,
                                ironCore.defense * materialHead.attack / ironHead.attack));

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

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }
}