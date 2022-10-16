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

    @ManyToOne
    @JoinColumn(name="sellerid", nullable = false)
    private Seller seller;

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

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="tags_to_products",
            joinColumns = @JoinColumn(name="products_id"),
            inverseJoinColumns = @JoinColumn(name="ecotag_id") //TODO: Así funciona para usar un field de enum?
    )*/

    @ElementCollection(targetClass = Ecotag.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "tags_to_products", joinColumns = @JoinColumn(name = "productid"))
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ecotag_id")
    private List<Ecotag> tagList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="imageid", nullable = false)
    private Image image;

    public void addEcotag(Ecotag tag) {
        tagList.add(tag);
    }
    Product(){}

    public Product(Long id, Seller seller, long categoryId, String name, String description, int stock,
                   Integer price, Image image) {
        this.id = id;
        this.seller = seller;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.image = image;
    }

    public Product(Seller seller, long categoryId, String name, String description, int stock,
                   Integer price, Image image) {
        this(null, seller, categoryId, name, description, stock, price, image);
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

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
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
