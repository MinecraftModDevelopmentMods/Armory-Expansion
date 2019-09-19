package org.softc.armoryexpansion.common.integration.aelib.config;

import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.IBasicMaterial;

public class ArmorMaterialConfigOptions {
    protected String name;
    protected boolean material;
    private boolean fluid;
    protected boolean traits;

    protected ArmorConfigOptions armorOptions;

    ArmorMaterialConfigOptions(IBasicMaterial iBasicMaterial) {
        this.name = iBasicMaterial.getIdentifier();
        this.material = true;
        this.fluid = iBasicMaterial.isCastable();
        this.traits = true;

        this.armorOptions = new ArmorConfigOptions(iBasicMaterial.isArmorMaterial());
    }

    protected ArmorMaterialConfigOptions(String name, boolean material, boolean traits, boolean armorOptions) {
        this.name = name;
        this.material = material;
        this.traits = traits;
        this.armorOptions = new ArmorConfigOptions(armorOptions);
    }

    String getName() {
        return this.name;
    }

    public boolean materialEnabled(){
        return this.material;
    }

    boolean fluidEnabled(){
        return this.fluid;
    }

    boolean traitsEnabled(){
        return this.traits;
    }

    public boolean armorEnabled(){
        return this.armorOptions.enableArmor;
    }

    public boolean coreEnabled(){
        return this.armorOptions.enableCore;
    }

    public boolean platesEnabled(){
        return this.armorOptions.enablePlates;
    }

    public boolean trimEnabled(){
        return this.armorOptions.enableTrim;
    }

    static class ArmorConfigOptions {
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
