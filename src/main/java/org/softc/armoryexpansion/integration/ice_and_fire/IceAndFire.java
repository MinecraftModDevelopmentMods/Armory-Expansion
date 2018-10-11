package org.softc.armoryexpansion.integration.ice_and_fire;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.softc.armoryexpansion.integration.util.tinkers_construct.TiCStats;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.HashMap;
import java.util.Map;

import static net.minecraftforge.fml.common.Loader.isModLoaded;

public class IceAndFire {
    private static Map<String, Item> oreDictedItems = new HashMap<>();

    private static void addOreDictEntry(String identifier, String itemName){
        Item item = Item.getByNameOrId(itemName);
        oreDictedItems.put(identifier, item);
        OreDictionary.registerOre(identifier, item);
    }

    public static void preInit(final FMLPreInitializationEvent event){
        //TODO Add missing oreDict entries for Ice And Fire materials
        addOreDictEntry("reddragonScale","iceandfire:dragonscales_red");
        addOreDictEntry("greendragonScale","iceandfire:dragonscales_green");
        addOreDictEntry("bronzedragonScale","iceandfire:dragonscales_bronze");
        addOreDictEntry("graydragonScale","iceandfire:dragonscales_gray");

        addOreDictEntry("bluedragonScale","iceandfire:dragonscales_blue");
        addOreDictEntry("whitedragonScale","iceandfire:dragonscales_white");
        addOreDictEntry("sapphiredragonScale","iceandfire:dragonscales_sapphire");
        addOreDictEntry("silverdragonScale","iceandfire:dragonscales_silver");


    }

    private static void addIceAndFireToolMaterial(String identifier, int durability, float hardness, float damage, float magicaffinity, int harvestLevel){
        TiCStats.registerMaterialToolStats(identifier, durability, hardness, damage, magicaffinity, harvestLevel);
    }

    private static void addIceAndFireBowMaterial(String identifier, int durability, float range, float damage, float magicaffinity){
        TiCStats.registerMaterialBowStats(identifier, durability, range, damage, magicaffinity);
    }

    private static void addIceAndFireMaterial(String identifier, int durability, float hardness, float damage, float magicaffinity, int harvestLevel, float range){
        addIceAndFireToolMaterial(identifier, durability, hardness, damage, magicaffinity, harvestLevel);
        addIceAndFireBowMaterial(identifier, durability, range, damage, magicaffinity);

    }

    private static void addIceAndFireMaterial(String identifier, int color, Item item){
        Material material = new Material(identifier, color);
        material.setCastable(false)
                .setCraftable(true)
                .addItemIngot(identifier);
        material.setRepresentativeItem(item);
        TinkerRegistry.addMaterial(material);
    }

    private static void addIceAndFireMaterials(){
        addIceAndFireMaterial("reddragonScale", TextFormatting.DARK_RED.getColorIndex(), oreDictedItems.get("reddragonScale"));
        addIceAndFireMaterial("reddragonScale", 100, 3, 10, 5, 4, 20);
    }

    public static void integrate() {
        if (isModLoaded("iceandfire")) {
            addIceAndFireMaterials();;
        }
    }
}
