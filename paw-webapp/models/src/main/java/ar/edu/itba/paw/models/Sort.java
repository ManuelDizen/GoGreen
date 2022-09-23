package ar.edu.itba.paw.models;

public enum Sort {

    SORT_PRICE(0, "sortbyprice"),
    SORT_ALPHABETIC(1, "sortalphabetic");

    private final int id;
    private final String name;

    Sort(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Sort getById(int id) {
        for (Sort s : Sort.values()) {
            if(s.getId() == id)
                return s;
        }
        return null;
    }

}
