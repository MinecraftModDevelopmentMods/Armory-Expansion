package org.softc.armoryexpansion.integration;

import c4.conarm.common.armor.traits.ArmorTraits;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.mcmoddev.lib.integration.plugins.TinkersConstruct;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.aelib.AbstractIntegration;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.MaterialRenderType;
import org.softc.armoryexpansion.integration.plugins.tinkers_construct.TiCMaterial;
import slimeknights.tconstruct.tools.TinkerTraits;

@Mod(
        modid = IceAndFireIntegration.MODID,
        name = IceAndFireIntegration.NAME,
        version = ArmoryExpansion.VERSION,
        dependencies = IceAndFireIntegration.DEPENDENCIES
)
@Mod.EventBusSubscriber
public class IceAndFireIntegration extends AbstractIntegration {
    static final String MODID = ArmoryExpansion.MODID + "-" + IceAndFire.MODID;
    static final String NAME = ArmoryExpansion.NAME + " - " + IceAndFire.NAME;
    static final String DEPENDENCIES =
            "required-after:" + TinkersConstruct.PLUGIN_MODID + "; " +
            "required-after:" + c4.conarm.ConstructsArmory.MODID + "; " +
            "required-after:" + ArmoryExpansion.MODID + "; " +
            "after:" + IceAndFire.MODID + "; ";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.modid = IceAndFire.MODID;
        super.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    protected void loadMaterialsFromSource() {
        if (Loader.isModLoaded(IceAndFire.MODID)) {
            // Fire Dragon Scales
            addMaterial(
                    new TiCMaterial("scalereddragon", "iceandfire:dragonscales_red", 0xAA0000)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setProjectileMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.autoforge.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.infernal.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            addMaterial(
                    new TiCMaterial("scalegreendragon", "iceandfire:dragonscales_green", 0x00AA00)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.autoforge.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.shielding.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            addMaterial(
                    new TiCMaterial("scalebronzedragon", "iceandfire:dragonscales_bronze", 0x845d2e)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.autoforge.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.prideful.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            addMaterial(
                    new TiCMaterial("scalegraydragon", "iceandfire:dragonscales_gray", 0x555555)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.autoforge.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.steady.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            // Ice Dragon Scales
            addMaterial(
                    new TiCMaterial("scalebluedragon", "iceandfire:dragonscales_blue", 0xe1f7f0)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
//                        .addGlobalArmorTrait(MMDTraitsCA.icy.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.lightweight.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            addMaterial(
                    new TiCMaterial("scalewhitedragon", "iceandfire:dragonscales_white", 0xFFFFFF)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
//                        .addGlobalArmorTrait(MMDTraitsCA.icy.identifier)
                            .addPrimaryArmorTrait(TinkerTraits.writable2.identifier) // Writable
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            addMaterial(
                    new TiCMaterial("scalesapphiredragon", "iceandfire:dragonscales_sapphire", 0x5555FF)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
//                        .addGlobalArmorTrait(MMDTraitsCA.icy.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            addMaterial(
                    new TiCMaterial("scalesilverdragon", "iceandfire:dragonscales_silver", 0xAAAAAA)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
//                        .addGlobalArmorTrait(MMDTraitsCA.icy.identifier)
                            .addPrimaryArmorTrait(ArmorTraits.blessed.identifier)
                            .setDurability(36)
                            .setMagicaffinity(15)
                            .setDefense(9)
                            .setToughness(2));

            // Dragon Bones
            addMaterial(
                    new TiCMaterial("bonedragon", "iceandfire:dragonbone", 0xAAAAAA)
                            .setToolMaterial(true)
                            .setBowMaterial(true)
                            .setProjectileMaterial(true)
                            .setCraftable(true)
                            .setDurability(36)
                            .setRange(1.25f)
                            .setDamage(8)
                            .setHarvestLevel(4)
                            .setHardness(10)
                            .setMagicaffinity(15));

            // Troll Leather
            addMaterial(
                    new TiCMaterial("leatherforesttroll", "iceandfire:troll_leather_forest", 0x394231)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .setDurability(20)
                            .setMagicaffinity(10)
                            .setDefense(7)
                            .setToughness(1));

            addMaterial(
                    new TiCMaterial("leatherfrosttroll", "iceandfire:troll_leather_frost", 0x31423e)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .setDurability(20)
                            .setMagicaffinity(10)
                            .setDefense(7)
                            .setToughness(1));

            addMaterial(
                    new TiCMaterial("leathermountaintroll", "iceandfire:troll_leather_mountain", 0x3b4240)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .setDurability(20)
                            .setMagicaffinity(10)
                            .setDefense(7)
                            .setToughness(1));

            // Death Worm Chitin
            addMaterial(
                    new TiCMaterial("chitintandeathworm", "iceandfire:deathworm_chitin", 0, 0xc4c4c4)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .setDurability(15)
                            .setMagicaffinity(5)
                            .setDefense(7)
                            .setToughness(1.5f));

            addMaterial(
                    new TiCMaterial("chitinwhitedeathworm", "iceandfire:deathworm_chitin", 1, 0xFFFFFF)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .setDurability(15)
                            .setMagicaffinity(5)
                            .setDefense(7)
                            .setToughness(1.5f));

            addMaterial(
                    new TiCMaterial("chitinbrowndeathworm", "iceandfire:deathworm_chitin", 2, 0x7a2929)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .setDurability(15)
                            .setMagicaffinity(5)
                            .setDefense(7)
                            .setToughness(1.5f));

            // Myrmex Chitin
            addMaterial(
                    new TiCMaterial("chitindesertmyrmex", "iceandfire:myrmex_desert_chitin", 0x593802)
                            .setType(MaterialRenderType.METAL)
//                        .setTexture("iceandfire:textures/items/myrmex_desert_chitin.png")
                            .setArmorMaterial(true)
                            .setToolMaterial(true)
                            .setProjectileMaterial(true)
                            .setCraftable(true)
                            .setDurability(20)
                            .setDamage(-1)
                            .setHarvestLevel(3)
                            .setHardness(6)
                            .setMagicaffinity(15)
                            .setDefense(7));

            addMaterial(
                    new TiCMaterial("chitinjunglemyrmex", "iceandfire:myrmex_jungle_chitin", 0x02594b)
                            .setType(MaterialRenderType.METAL)
//                        .setTexture("iceandfire:textures/items/myrmex_jungle_chitin.png")
                            .setArmorMaterial(true)
                            .setToolMaterial(true)
                            .setProjectileMaterial(true)
                            .setCraftable(true)
                            .setDurability(20)
                            .setDamage(-1)
                            .setHarvestLevel(3)
                            .setHardness(6)
                            .setMagicaffinity(15)
                            .setDefense(7));

            // Sea Serpent Scales
            addMaterial(
                    new TiCMaterial("scaleblueseaserpent", "iceandfire:sea_serpent_scales_blue", 0x5555FF)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

            addMaterial(
                    new TiCMaterial("scalebronzeseaserpent", "iceandfire:sea_serpent_scales_bronze", 0xFFAA00)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

            addMaterial(
                    new TiCMaterial("scaledeepblueseaserpent", "iceandfire:sea_serpent_scales_deepblue", 0x0000AA)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

            addMaterial(
                    new TiCMaterial("scalegreenseaserpent", "iceandfire:sea_serpent_scales_green", 0x00AA00)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

            addMaterial(
                    new TiCMaterial("scalepurpleseaserpent", "iceandfire:sea_serpent_scales_purple", 0xAA00AA)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

            addMaterial(
                    new TiCMaterial("scaleredseaserpent", "iceandfire:sea_serpent_scales_red", 0xFF5555)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

            addMaterial(
                    new TiCMaterial("scaletealseaserpent", "iceandfire:sea_serpent_scales_teal", 0x00AAAA)
                            .setType(MaterialRenderType.METAL)
                            .setArmorMaterial(true)
                            .setCraftable(true)
                            .addGlobalArmorTrait(ArmorTraits.rough.identifier)
                            .addGlobalArmorTrait(ArmorTraits.aquaspeed.identifier)
                            .setDurability(30)
                            .setMagicaffinity(25)
                            .setDefense(7)
                            .setToughness(2.5f));

        }
    }
}
