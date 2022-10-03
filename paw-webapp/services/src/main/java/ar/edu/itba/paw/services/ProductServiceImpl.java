package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
    private final EcotagService ecotagService;

    @Autowired
    public ProductServiceImpl(final ProductDao productDao, final ImageService imageService, SecurityService securityService, SellerService sellerService, UserService userService, EcotagService ecotagService){
        this.productDao = productDao;
        this.imageService = imageService;
        this.securityService = securityService;
        this.sellerService = sellerService;
        this.userService = userService;
        this.ecotagService = ecotagService;
    }

    @Override
    public Product create(long sellerId, long categoryId, String name, String description,
                          int stock, Integer price, byte[] image) {
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
    public Optional<Product> getById(long productId) {
        return productDao.getById(productId);
    }

    @Override
    public Optional<Product> getByName(String name) {
        return productDao.getByName(name);
    }

//    @Override
//    public List<Product> getAll() {
//        return productDao.getAll();
//    }

    @Override
    public List<Product> getAvailable(){return productDao.getAvailable();}

    @Override
    public List<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice) {
        List<Long> ecotags = new ArrayList<>();
        for(Ecotag tag : tags) {
            ecotags.add(tag.getId());
        }
        return productDao.filter(name, category, ecotags, maxPrice);
    }
    @Override
    public void sortProducts(List<Product> productList, int sort, int direction) {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (sort == 2) {
                    if (direction == 0) {
                        return (int) (o1.getPrice() - o2.getPrice());
                    } else {
                        return (int) (o2.getPrice() - o1.getPrice());
                    }
                } else if (sort == 1) {
                    if (direction == 0) {
                        return o1.getName().compareTo(o2.getName());
                    } else {
                        return o2.getName().compareTo(o1.getName());
                    }
                } else if (sort == 0) {
                    if (direction == 0)
                        return (int) (o1.getProductId() - o2.getProductId());
                    else {
                        return (int) (o2.getProductId() - o1.getProductId());
                    }
                }
                return 0;
            }
        });
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
    public List<List<Product>> exploreProcess(String name, long category, List<Ecotag> tags, float maxPrice, int sort, int direction) {
        List<Product> productList = filter(name, category, tags, maxPrice);
        setTagList(productList);
        sortProducts(productList, sort, direction);
        return divideIntoPages(productList);
    }

    @Override
    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }

    // TODO: This method's logic could be integrated to attemptUpdate()
    @Override
    public Boolean attemptDelete(long productId) {
        if(checkForOwnership(productId)){
            deleteProduct(productId);
            return true;
        }
        return false;
    }

    @Override
    public Boolean attemptUpdate(long productId, int amount){
        if(checkForOwnership(productId)){
           return addStock(productId, amount);
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
        if(user == null) throw new UnauthorizedRoleException();
        Optional<Product> prodToDelete = getById(prodId);
        if(prodToDelete.isPresent()){
            Product prod = prodToDelete.get();
            Optional<Seller> prodOwner = sellerService.findById(prod.getSellerId());
            if(!prodOwner.isPresent()) throw new UserNotFoundException();
            Optional<User> userProdOwner = userService.findById(prodOwner.get().getUserId());
            if(!userProdOwner.isPresent()) throw new UserNotFoundException();

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
    public Boolean updateProduct(long prodId, int amount, int price) {
        Boolean isOwner = checkForOwnership(prodId);
        if(!isOwner) return false;
        productDao.updateStock(prodId, amount);
        productDao.updatePrice(prodId, price);
        return true;
    }

    @Override
    public Boolean addStock(String prodName, int amount) {
        System.out.println("Nombre de producto: " + prodName + " stock a aumentar: " + amount);
        Optional<Product> prod = getByName(prodName);
        if(!prod.isPresent()) return true;
        return productDao.addStock(prodName, (amount + prod.get().getStock()));
    }

    @Override
    public Boolean addStock(long prodId, int amount){
        Optional<Product> product = getById(prodId);
        if(!product.isPresent()) return false;
        return addStock(product.get().getName(), amount);
    }

    @Override
    public String buildPath(String[] strings) {
        StringBuilder str = new StringBuilder("");
        for(String s : strings) {
            str.append("&strings=").append(s);
        }
        return str.toString();
    }

    private void addIfNotPresent(List<Product> toReturn, List<Product> list, Product product) {
        for(Product prod : list) {
            if(prod.getProductId() != product.getProductId() && toReturn.size() < 3) {
                toReturn.add(prod);
            }
        }
    }

    @Override
    public List<Product> getInteresting(Product product) {
        List<Product> toReturn = new ArrayList<>();
        List<Product> bySellerAndCategory = findBySeller(product.getSellerId());
        for(Product prod : bySellerAndCategory) {
            if(prod.getCategoryId() == product.getCategoryId() && prod.getProductId() != product.getProductId() && toReturn.size() < 3) {
                toReturn.add(prod);
            }
        }
        if(toReturn.size() < 3) {
            List<Product> byCategory = filter("", product.getCategoryId(), new ArrayList<>(), -1);
            addIfNotPresent(toReturn, byCategory, product);

        }
        if(toReturn.size() < 3) {
            List<Product> bySeller = findBySeller(product.getSellerId());
            addIfNotPresent(toReturn, bySeller, product);
        }
        if(toReturn.size() < 3) {
            List<Product> sorted = getAvailable();
            sortProducts(sorted, Sort.SORT_CHRONOLOGIC.getId(), 1);
            addIfNotPresent(toReturn, sorted, product);
        }
        return toReturn;
    }

    public void setTagList(List<Product> productList) {
        for(Product product : productList) {
            product.setTagList(ecotagService.getTagFromProduct(product.getProductId()));
        }
    }

    public List<Product> getProductPage(int page, List<List<Product>> productPages) {
        if(productPages.size() != 0)
            return productPages.get(page-1);
        return new ArrayList<>();

    }

    @Override
    public List<Product> getRecent(int amount){

        List<Product> recent =  productDao.getRecent(amount);

        for(Product product : recent) {
            product.setTagList(ecotagService.getTagFromProduct(product.getProductId()));
        }
        return recent;
    }

    @Override
    public Product createProduct(Integer stock, Integer price, long categoryId, String name,
                                 String description, byte[] image, long[] ecotagIds){
        User user = securityService.getLoggedUser();
        if(user == null)  throw new UnauthorizedRoleException();
        Optional<Seller> seller = sellerService.findByUserId(user.getId());
        if(!seller.isPresent()) throw new UserNotFoundException();
        Product product = create(seller.get().getId(), categoryId, name, description, stock, price, image);
        for (long id : ecotagIds) {
            ecotagService.addTag(Ecotag.getById(id), product.getProductId());
        }
        return product;
    }
}
