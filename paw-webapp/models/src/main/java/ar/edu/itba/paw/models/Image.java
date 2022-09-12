package ar.edu.itba.paw.models;

public class Image {
    private long id;
    private byte[] source;

    public Image(long id, byte[] source) {
        this.id = id;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getSource() {
        return source;
    }

    public void setSource(byte[] source) {
        this.source = source;
    }
}
