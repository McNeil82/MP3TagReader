package de.moralis.mp3tagreader.model;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class ReleaseObjectMapperTest {

    private ReleaseObjectMapper releaseObjectMapper = new ReleaseObjectMapper();

    @Test
    public void releaseIsParsed() throws IOException {
        InputStream jsonInputStream = getClass().getResourceAsStream("example.json");
        Release release = releaseObjectMapper.mapFromJson(jsonInputStream);
        assertEquals(release.getTitle(), "Six Ugly");
        assertEquals(release.getYear(), "2002");

        List<Artist> artists = release.getArtists();
        assertEquals(artists.size(), 1);
        assertEquals(artists.get(0).getName(), "Dir en grey");

        List<Label> labels = release.getLabels();
        assertEquals(labels.size(), 1);
        assertEquals(labels.get(0).getName(), "Firewall Div.");

        List<String> genres = release.getGenres();
        assertEquals(genres.size(), 1);
        assertEquals(genres.get(0), "Rock");

        List<Track> tracklist = release.getTracklist();
        assertEquals(tracklist.size(), 6);
        assertEquals(tracklist.get(0).getPosition(), "1");
        assertEquals(tracklist.get(0).getTitle(), "Mr.Newsman");
        assertEquals(tracklist.get(1).getPosition(), "2");
        assertEquals(tracklist.get(1).getTitle(), "Ugly");
        assertEquals(tracklist.get(2).getPosition(), "3");
        assertEquals(tracklist.get(2).getTitle(), "Hades");
        assertEquals(tracklist.get(3).getPosition(), "4");
        assertEquals(tracklist.get(3).getTitle(), "Umbrella");
        assertEquals(tracklist.get(4).getPosition(), "5");
        assertEquals(tracklist.get(4).getTitle(), "Children");
        assertEquals(tracklist.get(5).getPosition(), "6");
        assertEquals(tracklist.get(5).getTitle(), "秒「」深");
    }
}