package ar.edu.itba.paw.models;

public class Category {

    private final long id;
    private final String name;
    private final long imageId;

    public Category(long id, String name, long imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getImageId() {
        return imageId;
    }

    //    HYGIENE(1, "Higiene", "category.hygiene"),
//    HEALTH(2, "Salud", "category.health"),
//    SKINCARE(3, "Cuidado de la piel", "category.skincare");
//
//    private final long id;
//    private final String name;
//    private final String path;
//
//    Category(long id, String name, String path) {
//        this.id = id;
//        this.name = name;
//        this.path = path;
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
//    public String getPath() { return path; }
//
//    public static Category getById(long id) {
//        for(Category category : Category.values()) {
//            if(category.getId() == id)
//                return category;
//        }
//        return null;
//    }

}
