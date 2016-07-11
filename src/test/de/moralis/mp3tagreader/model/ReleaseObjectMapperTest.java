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
        assertEquals(release.getTitle(), "Tour09 Feast Of V Senses");
        assertEquals(release.getYear(), "2009");

        List<Artist> artists = release.getArtists();
        assertEquals(artists.size(), 1);
        assertEquals(artists.get(0).getName(), "DIR EN GREY");

        List<Label> labels = release.getLabels();
        assertEquals(labels.size(), 1);
        assertEquals(labels.get(0).getName(), "sun-krad");

        List<String> genres = release.getGenres();
        assertEquals(genres.size(), 1);
        assertEquals(genres.get(0), "Rock");

        List<Track> tracklist = release.getTracklist();
        assertEquals(tracklist.size(), 59);
        assertEquals(tracklist.get(0).getPosition(), "");
        assertEquals(tracklist.get(0).getTitle(), "2009.5.3 新木場Studio Coast(追加公演)");
        assertEquals(tracklist.get(1).getPosition(), "DVD1-1");
        assertEquals(tracklist.get(1).getTitle(), "Sa Bir");
        assertEquals(tracklist.get(8).getPosition(), "DVD1-8");
        assertEquals(tracklist.get(8).getTitle(), "凌辱の雨");
        assertEquals(tracklist.get(20).getPosition(), "DVD1-20");
        assertEquals(tracklist.get(20).getTitle(), "Clever Sleazoid");
        assertEquals(tracklist.get(31).getPosition(), "DVD2-10");
        assertEquals(tracklist.get(31).getTitle(), "C");
        assertEquals(tracklist.get(42).getPosition(), "DVD2-20");
        assertEquals(tracklist.get(42).getTitle(), "Child Prey");
        assertEquals(tracklist.get(53).getPosition(), "CD-8");
        assertEquals(tracklist.get(53).getTitle(), "Repetition Of Hatred");
    }
}