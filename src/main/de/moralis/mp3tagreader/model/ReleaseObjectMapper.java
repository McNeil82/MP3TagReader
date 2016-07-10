package de.moralis.mp3tagreader.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class ReleaseObjectMapper {

    public Release mapFromJson(InputStream json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(json, Release.class);
        } catch (IOException error) {
            error.printStackTrace();
        }

        return null;
    }
}
