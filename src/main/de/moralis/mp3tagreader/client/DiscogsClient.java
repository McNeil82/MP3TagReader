package de.moralis.mp3tagreader.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;

public class DiscogsClient {

    private static final String URL = "https://api.discogs.com/releases/";
    private static final String ACCEPT = "application/vnd.discogs.v2.html+json";
    private static final String USER_AGENT = "MP3TagReader/1.0";

    public InputStream getRelease(String releaseNumber) {
        String fullUrl = URL + releaseNumber;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(fullUrl);
        get.addHeader("Accept", ACCEPT);
        get.addHeader("User-Agent", USER_AGENT);

        try {
            HttpResponse response = httpClient.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                response.getEntity().getContent();
            }
        } catch (IOException error) {
            error.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException error) {
                error.printStackTrace();
            }
        }

        return null;
    }
}
