package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IMaterial;

public class MaterialConfigOptions {
    private String name;
    private boolean material;
    private boolean fluid;
    private boolean traits;

    private ArmorConfigOptions armorOptions;
    private ToolConfigOptions toolOptions;
    private RangedConfigOptions rangedOptions;

    MaterialConfigOptions() {
        this.name = "DEFAULT";
        this.material = true;
        this.traits = true;
        this.armorOptions = new ArmorConfigOptions(false);
        this.toolOptions = new ToolConfigOptions(false);
        this.rangedOptions = new RangedConfigOptions(false);
    }

    MaterialConfigOptions(IMaterial iMaterial) {
        this.name = iMaterial.getIdentifier();
        this.material = true;
        this.fluid = iMaterial.isCastable();
        this.traits = true;

        this.armorOptions = new ArmorConfigOptions(iMaterial.isArmorMaterial());
        this.toolOptions = new ToolConfigOptions(iMaterial.isToolMaterial());
        this.rangedOptions = new RangedConfigOptions(iMaterial.isRangedMaterial());
    }

    String getName() {
        return name;
    }

    boolean isMaterialEnabled(){
        return this.material;
    }

    boolean isFluidEnabled(){
        return this.fluid;
    }

    boolean isTraitsEnabled(){
        return this.traits;
    }

    boolean isArmorEnabled(){
        return this.armorOptions.enableArmor;
    }

    boolean isCoreEnabled(){
        return this.armorOptions.enableCore;
    }

    boolean isPlatesEnabled(){
        return this.armorOptions.enablePlates;
    }

    boolean isTrimEnabled(){
        return this.armorOptions.enableTrim;
    }

    boolean isToolEnabled(){
        return this.toolOptions.enableTool;
    }

    boolean isHeadEnabled(){
        return this.toolOptions.enableHead;
    }

    boolean isHandleEnabled(){
        return this.toolOptions.enableHandle;
    }

    boolean isExtraEnabled(){
        return this.toolOptions.enableExtra;
    }

    boolean isRangedEnabled(){
        return this.rangedOptions.enableRanged;
    }

    boolean isBowEnabled(){
        return this.rangedOptions.enableBow;
    }

    boolean isBowStringEnabled() {
        return this.rangedOptions.enableBowString;
    }

    boolean isShaftEnabled(){
        return this.rangedOptions.enableShaft;
    }

    boolean isFletchingEnabled(){
        return this.rangedOptions.enableFletching;
    }

    boolean isProjectileEnabled(){
        return this.rangedOptions.enableProjectile;
    }

    private class ArmorConfigOptions {
        private boolean enableArmor;
        private boolean enableCore;
        private boolean enablePlates;
        private boolean enableTrim;


        ArmorConfigOptions(boolean defVal) {
            this(defVal, defVal, defVal, defVal);
        }

        ArmorConfigOptions(boolean enableArmor, boolean enableCore, boolean enablePlates, boolean enableTrim) {
            this.enableArmor = enableArmor;
            this.enableCore = enableCore;
            this.enablePlates = enablePlates;
            this.enableTrim = enableTrim;
        }
    }

    private class ToolConfigOptions {
        private boolean enableTool;
        private boolean enableHead;
        private boolean enableHandle;
        private boolean enableExtra;

        ToolConfigOptions(boolean defVal) {
            this(defVal, defVal, defVal, defVal);
        }

        ToolConfigOptions(boolean enableTool, boolean enableHead, boolean enableHandle, boolean enableExtra) {
            this.enableTool = enableTool;
            this.enableHead = enableHead;
            this.enableHandle = enableHandle;
            this.enableExtra = enableExtra;
        }
    }

    private class RangedConfigOptions {
        private boolean enableRanged;
        private boolean enableBow;
        private boolean enableBowString;
        private boolean enableShaft;
        private boolean enableFletching;
        private boolean enableProjectile;

        RangedConfigOptions(boolean defVal) {
            this(defVal, defVal, defVal, defVal, defVal, defVal);
        }

        RangedConfigOptions(boolean enableRanged, boolean enableBow, boolean enableBowString, boolean enableShaft, boolean enableFletching, boolean enableProjectile) {
            this.enableRanged = enableRanged;
            this.enableBow = enableBow;
            this.enableBowString = enableBowString;
            this.enableShaft = enableShaft;
            this.enableFletching = enableFletching;
            this.enableProjectile = enableProjectile;
        }
    }
}
