package org.softc.armoryexpansion.common.web;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.softc.armoryexpansion.ArmoryExpansion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArmoryExpansionWebClient implements IWebClient{

    private static ArmoryExpansionWebClient instance;

    public static ArmoryExpansionWebClient getInstance(){
        if(instance == null){
            instance = new ArmoryExpansionWebClient();
        }
        return instance;
    }

    @Override
    public InputStream sendGet(String url, String file) throws Exception {
        URL urlObject = new URL(url + "/" + file);

        // TODO Use ServerProxy when opening connections
        HttpURLConnection urlConnection;
        if(FMLCommonHandler.instance().getSide().equals(Side.CLIENT)){
            urlConnection = this.getClientSidedConnection(urlObject);
        }
        else{
            urlConnection = this.getServerSidedConnection(urlObject);
        }

        urlConnection.setRequestMethod("GET");
        urlConnection.setConnectTimeout(ArmoryExpansion.getConnectTimeout());

        urlConnection.setRequestProperty("User-Agent", ArmoryExpansion.MODID + "/" + ArmoryExpansion.VERSION);

        ArmoryExpansion.logger.info("Attempting to connect to : " + url + " at: " + file);

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            return urlConnection.getInputStream();
        }
        return null;
    }

    @Override
    public InputStream sendGet(String url, String file, String folder) throws Exception {
        return sendGet(url, folder + "/" + file);
    }

    @Override
    public HttpURLConnection getServerSidedConnection(URL urlObject) throws IOException {
        return org.softc.armoryexpansion.server.web.ProxyConnection.getServerSidedConnection(urlObject);
    }

    @Override
    public HttpURLConnection getClientSidedConnection(URL urlObject) throws IOException {
        return org.softc.armoryexpansion.client.web.ProxyConnection.getClientSidedConnection(urlObject);
    }
}
