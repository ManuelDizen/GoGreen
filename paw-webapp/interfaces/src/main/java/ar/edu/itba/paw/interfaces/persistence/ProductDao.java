package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product create(long sellerId, long categoryId, String name, String description, int stock, float price);

    Optional<List<Product>> findBySeller(long sellerId);
    Optional<List<Product>> getByMaxPrice(float price);
    Optional<List<Product>> getByCategory(long categoryId);
    Optional<Product> getById(long productId);
    List<Product> getAll();

}
