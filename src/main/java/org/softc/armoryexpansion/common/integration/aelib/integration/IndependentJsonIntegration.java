package org.softc.armoryexpansion.common.integration.aelib.integration;

import org.softc.armoryexpansion.ArmoryExpansion;

public class IndependentJsonIntegration extends JsonIntegration {
    protected IndependentJsonIntegration(String modId, String root, String json) {
        super(modId, root, json);
    }

    @Override
    public boolean isLoadable() {
        return ArmoryExpansion.isIntegrationEnabled(this.modId);
    }
}
