package org.softc.armoryexpansion.integration;

import org.softc.armoryexpansion.integration.util.constructs_armory.ConArmStats;
import org.softc.armoryexpansion.integration.util.tinkers_construct.TiCMaterial;
import org.softc.armoryexpansion.integration.util.tinkers_construct.TiCStats;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractIntegration implements IIntegration {
    private String modId;
    protected List<TiCMaterial> materials = new LinkedList<>();

    public AbstractIntegration(String modId){
        this.modId = modId;
        this.addToIntegrationList();
    }

    public String getModId(){
        return modId;
    }

    protected abstract void setMaterials();

    protected void oredictMaterials() {
        this.materials.forEach(TiCMaterial::registerOreDict);
    }

    protected void registerMaterials() {
        this.materials.forEach(TiCMaterial::registerTinkersMaterial);
    }

    protected void registerMaterialStats() {
        this.materials.forEach(m -> {
            m.updateTinkersMaterial();
            if (m.isToolMaterial()) {
                TiCStats.registerMaterialToolStats(m);
            }
            if (m.isBowMaterial()) {
                TiCStats.registerMaterialBowStats(m);
            }
            if (m.isFletchingMaterial()) {
                TiCStats.registerMaterialFletchingStats(m);
            }
            if (m.isArmorMaterial()) {
                ConArmStats.registerMaterialArmorStats(m);
            }
        });
    }

    @Override
    public void addToIntegrationList() {
        Integrations.integrationList.add(this);
    }
}
