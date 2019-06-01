package org.softc.armoryexpansion.common.integration.aelib.integration;

import net.minecraftforge.common.MinecraftForge;

import java.io.InputStream;

public class JsonIntegration extends AbstractIntegration {
    private String json;

    protected JsonIntegration(String modId) {
        this(modId, modId, modId);
    }

    protected JsonIntegration(String modId, String root, String json) {
        this.modid = modId;
        this.root = root;
        this.json = json;
    }

    @Override
    protected void loadMaterialsFromSource() {
        InputStream stream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("assets/" + this.root + "/data/" + json + "-materials.json");
        if(stream == null){
            return;
        }
        this.loadMaterialsFromJson(stream);
    }

    @Override
    protected void loadAlloysFromSource() {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + json + "-alloys.json");
        if(stream == null){
            return;
        }
        this.loadAlloysFromJson(stream);
    }

    @Override
    protected void loadConfigFromSource() {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + json + "-config.json");
        if(stream == null){
            return;
        }
        this.loadConfigFromJson(stream);
    }
}
