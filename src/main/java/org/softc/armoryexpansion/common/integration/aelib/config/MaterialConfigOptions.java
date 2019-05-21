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

    public boolean isMaterialEnabled(){
        return this.material;
    }

    public boolean isFluidEnabled(){
        return this.fluid;
    }

    public boolean isTraitsEnabled(){
        return this.traits;
    }

    public boolean isArmorEnabled(){
        return this.armorOptions.enableArmor;
    }

    public boolean isCoreEnabled(){
        return this.armorOptions.enableCore;
    }

    public boolean isPlatesEnabled(){
        return this.armorOptions.enablePlates;
    }

    public boolean isTrimEnabled(){
        return this.armorOptions.enableTrim;
    }

    public boolean isToolEnabled(){
        return this.toolOptions.enableTool;
    }

    public boolean isHeadEnabled(){
        return this.toolOptions.enableHead;
    }

    public boolean isHandleEnabled(){
        return this.toolOptions.enableHandle;
    }

    public boolean isExtraEnabled(){
        return this.toolOptions.enableExtra;
    }

    public boolean isRangedEnabled(){
        return this.rangedOptions.enableRanged;
    }

    public boolean isBowEnabled(){
        return this.rangedOptions.enableBow;
    }

    public boolean isBowStringEnabled() {
        return this.rangedOptions.enableBowString;
    }

    public boolean isShaftEnabled(){
        return this.rangedOptions.enableShaft;
    }

    public boolean isFletchingEnabled(){
        return this.rangedOptions.enableFletching;
    }

    public boolean isProjectileEnabled(){
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
