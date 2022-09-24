package ar.edu.itba.paw.models;

public enum Category {

//    private final long id;
//    private final String name;
//    private final long imageId;
//
//    public Category(long id, String name, long imageId) {
//        this.id = id;
//        this.name = name;
//        this.imageId = imageId;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public long getImageId() {
//        return imageId;
//    }

    HYGIENE(1, "higiene"),
    HEALTH(2, "salud"),
    SKINCARE(3, "cuidadopiel");

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
