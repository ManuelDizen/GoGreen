package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(final ProductDao productDao){
        this.productDao = productDao;
    }

    @Override
    public Product addProduct(String name, String description, int stock, int price) {
        return productDao.create(name, description, stock, price);
    }

    @Override
    public List<Product> getProductList() {
        return productDao.getAll();
    }
}
