package org.softc.armoryexpansion.util;

public class Math {
    public static float Clamp(float value, float min, float max) {
        return value < min ? min : (value > max ? max : value);
    }
}
