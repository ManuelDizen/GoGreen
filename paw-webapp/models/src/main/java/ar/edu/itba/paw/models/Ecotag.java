package ar.edu.itba.paw.models;

public enum Ecotag {

    ECOTAG_TEST("testTag", 0, "test", "blue", "ecotagTest"),
    ECOTAG_RECYCLE("recycleTag", 1, "recycling", "blue", "ecotagRecycle"),
    ECOTAG_FOREST("forestTag", 2, "forest", "green", "ecotagForest"),
    ECOTAG_ENERGY("energyTag", 3, "wind_power", "grey", "ecotagEnergy"),
    ECOTAG_ANIMALS("animalTag", 4, "pets", "brown", "ecotagAnimals"),
    ECOTAG_TRANSPORT("transportTag", 5, "local_shipping", "yellow darken-2", "ecotagTransport");

    private final String tag;
    private final long id;
    private final String icon;
    private final String color;
    private final String path;

    Ecotag(String tag, long id, String icon, String color, String path) {
        this.tag = tag;
        this.id = id;
        this.icon = icon;
        this.color = color;
        this.path = path;
    }

    public String getTag() {
        return tag;
    }

    public long getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getColor() { return color; }

    public String getPath() { return path; }

    public static Ecotag getById(long id) {
        for(Ecotag tag : Ecotag.values()) {
            if(tag.getId() == id)
                return tag;
        }
        return null;
    }


}