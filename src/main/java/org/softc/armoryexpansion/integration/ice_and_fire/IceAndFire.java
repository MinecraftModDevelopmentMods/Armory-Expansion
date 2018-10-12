package org.softc.armoryexpansion.integration.ice_and_fire;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.softc.armoryexpansion.integration.AbstractIntegration;
import org.softc.armoryexpansion.integration.util.tinkers_construct.TiCStats;

import java.util.HashMap;
import java.util.Map;

public class IceAndFire extends AbstractIntegration {
    private static Map<String, Item> oreDictedItems = new HashMap<>();
    public static AbstractIntegration INSTANCE = new IceAndFire();

    private IceAndFire(){
        super(com.github.alexthe666.iceandfire.IceAndFire.MODID);
        //this.addToIntegrationList();
    }

    private static void addOreDictEntry(String identifier, String itemName){
        Item item = Item.getByNameOrId(itemName);
        oreDictedItems.put(identifier, item);
        OreDictionary.registerOre(identifier, item);
    }

    private static void addOreDictEntries(){
        //TODO Add missing oreDict entries for Ice And Fire materials
        addOreDictEntry("scaleReddragon","iceandfire:dragonscales_red");
        addOreDictEntry("scaleGreendragon","iceandfire:dragonscales_green");
        addOreDictEntry("scaleBronzedragon","iceandfire:dragonscales_bronze");
        addOreDictEntry("scaleGraydragon","iceandfire:dragonscales_gray");

        addOreDictEntry("scaleBluedragon","iceandfire:dragonscales_blue");
        addOreDictEntry("scaleWhitedragon","iceandfire:dragonscales_white");
        addOreDictEntry("scaleSapphiredragon","iceandfire:dragonscales_sapphire");
        addOreDictEntry("scaleSilverdragon","iceandfire:dragonscales_silver");
    }

    private static void addTinkersMaterial(String identifier, int color){
        TiCStats.addTinkersMaterial(identifier, color, oreDictedItems.get(identifier));
    }

    private static void addIceAndFireMaterials(){
        addTinkersMaterial("scaleReddragon", 11141120);
        TiCStats.addToolMaterial("scaleReddragon", 100, 3, 10, 5, 4, 20);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void registerItems(RegistryEvent.Register<Item> event) {
        addOreDictEntries();
        addIceAndFireMaterials();
    }

    private static void addTinkersMaterialItem(String identifer){
        TiCStats.addTinkersMaterialItem(identifer, oreDictedItems.get(identifer));
    }

    private static void addIceAndFireRecipes(){
        addTinkersMaterialItem("scaleReddraon");
    }

    @Override
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        addIceAndFireRecipes();
    }
}
