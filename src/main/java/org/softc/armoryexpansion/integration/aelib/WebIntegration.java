package org.softc.armoryexpansion.integration.aelib;

import net.minecraftforge.fml.common.Loader;
import org.softc.armoryexpansion.ArmoryExpansion;

public class WebIntegration extends JsonIntegration {
    public WebIntegration(String modId, String json) {
        super(modId, json);
    }

    @Override
    protected void loadMaterialsFromWeb() {
        if (Loader.isModLoaded(modId)) {
            for (String url: ArmoryExpansion.getWebServerList()) {
                try {
                    this.loadMaterialsFromJson(this.webClient.sendGet(url, modId + "-materials.json", "armory-expansion"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void loadAlloysFromWeb() {
        if (Loader.isModLoaded(modId)) {
            for (String url: ArmoryExpansion.getWebServerList()) {
                try {
                    this.loadAlloysFromJson(this.webClient.sendGet(url, modId + "-alloys.json"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
