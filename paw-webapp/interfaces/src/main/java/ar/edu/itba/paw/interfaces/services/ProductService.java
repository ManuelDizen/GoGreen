package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(long sellerId, long categoryId, String name, String description, int stock,
                   float price, byte[] image);

    List<Product> findBySeller(long sellerId);
    Optional<List<Product>> getByMaxPrice(float price);
    Optional<List<Product>> getByCategory(long categoryId);
    Optional<Product> getById(long productId);
    Optional<Product> getByName(String name);
    List<Product> getAll();
    List<Product> getAvailable();


    List<Product> getRecent(int amount);
    List<Product> filter(String name, long category, List<Ecotag> tags, float maxPrice);

    void sortProducts(List<Product> productList, int sort, int direction);

    List<List<Product>> divideIntoPages(List<Product> list);

    void deleteProduct(long productId);
    Boolean attemptDelete(long productId);
    Boolean attemptUpdate(long productId, int amount);

    Boolean checkForAvailableStock(Product p, int amount);
    Boolean checkForOwnership(long prodId);
    void updateStock(long prodId, int amount);

    Boolean addStock(String prodName, int amount);
    Boolean addStock(long prodId, int amount);

    String buildPath(String[] strings);
}
