package ar.edu.itba.paw.models;

public enum Ecotag {
    ECOTAG1("Ecotag1", 1),
    ECOTAG2("Ecotag2", 2),
    ECOTAG3("Ecotag3", 3);

    private final String tag;
    private final long id;

    Ecotag(String tag, long id) {
        this.tag = tag;
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public long getId() {
        return id;
    }

    public static Ecotag getById(long id) {
        for(Ecotag tag : Ecotag.values()) {
            if(tag.getId() == id)
                return tag;
        }
        return null;
    }


}