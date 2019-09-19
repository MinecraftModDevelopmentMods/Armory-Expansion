package org.softc.armoryexpansion.common.integration.aelib.plugins.tinkersconstruct.alloys;

public interface IAlloy {
    void registerTiCAlloy();

    AlloyComponent getOutput();

    AlloyComponent[] getInputs();

    String getName();
}
