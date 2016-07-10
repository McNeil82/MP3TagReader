package de.moralis.mp3tagreader.model;

public enum FrameId {

    AENC("Audio encryption"),
    APIC("Attached picture"),
    COMM("Comments"),
    COMR("Commercial frame"),
    ENCR("Encryption method registration"),
    EQUA("Equalization"),
    ETCO("Event timing codes"),
    GEOB("General encapsulated object"),
    GRID("Group identification registration"),
    IPLS("Involved people list"),
    LINK("Linked information"),
    MCDI("Music CD identifier"),
    MLLT("MPEG location lookup table"),
    OWNE("Ownership frame"),
    PRIV("Private frame"),
    PCNT("Play counter"),
    POPM("Popularimeter"),
    POSS("Position synchronisation frame"),
    RBUF("Recommended buffer size"),
    RVAD("Relative volume adjustment"),
    RVRB("Reverb"),
    SYLT("Synchronized lyric/text"),
    SYTC("Synchronized tempo codes"),
    TALB("Album/Movie/Show title"),
    TBPM("BPM (beats per minute)"),
    TCOM("Composer"),
    TCON("Content type"),
    TCOP("Copyright message"),
    TDAT("Date"),
    TDLY("Playlist delay"),
    TENC("Encoded by"),
    TEXT("Lyricist/Text writer"),
    TFLT("File type"),
    TIME("Time"),
    TIT1("Content group description"),
    TIT2("Title/songname/content description"),
    TIT3("Subtitle/Description refinement"),
    TKEY("Initial key"),
    TLAN("Language(s)"),
    TLEN("Length"),
    TMED("Media type"),
    TOAL("Original album/movie/show title"),
    TOFN("Original filename"),
    TOLY("Original lyricist(s)/text writer(s)"),
    TOPE("Original artist(s)/performer(s)"),
    TORY("Original release year"),
    TOWN("File owner/licensee"),
    TPE1("Lead performer(s)/Soloist(s)"),
    TPE2("Band/orchestra/accompaniment"),
    TPE3("Conductor/performer refinement"),
    TPE4("Interpreted, remixed, or otherwise modified by"),
    TPOS("Part of a set"),
    TPUB("Publisher"),
    TRCK("Track number/Position in set"),
    TRDA("Recording dates"),
    TRSN("Internet radio station name"),
    TRSO("Internet radio station owner"),
    TSIZ("Size"),
    TSRC("ISRC (international standard recording code)"),
    TSSE("Software/Hardware and settings used for encoding"),
    TYER("Year"),
    TXXX("User defined text information frame"),
    UFID("Unique file identifier"),
    USER("Terms of use"),
    USLT("Unsychronized lyric/text transcription"),
    WCOM("Commercial information"),
    WCOP("Copyright/Legal information"),
    WOAF("Official audio file webpage"),
    WOAR("Official artist/performer webpage"),
    WOAS("Official audio source webpage"),
    WORS("Official internet radio station homepage"),
    WPAY("Payment"),
    WPUB("Publishers official webpage"),
    WXXX("User defined URL link frame");

    private String description;

    FrameId(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name() + " (" + description + ')';
    }
}