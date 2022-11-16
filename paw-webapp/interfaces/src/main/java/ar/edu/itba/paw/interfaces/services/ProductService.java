package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product create(Seller seller, long categoryId, String name, String description, int stock,
                   Integer price, byte[] image);
    List<Product> findBySeller(long sellerId);
    List<Product> findBySeller(long sellerId, boolean ecotag);
    Pagination<Product> findBySeller(long sellerId, boolean ecotag, int page, int amount, boolean available);
    Optional<Product> getById(long productId);
    Optional<Product> getByName(String name);
    List<Product> getAvailable();
    List<Product> getAvailable(int limit);
    List<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId);
    Pagination<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice,
                               long areaId, boolean favorite, int page, int sort,
                               int direction, long userId);
    void deleteProduct(long productId);
    void attemptDelete(long productId);
    void attemptPause(long productId);
    void attemptRepublish(long productId);
    boolean checkForAvailableStock(Product p, int amount);
    boolean checkForOwnership(long prodId);
    void decreaseStock(long prodId, int amount);
    void updateProduct(long prodId, int amount, int price);
    void addStock(String prodName, int amount);
    String buildPath(String[] strings);
    Product createProduct(Integer stock, Integer price, long categoryId, String name,
                          String description, byte[] image, long[] ecotagIds);
    List<Product> getLandingProducts(User loggedUser, List<Order> ordersForUser,
                                     List<String> popularOrders);
    List<Product> getInteresting(Product product, int amount);
    List<Product> getInterestingForUser(List<Order> orders, int amount);
    boolean atLeastOneProduct();
}
