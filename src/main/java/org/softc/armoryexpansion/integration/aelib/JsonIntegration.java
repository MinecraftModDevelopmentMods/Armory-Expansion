package org.softc.armoryexpansion.integration.aelib;

import net.minecraftforge.fml.common.Loader;

public class JsonIntegration extends AbstractIntegration {
    String modId;
    private String json;

    JsonIntegration(String modId, String json) {
        this.modId = modId;
        this.json = json;
    }

    @Override
    protected void loadMaterialsFromWeb() {

    }

    @Override
    protected void loadMaterialsFromSource() {
        if (Loader.isModLoaded(modId)) {
            this.loadMaterialsFromJson(
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(json + "-materials.json"));
        }
    }

    @Override
    protected void loadAlloysFromWeb() {

    }

    @Override
    protected void loadAlloysFromSource() {
        if (Loader.isModLoaded(modId)) {
            this.loadAlloysFromJson(
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(json + "-alloys.json"));
        }
    }
}
