package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    Product create(Seller seller, long categoryId, String name, String description, int stock,
                   Integer price, byte[] image);

    List<Product> findBySeller(long sellerId);
    Optional<Product> getById(long productId);
    Optional<Product> getByName(String name);
    //List<Product> getAll();
    List<Product> getAvailable();
    List<Product> getRecent(int amount);
    List<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId);

    void sortProducts(List<Product> productList, int sort, int direction);

    List<List<Product>> divideIntoPages(List<Product> list, int pageSize);

    List<List<Product>> exploreProcess(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId, int sort, int direction);

    void deleteProduct(long productId);
    Boolean attemptDelete(long productId);
    Boolean attemptUpdate(long productId, int amount);

    Boolean checkForAvailableStock(Product p, int amount);
    Boolean checkForOwnership(long prodId);
    void updateStock(long prodId, int amount);

    Boolean updateProduct(long prodId, int amount, int price);

    Boolean addStock(String prodName, int amount);
    Boolean addStock(long prodId, int amount);

    String buildPath(String[] strings);

    Product createProduct(Integer stock, Integer price, long categoryId, String name,
                          String description, byte[] image, long[] ecotagIds);

    void setTagList(List<Product> productList);

    List<Product> getProductPage(int page, List<List<Product>> productPages);

    List<Product> getInteresting(Product product);

    List<List<Product>> productsPerCategory();
    List<Product> getByCategory(Category c);

}
