package org.softc.armoryexpansion.client.web;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@SideOnly(Side.CLIENT)
public class ProxyConnection {
    public static HttpURLConnection getClientSidedConnection(URL urlObject) throws IOException {
        return (HttpURLConnection) urlObject.openConnection(Minecraft.getMinecraft().getProxy());
    }
}
