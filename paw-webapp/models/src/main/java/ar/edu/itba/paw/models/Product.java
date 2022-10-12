package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_id_seq")
    @SequenceGenerator(name = "products_id_seq", sequenceName = "products_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="sellers_id", nullable=false)
    private long sellerId;

    @Column(nullable = false)
    private long categoryId;

    @Column(unique = true, nullable = false, length=255)
    private String name;

    @Column(length=1023)
    private String description;

    @Column(nullable=false)
    private int stock;
    @Column(nullable=false)
    private Integer price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="tags_to_products",
            joinColumns = @JoinColumn(name="products_id"),
            inverseJoinColumns = @JoinColumn(name="ecotag_id") //TODO: Así funciona para usar un field de enum?
    )
    private List<Ecotag> tagList = new ArrayList<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="images_id")
    private long imageId;

    Product(){}

    public Product(Long id, long sellerId, long categoryId, String name, String description, int stock,
                   Integer price, long imageId) {
        this.id = id;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.imageId = imageId;
    }

    public Product(long sellerId, long categoryId, String name, String description, int stock,
                   Integer price, long imageId) {
        this(null, sellerId, categoryId, name, description, stock, price, imageId);
    }

    public long getProductId() {
        return id;
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

    public void setProductId(long id) {
        this.id = id;
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
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
