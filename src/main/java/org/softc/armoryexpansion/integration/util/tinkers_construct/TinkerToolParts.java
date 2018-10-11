package org.softc.armoryexpansion.integration.util.tinkers_construct;

import java.util.Locale;

//TODO Credit the MMD team for the enum
public enum TinkerToolParts {
    HEAD, HANDLE, EXTRA, BOW, BOWSTRING, PROJECTILE, SHAFT, FLETCHING, GENERAL;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
