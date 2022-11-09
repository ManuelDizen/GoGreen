package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product create(Seller seller, long categoryId, String name, String description, int stock,
                   Integer price, byte[] image);

    List<Product> findBySeller(long sellerId);
    List<Product> findBySeller(long sellerId, boolean ecotag);
    Optional<Product> getById(long productId);
    Optional<Product> getByName(String name);
    //List<Product> getAll();
    List<Product> getAvailable();
    List<Product> getAvailable(int limit);
    List<Product> getPopular(int amount);
    List<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId);

    void onlyFavorites(List<Product> productList, long userId);
    void sortProducts(List<Product> productList, int sort, int direction);

    <T> List<List<T>> divideIntoPages(List<T> list, int pageSize);

    List<Product> exploreProcess(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId, int sort, int direction);

    void deleteProduct(long productId);
    void attemptDelete(long productId);
    void attemptPause(long productId);
    void attemptRepublish(long productId);

    boolean checkForAvailableStock(Product p, int amount);
    boolean checkForOwnership(long prodId);
    void decreaseStock(long prodId, int amount);

    void updateProduct(long prodId, int amount, int price);

    void addStock(String prodName, int amount);
    void addStock(long prodId, int amount);

    String buildPath(String[] strings);

    Product createProduct(Integer stock, Integer price, long categoryId, String name,
                          String description, byte[] image, long[] ecotagIds);

    void setTagList(List<Product> productList);

    List<Product> getProductPage(int page, List<List<Product>> productPages);

    List<Product> getInteresting(Product product, int amount);

    List<Product> getInterestingForUser(List<Order> orders, int amount);

    List<List<Product>> productsPerCategory();
    List<Product> getByCategory(Category c);

    List<Product> getLandingProducts(User loggedUser, List<Order> ordersForUser);
}
