package org.softc.armoryexpansion.util;

public class Math {
    public static float clamp(float value, float min, float max) {
        return value < min ? min : (value > max ? max : value);
    }
}
