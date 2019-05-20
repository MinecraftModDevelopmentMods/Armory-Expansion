package org.softc.armoryexpansion.common.integration.aelib.integration;

import java.io.InputStream;

public class JsonIntegration extends AbstractIntegration {
    String modId;
    private String json;

    JsonIntegration(String modId, String json) {
        this.modId = modId;
        this.json = json;
    }

    @Override
    protected void loadMaterialsFromWeb() {
        // Not used by this class
    }

    @Override
    protected void loadMaterialsFromSource() {
        InputStream stream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(json + "-materials.json");
        if(stream == null){
            return;
        }
        this.loadMaterialsFromJson(stream);
    }

    @Override
    protected void loadAlloysFromWeb() {
        // Not used by this class
    }

    @Override
    protected void loadAlloysFromSource() {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream(json + "-alloys.json");
        if(stream == null){
            return;
        }
        this.loadAlloysFromJson(stream);
    }

    @Override
    protected void loadConfigFromWeb() {
        // Not used by this class
    }

    @Override
    protected void loadConfigFromSource() {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream(json + "-config.json");
        if(stream == null){
            return;
        }
        this.loadConfigFromJson(stream);
    }
}
