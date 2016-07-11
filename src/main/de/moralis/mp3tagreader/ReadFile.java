package de.moralis.mp3tagreader;

import de.moralis.mp3tagreader.model.CharSet;
import de.moralis.mp3tagreader.model.Frame;
import de.moralis.mp3tagreader.model.FrameId;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFile {

    private RandomAccessFile file = null;

    private String version = "";

    private boolean unsynchronisation = false;

    private boolean extendedHeader = false;

    private boolean experimentalIndicator = false;

    private int totalTagSize = 0;

    private List<Frame> frames = new ArrayList<>();

    public ReadFile(RandomAccessFile file) throws IOException {
        this.file = file;
        init();
    }

    private void init() throws IOException {
        try {
            if (isID3v2()) {
                setVersion();
                checkVersion();
                setFlags();
                setTagSize();
                setFrames();
            }
        } finally {
            file.close();
        }
    }

    /**
     * Liest die ersten 3 Bytes der Datei.
     *
     * @return <code>true</code> wenn ein ID3v2-Tag vorhanden ist, ansonsten <code>false</code>.
     */
    private boolean isID3v2() {
        String version = readChars(3);

        return version.length() > 0 && "ID3".equals(version);
    }

    public int getTotalTagSize() {
        return totalTagSize;
    }

    private void setTagSize() {
        totalTagSize = calculateTagSizeWithoutHeader() + 10;
    }

    private int calculateTagSizeWithoutHeader() {
        int size = 0;
        try {
            size += file.readByte() << 21;
            size += file.readByte() << 14;
            size += file.readByte() << 7;
            size += file.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    /**
     * Liest ein Byte aus, wobei Bit-7 für Unsynchronisation, Bit-6 für Extended header und Bit-5 für Experimental indicator steht.
     */
    private void setFlags() {
        boolean[] flags = readFlags();
        setUnsynchronisation(flags[7]);
        setExtendedHeader(flags[6]);
        setExperimentalIndicator(flags[5]);
    }

    public String getVersion() {
        return version;
    }

    private void setVersion() {
        StringBuilder mySb = new StringBuilder("2.");
        for (int i = 0; i < 2; i++) {
            try {
                mySb.append(file.read());
                if (i == 0) {
                    mySb.append(".");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.version = mySb.toString();
    }

    private void checkVersion() {
        if (!"2.3.0".equals(getVersion())) {
            throw new UnsupportedOperationException("Unsupported ID3v2 version: " + getVersion() + "!");
        }
    }

    public List<Frame> getFrames() {
        return frames;
    }

    private void setFrames() throws IOException {
        while (file.getFilePointer() < totalTagSize) {
            String id = readChars(4);
            Integer size = readFrameSize();
            boolean[] flagsFirstByte = readFlags();
            boolean[] flagsSecondByte = readFlags();

            FrameId frameId = null;
            try {
                frameId = FrameId.valueOf(id);
            } catch (IllegalArgumentException e) {
                if (size == 0) {
                    System.err.println("Padding found (" + id + ")! Terminating tag reading at " + file.getFilePointer() + "!");
                    break;
                }
            }

            Frame frame = new Frame();
            frame.setId(frameId);
            frame.setSize(size);
            frame.setTagAlterPreservation(flagsFirstByte[7]);
            frame.setFileAlterPreservation(flagsFirstByte[6]);
            frame.setReadOnly(flagsFirstByte[5]);
            frame.setCompression(flagsSecondByte[7]);
            frame.setEncryption(flagsSecondByte[6]);
            frame.setGroupingIdentity(flagsSecondByte[5]);

            if (frameId != null && frameId.name().startsWith("T") || frameId == FrameId.WXXX) {
                frame.setEncoding(determineEncoding());

                if (frameId == FrameId.TXXX || frameId == FrameId.WXXX) {
                    long filePointerBefore = file.getFilePointer();
                    frame.setXxxDescription(readString(frame.getSize(), frame.getEncoding(), true));
                    long filePointerAfter = file.getFilePointer();
                    int offset = (int) (filePointerAfter - filePointerBefore);
                    frame.setContent(readString(frame.getSize(), offset, frame.getEncoding(), false));
                } else {
                    frame.setContent(readString(frame.getSize(), frame.getEncoding(), false));
                }

                frames.add(frame);
            } else {
                System.err.println("Frame can not be processed yet! Skipping Frame (" + id + ")!");
                file.skipBytes(frame.getSize());
            }
        }
    }

    private int readFrameSize() {
        int size = 0;

        try {
            size = file.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    private CharSet determineEncoding() throws UnsupportedEncodingException {
        CharSet encoding;

        int encodingByte = 0;
        try {
            encodingByte = file.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (encodingByte == 0) {
            encoding = CharSet.ISO_8859_1;
        } else if (encodingByte == 1) {
            encoding = determineUnicodeEncoding();
        } else {
            throw new UnsupportedEncodingException("Unknown encoding byte " + encodingByte);
        }

        return encoding;
    }

    private CharSet determineUnicodeEncoding() throws UnsupportedEncodingException {
        int[] hex = new int[2];
        for (int i = 0; i < 2; i++) {
            try {
                hex[i] = file.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (hex[0] == 254 && hex[1] == 255) {
            return CharSet.UTF_16_BE;
        } else if (hex[0] == 255 && hex[1] == 254) {
            return CharSet.UTF_16_LE;
        }

        throw new UnsupportedEncodingException("Unknown unicode encoding description " + Arrays.toString(hex));
    }

    private String readChars(int bytes) {
        StringBuilder mySb = new StringBuilder();
        for (int i = 0; i < bytes; i++) {
            try {
                mySb.append((char) file.read());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mySb.toString();
    }

    private boolean[] readFlags() {
        boolean[] flags = new boolean[8];

        try {
            int b = file.read();
            for (int i = 7; i >= 0; i--) {
                int pow = (int) Math.pow(2, i);

                if (b % pow != b) {
                    flags[i] = true;
                    b -= pow;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flags;
    }

    private String readString(int frameSize, CharSet encoding, boolean terminationRequired) {
        return readString(frameSize, 0, encoding, terminationRequired);
    }

    private String readString(int frameSize, int offset, CharSet encoding, boolean terminationRequired) {
        int bytesToRead = frameSize - encoding.getEncodingDescriptionOffset() - offset;

        StringBuilder stringBuilder = new StringBuilder();
        boolean terminationByteFound = false;
        int bytesProcessed = 0;
        int bytesPerChar = encoding.getBytesPerChar();

        try {
            for (int i = 0; i < bytesToRead; i += bytesPerChar, bytesProcessed += bytesPerChar) {

                byte firstByte = file.readByte();
                byte secondByte = 0x00;
                if (bytesPerChar == 2) {
                    secondByte = file.readByte();
                }

                if (bytesPerChar == 1 && Byte.toUnsignedInt(firstByte) != 0) {
                    stringBuilder.append(new String(new byte[]{firstByte}, encoding.getName()));
                } else if (bytesPerChar == 2 && (Byte.toUnsignedInt(firstByte) != 0 || Byte.toUnsignedInt(secondByte) != 0)) {
                    stringBuilder.append(new String(new byte[]{firstByte, secondByte}, encoding.getName()));
                } else {
                    bytesProcessed += bytesPerChar;
                    terminationByteFound = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long filePointer = 0;
        try {
            filePointer = file.getFilePointer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (terminationRequired && !terminationByteFound) {
            System.err.println("Required termination byte(s) missing at " + filePointer + "! Content will be ignored!");
            return "";
        } else if (!terminationRequired && terminationByteFound && bytesToRead != bytesProcessed) {
            System.err.println("Termination byte(s) found at " + filePointer + "! Following content will be ignored!");
            try {
                file.skipBytes(bytesToRead - bytesProcessed);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    public boolean hasUnsynchronisation() {
        return unsynchronisation;
    }

    public void setUnsynchronisation(boolean unsynchronisation) {
        if (unsynchronisation) {
            System.err.println("Unsynchronisation is not supported yet!");
        }
        this.unsynchronisation = unsynchronisation;
    }

    public boolean hasExtendedHeader() {
        return extendedHeader;
    }

    public void setExtendedHeader(boolean extendedHeader) {
        if (extendedHeader) {
            System.err.println("Extended header is not supported yet!");
        }
        this.extendedHeader = extendedHeader;
    }

    public boolean hasExperimentalIndicator() {
        return experimentalIndicator;
    }

    public void setExperimentalIndicator(boolean experimentalIndicator) {
        if (experimentalIndicator) {
            System.err.println("Experimental indicator is not supported yet!");
        }
        this.experimentalIndicator = experimentalIndicator;
    }
}
