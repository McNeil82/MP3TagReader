package de.moralis.models;

import java.io.IOException;
import java.io.RandomAccessFile;

public class ReadFile {

    private RandomAccessFile myFile = null;

    private String version = "";

    private boolean unsynchronisation = false;

    private boolean extendedHeader = false;

    private boolean experimentalIndicator = false;

    private int tagSize = 0;

    public ReadFile(RandomAccessFile myFile) {
        setMyFile(myFile);
        init();
    }

    private void init() {
        if (isID3v2()) {
            setVersion();
            setFlags();
            setTagSize();
        }
    }

    /**
     * Liest die ersten 3 Bytes der Datei.
     *
     * @return <code>true</code> wenn ein ID3v2-Tag vorhanden ist, ansonsten <code>false</code>.
     */
    private boolean isID3v2() {
        StringBuilder mySb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            try {
                mySb.append((char) getMyFile().read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mySb.length() > 0 && mySb.toString().equals("ID3");
    }

    public int getTagSize() {
        return tagSize;
    }

    private void setTagSize() {
        StringBuilder mySb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            try {
                String h = Integer.toHexString(getMyFile().read());
                if (h.length() == 1) {
                    h = "0" + h;
                }
                mySb.append(h);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tagSize = Integer.valueOf(mySb.toString(), 16);
    }

    /**
     * Liest ein Byte aus, wobei Bit-7 für Unsynchronisation, Bit-6 für Extended header und Bit-5 für
     * Experimental indicator steht.
     */
    private void setFlags() {
        try {
            int b = getMyFile().read();
            if (b % 64 != b) {
                setUnsynchronisation(true);
                b -= 64;
            }
            if (b % 32 != b) {
                setExtendedHeader(true);
                b -= 32;
            }
            if (b % 16 != b) {
                setExperimentalIndicator(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return version;
    }

    private void setVersion() {
        StringBuilder mySb = new StringBuilder("2.");
        for (int i = 0; i < 2; i++) {
            try {
                mySb.append(getMyFile().read());
                if (i == 0) {
                    mySb.append(".");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.version = mySb.toString();
    }

    public RandomAccessFile getMyFile() {
        return myFile;
    }

    public void setMyFile(RandomAccessFile myFile) {
        this.myFile = myFile;
    }

    public boolean hasUnsynchronisation() {
        return unsynchronisation;
    }

    public void setUnsynchronisation(boolean unsynchronisation) {
        this.unsynchronisation = unsynchronisation;
    }

    public boolean hasExtendedHeader() {
        return extendedHeader;
    }

    public void setExtendedHeader(boolean extendedHeader) {
        this.extendedHeader = extendedHeader;
    }

    public boolean hasExperimentalIndicator() {
        return experimentalIndicator;
    }

    public void setExperimentalIndicator(boolean experimentalIndicator) {
        this.experimentalIndicator = experimentalIndicator;
    }
}
