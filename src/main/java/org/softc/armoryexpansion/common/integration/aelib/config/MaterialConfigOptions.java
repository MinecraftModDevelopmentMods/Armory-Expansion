package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;

public class MaterialConfigOptions extends ArmorMaterialConfigOptions{
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

    MaterialConfigOptions(IBasicMaterial iBasicMaterial) {
        super(iBasicMaterial);

        this.toolOptions = new ToolConfigOptions(iBasicMaterial.isToolMaterial());
        this.rangedOptions = new RangedConfigOptions(iBasicMaterial.isRangedMaterial());
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

    boolean isRangedEnabled(){
        return this.rangedOptions.enableRanged;
    }

    public boolean isBowEnabled(){
        return this.rangedOptions.enableBow;
    }

    public boolean isBowStringEnabled() {
        return this.rangedOptions.enableBowString;
    }

    boolean isShaftEnabled(){
        return this.rangedOptions.enableShaft;
    }

    public boolean isFletchingEnabled(){
        return this.rangedOptions.enableFletching;
    }

    public boolean isProjectileEnabled(){
        return this.rangedOptions.enableProjectile;
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

    @Override
    public boolean equals(Object o){
        return o instanceof MaterialConfigOptions && this.name.equals(((MaterialConfigOptions) o).name);
    }
}
