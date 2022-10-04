package ar.edu.itba.paw.models;

public enum Category {

    HYGIENE(1, "higiene"),
    HEALTH(2, "salud"),
    SKINCARE(3, "cuidadopiel"),
    FOOD(4, "alimento"),
    CLOTHING(5, "indumentaria"),
    INTERIOR(6, "interiores"),
    OTHERS(7, "otros");

    private final long id;
    private final String name;


    Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Category getById(long id) {
        for(Category category : Category.values()) {
            if(category.getId() == id)
                return category;
        }
        return null;
    }

}
