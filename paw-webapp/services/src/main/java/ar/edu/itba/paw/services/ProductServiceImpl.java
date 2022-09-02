package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(final ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public Product create(long sellerId, long categoryId, String name, String description, int stock, float price) {
        return productDao.create(sellerId, categoryId, name, description, stock, price);
    }

    @Override
    public Optional<List<Product>> findBySeller(long sellerId) {
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
    public Optional<Seller> getProductSeller(long sellerId) {
        return productDao.getProductSeller(sellerId);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }
}
