package de.moralis.models;

public class Frame {

    private FrameId id;
    private int size = 0;
    private boolean tagAlterPreservation = false;
    private boolean fileAlterPreservation = false;
    private boolean readOnly = false;
    private boolean compression = false;
    private boolean encryption = false;
    private boolean groupingIdentity = false;
    private CharSet encoding = CharSet.ISO_8859_1;
    private String content = "";
    private String xxxDescription = "";

    @Override
    public String toString() {
        return "Frame{" +
                "id=" + id +
                ", size=" + size +
                ", tagAlterPreservation=" + tagAlterPreservation +
                ", fileAlterPreservation=" + fileAlterPreservation +
                ", readOnly=" + readOnly +
                ", compression=" + compression +
                ", encryption=" + encryption +
                ", groupingIdentity=" + groupingIdentity +
                ", encoding='" + encoding + '\'' +
                ", content='" + content + '\'' +
                ", xxxDescription='" + xxxDescription + '\'' +
                "}\n";
    }

    public FrameId getId() {
        return id;
    }

    public void setId(FrameId id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isTagAlterPreservation() {
        return tagAlterPreservation;
    }

    public void setTagAlterPreservation(boolean tagAlterPreservation) {
        if (tagAlterPreservation) {
            System.err.println("Tag alter preservation is not supported yet!");
        }
        this.tagAlterPreservation = tagAlterPreservation;
    }

    public boolean isFileAlterPreservation() {
        return fileAlterPreservation;
    }

    public void setFileAlterPreservation(boolean fileAlterPreservation) {
        if (fileAlterPreservation) {
            System.err.println("File alter preservation is not supported yet!");
        }
        this.fileAlterPreservation = fileAlterPreservation;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        if (readOnly) {
            System.err.println("Read only is not supported yet!");
        }
        this.readOnly = readOnly;
    }

    public boolean isCompression() {
        return compression;
    }

    public void setCompression(boolean compression) {
        if (compression) {
            System.err.println("Compression is not supported yet!");
        }
        this.compression = compression;
    }

    public boolean isEncryption() {
        return encryption;
    }

    public void setEncryption(boolean encryption) {
        if (encryption) {
            System.err.println("Encryption is not supported yet!");
        }
        this.encryption = encryption;
    }

    public boolean isGroupingIdentity() {
        return groupingIdentity;
    }

    public void setGroupingIdentity(boolean groupingIdentity) {
        if (groupingIdentity) {
            System.err.println("Grouping identity is not supported yet!");
        }
        this.groupingIdentity = groupingIdentity;
    }

    public CharSet getEncoding() {
        return encoding;
    }

    public void setEncoding(CharSet encoding) {
        this.encoding = encoding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getXxxDescription() {
        return xxxDescription;
    }

    public void setXxxDescription(String xxxDescription) {
        this.xxxDescription = xxxDescription;
    }
}
