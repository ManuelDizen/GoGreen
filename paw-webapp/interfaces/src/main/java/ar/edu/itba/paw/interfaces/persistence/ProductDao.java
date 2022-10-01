package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product create(long sellerId, long categoryId, String name, String description, int stock,
                   Integer price, long imageId);

    List<Product> findBySeller(long sellerId);

    Optional<Product> getById(long productId);
    Optional<Product> getByName(String name);
    List<Product> getAll();
    List<Product> getAvailable();

    List<Product> filter(String name, long category, List<Long> tags, Integer maxPrice);

    List<Product> getRecent(int amount);

    void deleteProduct(long productId);
    void updateStock(long productId, int amount);
    void updatePrice(long productId, int price);

    Boolean addStock(String name, int amount);

}
