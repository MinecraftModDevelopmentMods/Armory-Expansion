package org.softc.armoryexpansion.common.util;

public enum Math {
    ;

    public static float clampFloat(float value, float min, float max) {
        return value < min ? min : (java.lang.Math.min(value, max));
    }

    public static int clampInt(float value, int min, int max) {
        //noinspection NumericCastThatLosesPrecision
        return (int) clampFloat(value, min, max);
    }
}
