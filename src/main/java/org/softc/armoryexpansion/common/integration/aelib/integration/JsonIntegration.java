package org.softc.armoryexpansion.common.integration.aelib.integration;

import java.io.InputStream;

public class JsonIntegration extends AbstractIntegration {
    private String json;

    protected JsonIntegration(String modId) {
        this(modId, modId, modId);
    }

    protected JsonIntegration(String modId, String root, String json) {
        super(modId, root);
        this.json = json;
    }

    @Override
    protected void loadMaterialsFromSource() {
        InputStream stream =
                this.getClass()
                        .getClassLoader()
                        .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-materials.json");
        if (null != stream) {
            this.loadMaterialsFromJson(stream);
        }
    }

    @Override
    protected void loadOreDictionaryEntriesFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-oreDictEntries.json");
        if (null != stream) {
            this.loadOreDictionaryEntriesFromJson(stream);
        }
    }

    @Override
    protected void loadAlloysFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-alloys.json");
        if (null != stream) {
            this.loadAlloysFromJson(stream);
        }
    }

    @Override
    protected void loadConfigFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-config.json");
        if (null != stream) {
            this.loadConfigFromJson(stream);
        }
    }

    @Override
    protected void loadTraitsFromSource() {
        InputStream stream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("assets/" + this.root + "/data/" + this.json + "/" + this.json + "-traits.json");
        if (null != stream) {
            this.loadTraitsFromJson(stream);
        }
    }
}
