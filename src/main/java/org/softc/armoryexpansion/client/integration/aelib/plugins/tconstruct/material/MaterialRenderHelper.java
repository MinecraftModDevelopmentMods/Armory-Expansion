package org.softc.armoryexpansion.client.integration.aelib.plugins.tconstruct.material;

import net.minecraftforge.fml.relauncher.*;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.materials.*;

@SideOnly(Side.CLIENT)
public enum MaterialRenderHelper {
    ;

    public static void setMaterialRenderInfo(Material material, IBasicMaterial ticMaterial) {
        MaterialRenderInfo materialRenderInfo;
        switch (ticMaterial.getType()) {
            case METAL:
                materialRenderInfo = new MaterialRenderInfo.Metal(ticMaterial.getColor());
                break;
            case METALTEXTURED:
                materialRenderInfo = new MaterialRenderInfo.MetalTextured(ticMaterial.getTexture(), ticMaterial.getColor(), 0.4f, 0.4f, 0.1f);
                break;
            default:
                materialRenderInfo = new MaterialRenderInfo.Default(ticMaterial.getColor());
                break;
        }
        material.setRenderInfo(materialRenderInfo);
    }
}
