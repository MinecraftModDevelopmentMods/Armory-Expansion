package org.softc.armoryexpansion.common.util;

public enum ConfigFileSuffixEnum {
    CONFIG_SUFFIX("-config"),
    ALLOYS_SUFFIX("-alloys"),
    ORE_DICT_ENTRIES_SUFFIX("-oreDictEntries"),
    MATERIALS_SUFFIX("-materials"),
    TRAITS_SUFFIX("-traits");

    private final String suffix;

    ConfigFileSuffixEnum(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return this.suffix;
    }
}
