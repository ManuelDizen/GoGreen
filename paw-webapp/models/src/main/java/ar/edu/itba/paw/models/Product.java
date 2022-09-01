package ar.edu.itba.paw.models;

public class Product {

    private long productId;
    private long sellerId;
    private long categoryId;
    private String name;
    private String description;
    private int stock;
    private float price;

    public Product(long productId, long sellerId, long categoryId, String name, String description, int stock, float price) {
        this.productId = productId;
        this.sellerId = sellerId;
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

    public float getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
