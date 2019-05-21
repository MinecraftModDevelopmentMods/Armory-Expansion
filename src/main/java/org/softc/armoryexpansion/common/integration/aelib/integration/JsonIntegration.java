package org.softc.armoryexpansion.common.integration.aelib.integration;

import java.io.InputStream;

public class JsonIntegration extends AbstractIntegration {
    private String json;

    protected JsonIntegration(String modId, String json) {
        this.modid = modId;
        this.json = json;
//        MinecraftForge.EVENT_BUS.register(this);
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
