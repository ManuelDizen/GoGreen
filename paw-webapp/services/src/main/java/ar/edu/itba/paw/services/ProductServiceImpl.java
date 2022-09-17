package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ImageService imageService;

    @Autowired
    public ProductServiceImpl(final ProductDao productDao, final ImageService imageService){
        this.productDao = productDao;
        this.imageService = imageService;
    }

    @Override
    public Product create(long sellerId, long categoryId, String name, String description,
                          int stock, float price, byte[] image) {
        Image img = imageService.create(image);
        if(img == null){
            throw new IllegalArgumentException("Error en creación de imagen");
        }
        return productDao.create(sellerId, categoryId, name, description, stock, price, img.getId());
    }

    @Override
    public List<Product> findBySeller(long sellerId) {
        return productDao.findBySeller(sellerId);
    }

    @Override
    public Optional<List<Product>> getByMaxPrice(float price) {
        return productDao.getByMaxPrice(price);
    }

    @Override
    public Optional<List<Product>> getByCategory(long categoryId) {
        return productDao.getByCategory(categoryId);
    }

    @Override
    public Optional<Product> getById(long productId) {
        return productDao.getById(productId);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public List<Product> filter(String name, String category, float maxPrice) {
        return productDao.filter(name, category, maxPrice);
    }
    @Override
    public List<Product> getRecent(int amount){
        return productDao.getRecent(amount);
    }
}
