package de.moralis.mp3tagreader.model;

public enum CharSet {
    ISO_8859_1("ISO-8859-1", 1, 1),
    UTF_16_BE("UTF-16BE", 3, 2),
    UTF_16_LE("UTF-16LE", 3, 2);

    private String name;
    private int encodingDescriptionOffset;
    private int bytesPerChar;

    CharSet(String name, int encodingDescriptionOffset, int bytesPerChar) {
        this.name = name;
        this.encodingDescriptionOffset = encodingDescriptionOffset;
        this.bytesPerChar = bytesPerChar;
    }

    public String getName() {
        return name;
    }

    public int getEncodingDescriptionOffset() {
        return encodingDescriptionOffset;
    }

    public int getBytesPerChar() {
        return bytesPerChar;
    }
}
