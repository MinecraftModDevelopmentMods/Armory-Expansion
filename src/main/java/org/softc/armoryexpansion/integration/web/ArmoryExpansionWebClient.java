package org.softc.armoryexpansion.integration.web;

import org.softc.armoryexpansion.ArmoryExpansion;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArmoryExpansionWebClient {

    private static ArmoryExpansionWebClient instance;

    private ArmoryExpansionWebClient() {

    }

    public static ArmoryExpansionWebClient getInstance(){
        if(instance == null){
            instance = new ArmoryExpansionWebClient();
        }
        return instance;
    }

    // HTTP GET request
    public InputStream sendGet(String url, String file) throws Exception {
        URL urlObject = new URL(url + "/" + file);
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();

        // optional default is GET
        urlConnection.setRequestMethod("GET");

        //add request header
        urlConnection.setRequestProperty("User-Agent", ArmoryExpansion.MODID + "/" + ArmoryExpansion.VERSION);
//        urlConnection.setRequestProperty("Location", file);

        ArmoryExpansion.logger.info("Attempting to connect to : " + url + " at: " + file);

        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            return urlConnection.getInputStream();
        }
        return null;
    }

    public InputStream sendGet(String url, String file, String folder) throws Exception {
        return sendGet(url, folder + "/" + file);
    }
}
