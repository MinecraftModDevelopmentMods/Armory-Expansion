package org.softc.armoryexpansion.integration.aelib;

import net.minecraftforge.fml.common.Loader;

public class JsonIntegration extends AbstractIntegration {
    String modId;
    String json;

    public JsonIntegration(String modId, String json) {
        this.modId = modId;
        this.json = json;
    }

    @Override
    protected void loadMaterialsFromSource() {
        if (Loader.isModLoaded(modId)) {
            loadMaterialsFromJson(
                    getClass()
                            .getClassLoader()
                            .getResourceAsStream(json));
        }
    }
}
