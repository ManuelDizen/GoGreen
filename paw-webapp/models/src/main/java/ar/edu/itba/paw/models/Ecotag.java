package ar.edu.itba.paw.models;

public enum Ecotag {
    ECOTAG_RECYCLE("Productos reciclados", 1, "recycling", "blue"),
    ECOTAG_FOREST("Sin deforestación", 2, "forest", "green"),
    ECOTAG_ENERGY("Energías renovables", 3, "wind_power", "grey");

    private final String tag;
    private final long id;
    private final String icon;
    private final String color;

    Ecotag(String tag, long id, String icon, String color) {
        this.tag = tag;
        this.id = id;
        this.icon = icon;
        this.color = color;
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

    public static Ecotag getById(long id) {
        for(Ecotag tag : Ecotag.values()) {
            if(tag.getId() == id)
                return tag;
        }
        return null;
    }


}