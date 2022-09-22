package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ImageService imageService;
    private final SecurityService securityService;
    private final SellerService sellerService;
    private final UserService userService;

    @Autowired
    public ProductServiceImpl(final ProductDao productDao, final ImageService imageService, SecurityService securityService, SellerService sellerService, UserService userService){
        this.productDao = productDao;
        this.imageService = imageService;
        this.securityService = securityService;
        this.sellerService = sellerService;
        this.userService = userService;
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
    public List<Product> filter(String name, String category, List<Ecotag> tags, float maxPrice) {
        List<Long> ecotags = new ArrayList<>();
        for(Ecotag tag : tags) {
            ecotags.add(tag.getId());
        }

        return productDao.filter(name, category, ecotags, maxPrice);
    }

    @Override
    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public Boolean attemptDelete(long productId) {
        User user = securityService.getLoggedUser();
        if(user == null) throw new IllegalStateException();
        /* TODO: Exceptions!!! */
        Optional<Product> prodToDelete = getById(productId);
        if(prodToDelete.isPresent()){
            Product prod = prodToDelete.get();
            Optional<Seller> prodOwner = sellerService.findById(prod.getSellerId());
            if(!prodOwner.isPresent()) throw new IllegalStateException();
            Optional<User> userProdOwner = userService.findById(prodOwner.get().getUserId());
            if(!userProdOwner.isPresent()) throw new IllegalStateException();

            if(userProdOwner.get().getEmail().equals(user.getEmail())){
                deleteProduct(productId);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> getRecent(int amount){
        return productDao.getRecent(amount);
    }
}
