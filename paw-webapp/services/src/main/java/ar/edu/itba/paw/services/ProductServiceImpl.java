package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
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

    @Transactional
    @Override
    public Product create(Seller seller, long categoryId, String name, String description,
                          int stock, Integer price, byte[] image) {
        Image img = null;
        if(image != null && image.length > 0){
            img = imageService.create(image);
        }
        else{
            Optional<Image> maybeImg = imageService.getById(0);
            if(maybeImg.isPresent()) img = maybeImg.get();
        }
        return productDao.create(seller, categoryId, name, description, stock, price, img);
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
    public List<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId) {
        List<Long> ecotags = new ArrayList<>();
        for(Ecotag tag : tags) {
            ecotags.add(tag.getId());
        }
        return productDao.filter(name, category, ecotags, maxPrice, areaId);
    }

    private int getSales(String productName) {
        return productDao.getSales(productName);
    }
    @Override
    public void sortProducts(List<Product> productList, int sort, int direction) {
        productList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (sort == 3) {
                    if (direction == 0) {
                        return (getSales(o1.getName())- getSales(o2.getName()));
                    } else {
                        return (getSales(o2.getName()) - getSales(o1.getName()));
                    }

                } else if (sort == 2) {
                    if (direction == 0) {
                        return (o1.getPrice() - o2.getPrice());
                    } else {
                        return (o2.getPrice() - o1.getPrice());
                    }
                } else if (sort == 1) {
                    if (direction == 0) {
                        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                    } else {
                        return o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase());
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
    public List<List<Product>> divideIntoPages(List<Product> list, int pageSize) {
        List<List<Product>> pageList = new ArrayList<>();

        int aux = 1;
        while(aux <= list.size()/pageSize) {
            pageList.add(list.subList((aux-1)*pageSize, aux*pageSize));
            aux++;
        }
        if(list.size() % pageSize != 0)
            pageList.add(list.subList((aux-1)*pageSize, list.size()));
        if(list.size() == 0) pageList.add(new ArrayList<>());
        return pageList;
    }

    @Override
    public List<List<Product>> exploreProcess(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId, int sort, int direction) {
        List<Product> productList = filter(name, category, tags, maxPrice, areaId);
        setTagList(productList);
        sortProducts(productList, sort, direction);
        return divideIntoPages(productList, 12);
    }

    @Transactional
    @Override
    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }

    @Transactional
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
            Optional<Seller> prodOwner = sellerService.findById(prod.getSeller().getId());
            if(!prodOwner.isPresent()) throw new UserNotFoundException();
            Optional<User> userProdOwner = userService.findById(prodOwner.get().getUser().getId());
            if(!userProdOwner.isPresent()) throw new UserNotFoundException();

            return userProdOwner.get().getEmail().equals(user.getEmail());
        }
        return false;
    }

    @Transactional
    @Override
    public void updateStock(long prodId, int amount) {
        Optional<Product> product = productDao.getById(prodId);
        if(!product.isPresent()) return;
        productDao.updateStock(prodId, (product.get().getStock()-amount));
    }

    @Transactional
    @Override
    public Boolean updateProduct(long prodId, int amount, int price) {
        Boolean isOwner = checkForOwnership(prodId);
        if(!isOwner) return false;
        productDao.updateStock(prodId, amount);
        productDao.updatePrice(prodId, price);
        return true;
    }

    @Transactional
    @Override
    public Boolean addStock(String prodName, int amount) {
        Optional<Product> prod = getByName(prodName);
        if(!prod.isPresent()) return true;
        return productDao.addStock(prodName, (amount + prod.get().getStock()));
    }

    @Transactional
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
            if(prod.getProductId() != product.getProductId() && !toReturn.contains(prod) && toReturn.size() < 3) {
                toReturn.add(prod);
            }
        }
    }

    @Override
    public List<Product> getInteresting(Product product, int amount) {
        List<Product> toReturn = new ArrayList<>();
        List<Product> bySellerAndCategory = findBySeller(product.getSeller().getId());
        for(Product prod : bySellerAndCategory) {
            if(prod.getCategoryId() == product.getCategoryId() && prod.getProductId() != product.getProductId() && toReturn.size() < 3) {
                toReturn.add(prod);
            }
        }
        if(toReturn.size() < amount) {
            List<Product> byCategory = filter("", product.getCategoryId(), new ArrayList<>(), -1, 0);
            addIfNotPresent(toReturn, byCategory, product);

        }
        if(toReturn.size() < amount) {
            List<Product> bySeller = findBySeller(product.getSeller().getId());
            addIfNotPresent(toReturn, bySeller, product);
        }
        if(toReturn.size() < amount) {
            List<Product> sorted = getAvailable();
            sortProducts(sorted, Sort.SORT_CHRONOLOGIC.getId(), 1);
            addIfNotPresent(toReturn, sorted, product);
        }
        setTagList(toReturn);
        return toReturn;
    }

    @Override
    public List<Product> getInterestingForUser(List<Order> orders, int amount) {
        List<Product> interesting = new ArrayList<>();
        int i=0;
        while(interesting.size() < amount) {
            for(Order order : orders) {
                Optional<Product> aux = getByName(order.getProductName());
                if(aux.isPresent()) {
                    Product product = aux.get();
                    Product candidate = getInteresting(product, 1).get(i);
                    if (!interesting.contains(candidate) && interesting.size() < amount)
                        interesting.add(candidate);
                }
            }
            i++;
        }
        return interesting;
    }


    @Override
    public List<List<Product>> productsPerCategory() {
        List<List<Product>> products = new ArrayList<>();
        for(Category c : Category.values()){
            List<Product> auxSet = getByCategory(c);
            products.add(auxSet);
        }
        return products;
    }

    @Override
    public List<Product> getByCategory(Category c){
        return productDao.getByCategory(c.getId());
    }

    public void setTagList(List<Product> productList) {
        for(Product product : productList) {
            product.setTagList(ecotagService.getTagsFromProduct(product.getProductId()));
        }
    }

    public List<Product> getProductPage(int page, List<List<Product>> productPages) {
        if(productPages.size() != 0)
            return productPages.get(page-1);
        return new ArrayList<>();

    }

    @Override
    public List<Product> getPopular(int amount) {

        List<Product> products = getAvailable();

        products.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return getSales(o2.getName()) - getSales(o1.getName());
            }
        });

        List<Product> popular = products.subList(0, amount);

        for(Product product : popular) {
            product.setTagList(ecotagService.getTagsFromProduct(product.getProductId()));
        }
        return popular;
    }

    @Transactional
    @Override
    public Product createProduct(Integer stock, Integer price, long categoryId, String name,
                                 String description, byte[] image, long[] ecotagIds){
        User user = securityService.getLoggedUser();
        if(user == null)  throw new UnauthorizedRoleException();
        Optional<Seller> seller = sellerService.findByUserId(user.getId());
        if(!seller.isPresent()) throw new UserNotFoundException();
        Product product = create(seller.get(), categoryId, name, description, stock, price, image);
        for (long id : ecotagIds) {
            ecotagService.addTag(Ecotag.getById(id), product.getProductId());
        }
        return product;
    }
}
