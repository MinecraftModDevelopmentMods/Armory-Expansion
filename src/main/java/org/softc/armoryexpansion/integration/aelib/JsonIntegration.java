package org.softc.armoryexpansion.integration.aelib;

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
        this.loadMaterialsFromJson(
                getClass()
                .getClassLoader()
                .getResourceAsStream(json + "-materials.json"));
    }

    @Override
    protected void loadAlloysFromWeb() {

    }

    @Override
    protected void loadAlloysFromSource() {
        this.loadAlloysFromJson(
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(json + "-alloys.json"));
    }
}
