package org.softc.armoryexpansion.client.integration.aelib.plugins.tinkers_construct.material;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.softc.armoryexpansion.common.integration.aelib.plugins.general.material.BasicMaterial;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.materials.Material;

@SideOnly(Side.CLIENT)
public class MaterialRenderHelper {
    public static void setMaterialRenderInfo(Material material, BasicMaterial ticMaterial) {
        MaterialRenderInfo materialRenderInfo = new MaterialRenderInfo.Default(ticMaterial.getColor());
        switch (ticMaterial.getType()) {
            case METAL:
                materialRenderInfo = new MaterialRenderInfo.Metal(ticMaterial.getColor());
                break;
            case METALTEXTURED:
                materialRenderInfo = new MaterialRenderInfo.MetalTextured(ticMaterial.getTexture(), ticMaterial.getColor(), 0.4f, 0.4f, 0.1f);
                break;
            default:
                break;
        }
        material.setRenderInfo(materialRenderInfo);
    }
}
