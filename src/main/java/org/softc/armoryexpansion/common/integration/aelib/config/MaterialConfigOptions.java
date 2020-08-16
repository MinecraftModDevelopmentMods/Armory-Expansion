package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.*;

import java.util.*;

public class MaterialConfigOptions extends ArmorMaterialConfigOptions{
    private final ToolConfigOptions toolOptions;
    private final RangedConfigOptions rangedOptions;

    MaterialConfigOptions() {
        super("DEFAULT", true, true, false);
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

    private static class ToolConfigOptions {
        private final boolean enableTool;
        private final boolean enableHead;
        private final boolean enableHandle;
        private final boolean enableExtra;

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

    private static class RangedConfigOptions {
        private final boolean enableRanged;
        private final boolean enableBow;
        private final boolean enableBowString;
        private final boolean enableShaft;
        private final boolean enableFletching;
        private final boolean enableProjectile;

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
    public boolean equals(Object obj){
        return obj instanceof MaterialConfigOptions && this.getName().equals(((ArmorMaterialConfigOptions) obj).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toolOptions, this.rangedOptions);
    }
}
