package ar.edu.itba.paw.models;

public enum Sort {

    SORT_PRICE(0, "sortbyprice"),
    SORT_ALPHABETIC(1, "sortalphabetic");

    private final long id;
    private final String name;

    Sort(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
