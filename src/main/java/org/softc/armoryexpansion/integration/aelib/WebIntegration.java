package org.softc.armoryexpansion.integration.aelib;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.softc.armoryexpansion.ArmoryExpansion;
import org.softc.armoryexpansion.integration.web.ArmoryExpansionWebClient;

import java.io.File;

public class WebIntegration extends JsonIntegration {
    public WebIntegration(String modId, String json) {
        super(modId, json);
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        this.webClient = ArmoryExpansionWebClient.getInstance();
        Property property = ArmoryExpansion.config
                .get("integrations", modid, true, "Whether integration with " + modid + " should be enabled");
        isEnabled = property == null || property.getBoolean();
        ArmoryExpansion.config.save();
        if(isEnabled){
            this.configHelper = new Config(new Configuration(
                    new File(event.getModConfigurationDirectory().getPath() + "/" + ArmoryExpansion.MODID + "/" + modid + ".cfg")));
            this.setMaterials(event);
            this.setAlloys(event);
            this.configHelper.syncConfig(materials);
            this.registerMaterials();
//            this.registerMaterialFluids();
            this.registerAlloys();
            this.registerMaterialStats();
        }
    }

    @Override
    protected void loadMaterialsFromWeb() {
        for (String url: ArmoryExpansion.getWebServerList()) {
            try {
                this.loadMaterialsFromJson(this.webClient.sendGet(url, modId + "-materials.json", "armory-expansion"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void loadAlloysFromWeb() {
        for (String url: ArmoryExpansion.getWebServerList()) {
            try {
                this.loadAlloysFromJson(this.webClient.sendGet(url, modId + "-alloys.json","armory-expansion"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
