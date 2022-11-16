package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product create(Seller seller, long categoryId, String name, String description, int stock,
                   Integer price, Image image);
    List<Product> findBySeller(long sellerId);
    List<Product> findBySellerNoEcotag(long sellerId);
    Optional<Product> getById(long productId);
    Optional<Product> getByName(String name);
    List<Product> getAvailable();
    List<Product> getAvailable(int limit);
    List<Product> filter(String name, long category, List<Long> tags, Integer maxPrice, long areaId);
    Pagination<Product> filter(String name, long category, List<Long> tags, Integer maxPrice,
                               long areaId, boolean favorite, int page, int sort,
                               int direction, long userId);
    boolean atLeastOneProduct();
    Pagination<Product> findBySeller(long sellerId, int page, int amount, boolean ecotag, boolean available);

}
