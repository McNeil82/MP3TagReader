package de.moralis.mp3tagreader.client;

import de.moralis.mp3tagreader.model.Release;
import de.moralis.mp3tagreader.model.ReleaseObjectMapper;
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

    public Release getRelease(String releaseNumber) {
        String fullUrl = URL + releaseNumber;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(fullUrl);
        get.addHeader("Accept", ACCEPT);
        get.addHeader("User-Agent", USER_AGENT);

        Release release = null;

        try {
            HttpResponse response = httpClient.execute(get);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                InputStream content = response.getEntity().getContent();
                ReleaseObjectMapper releaseObjectMapper = new ReleaseObjectMapper();
                release = releaseObjectMapper.mapFromJson(content);
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

        return release;
    }
}
