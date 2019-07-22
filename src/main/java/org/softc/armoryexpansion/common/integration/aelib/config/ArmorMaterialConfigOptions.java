package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;

@SuppressWarnings("WeakerAccess")
public class ArmorMaterialConfigOptions {
    protected String name;
    protected boolean material;
    private boolean fluid;
    protected boolean traits;

    ArmorConfigOptions armorOptions;

    ArmorMaterialConfigOptions() {
        this.name = "DEFAULT";
        this.material = true;
        this.traits = true;
        this.armorOptions = new ArmorConfigOptions(false);
    }

    ArmorMaterialConfigOptions(IBasicMaterial iBasicMaterial) {
        this.name = iBasicMaterial.getIdentifier();
        this.material = true;
        this.fluid = iBasicMaterial.isCastable();
        this.traits = true;

        this.armorOptions = new ArmorConfigOptions(iBasicMaterial.isArmorMaterial());
    }

    String getName() {
        return name;
    }

    public boolean isMaterialEnabled(){
        return this.material;
    }

    boolean isFluidEnabled(){
        return this.fluid;
    }

    boolean isTraitsEnabled(){
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

    class ArmorConfigOptions {
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
}
