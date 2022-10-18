package ar.edu.itba.paw.models;

public enum Ecotag {

    ECOTAG_RECYCLE("recycleTag", 0, "recycling", "blue", "ecotagRecycle"),
    ECOTAG_FOREST("forestTag", 1, "forest", "green", "ecotagForest"),
    ECOTAG_ENERGY("energyTag", 2, "wind_power", "grey", "ecotagEnergy"),
    ECOTAG_ANIMALS("animalTag", 3, "pets", "brown", "ecotagAnimals"),
    ECOTAG_TRANSPORT("transportTag", 4, "local_shipping", "yellow darken-2", "ecotagTransport");

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