package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(long sellerId, long categoryId, String name, String description, int stock,
                   float price, long imageId);

    Optional<List<Product>> findBySeller(long sellerId);
    Optional<List<Product>> getByMaxPrice(float price);
    Optional<List<Product>> getByCategory(long categoryId);
    Optional<Product> getById(long productId);
    List<Product> getAll();

    List<Product> filter(String name, String category, float maxPrice);
}
