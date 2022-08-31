package ar.edu.itba.paw.models;

public class Product {

    private String name;
    private String description;
    private int stock;
    private int price;

    public Product(String name, String description, int stock, int price) {
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }

    public int getPrice() {
        return price;
    }
}
