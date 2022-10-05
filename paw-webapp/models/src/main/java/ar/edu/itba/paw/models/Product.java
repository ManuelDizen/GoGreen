package ar.edu.itba.paw.models;

import java.util.List;
import java.util.Objects;

public class Product {

    private long productId;
    private long sellerId;
    private long categoryId;
    private String name;
    private String description;
    private int stock;
    private Integer price;
    private List<Ecotag> tagList;

    private long imageId;

    public Product(long productId, long sellerId, long categoryId, String name, String description, int stock,
                   Integer price, long imageId) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.imageId = imageId;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public List<Ecotag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Ecotag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
