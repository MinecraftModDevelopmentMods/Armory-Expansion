package org.softc.armoryexpansion.integration.aelib;

import net.minecraftforge.fml.common.Loader;

public class JsonIntegration extends AbstractIntegration {
    private String modId;
    private String json;

    public JsonIntegration(String modId, String json) {
        this.modId = modId;
        this.json = json;
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
    protected void loadAlloysFromSource() {
        if (Loader.isModLoaded(modId)) {
            this.loadAlloysFromJson(
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(json + "-alloys.json"));
        }
    }
}
