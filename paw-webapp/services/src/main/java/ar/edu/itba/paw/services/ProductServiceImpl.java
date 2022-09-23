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

    private final static int PAGE_SIZE = 6;
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
        long imgId = 0;
        if(image != null && image.length > 0){
            imgId = imageService.create(image).getId();
        }
        return productDao.create(sellerId, categoryId, name, description, stock, price, imgId);
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
    public Optional<Product> getByName(String name) {
        return productDao.getByName(name);
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public List<Product> getAvailable(){return productDao.getAvailable();}

    @Override
    public List<Product> filter(String name, long category, List<Ecotag> tags, float maxPrice) {
        List<Long> ecotags = new ArrayList<>();
        for(Ecotag tag : tags) {
            ecotags.add(tag.getId());
        }

        return productDao.filter(name, category, ecotags, maxPrice);
    }

    @Override
    public List<List<Product>> divideIntoPages(List<Product> list) {
        List<List<Product>> pageList = new ArrayList<>();

        int aux = 1;
        while(aux <= list.size()/PAGE_SIZE) {
            pageList.add(list.subList((aux-1)*PAGE_SIZE, aux*PAGE_SIZE));
            aux++;
        }
        if(list.size() % PAGE_SIZE != 0)
            pageList.add(list.subList((aux-1)*PAGE_SIZE, list.size()));
        if(list.size() == 0) pageList.add(new ArrayList<>());
        return pageList;
    }


    @Override
    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public Boolean attemptDelete(long productId) {
        if(checkForOwnership(productId)){
            deleteProduct(productId);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkForAvailableStock(Product p, int amount) {
        return p.getStock() >= amount;
    }

    @Override
    public Boolean checkForOwnership(long prodId) {
        User user = securityService.getLoggedUser();
        if(user == null) throw new IllegalStateException();
        /* TODO: Exceptions!!! */
        Optional<Product> prodToDelete = getById(prodId);
        if(prodToDelete.isPresent()){
            Product prod = prodToDelete.get();
            Optional<Seller> prodOwner = sellerService.findById(prod.getSellerId());
            if(!prodOwner.isPresent()) throw new IllegalStateException();
            Optional<User> userProdOwner = userService.findById(prodOwner.get().getUserId());
            if(!userProdOwner.isPresent()) throw new IllegalStateException();

            return userProdOwner.get().getEmail().equals(user.getEmail());
        }
        return false;
    }

    @Override
    public void updateStock(long prodId, int amount) {
        Optional<Product> product = productDao.getById(prodId);
        if(!product.isPresent()) return;
        productDao.updateStock(prodId, (product.get().getStock()-amount));
    }

    @Override
    public Boolean addStock(String prodName, int amount) {
        System.out.println("Nombre de producto: " + prodName + " stock a aumentar: " + amount);
        Optional<Product> prod = getByName(prodName);
        if(!prod.isPresent()) throw new IllegalStateException();
        return productDao.addStock(prodName, (amount + prod.get().getStock()));
    }

    @Override
    public List<Product> getRecent(int amount){
        return productDao.getRecent(amount);
    }
}
