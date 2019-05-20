package org.softc.armoryexpansion.common.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public interface IWebClient {
    InputStream sendGet(String url, String file) throws Exception;

    InputStream sendGet(String url, String file, String folder) throws Exception;

    HttpURLConnection getServerSidedConnection(URL urlObject) throws IOException;

    HttpURLConnection getClientSidedConnection(URL urlObject) throws IOException;
}
