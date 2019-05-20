package org.softc.armoryexpansion.integration.aelib.config;

public class MaterialConfigOptions {
    private String name;
    private boolean material;
    private boolean fluid;
    private boolean traits;

    private ArmorConfigOptions armorOptions;
    private ToolConfigOptions toolOptions;
    private RangedConfigOptions rangedOptions;

    MaterialConfigOptions() {
        this("DEFAULT");
    }

    MaterialConfigOptions(String name) {
        this.name = name;
        armorOptions = new ArmorConfigOptions();
        toolOptions = new ToolConfigOptions();
        rangedOptions = new RangedConfigOptions();
        material = true;
        fluid = true;
        traits = true;
    }

    public String getName() {
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
        private boolean enableArmor = true;
        private boolean enableCore = true;
        private boolean enablePlates = true;
        private boolean enableTrim = true;
    }

    private class ToolConfigOptions {
        private boolean enableTool = true;
        private boolean enableHead = true;
        private boolean enableHandle = true;
        private boolean enableExtra = true;
    }

    private class RangedConfigOptions {
        private boolean enableRanged = true;
        private boolean enableBow = true;
        private boolean enableShaft = true;
        private boolean enableFletching = true;
        private boolean enableProjectile = true;
    }
}
