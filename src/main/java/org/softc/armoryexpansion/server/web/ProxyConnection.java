package org.softc.armoryexpansion.server.web;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@SideOnly(Side.SERVER)
public class ProxyConnection {
    public static HttpURLConnection getServerSidedConnection(URL urlObject) throws IOException {
        return (HttpURLConnection) urlObject.openConnection();
    }
}
