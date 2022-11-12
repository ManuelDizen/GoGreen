package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final static int N_LANDING = 4;
    private final static int ASCENDING = 0;
    private final static int DESCENDING = 1;

    private final ProductDao productDao;
    private final ImageService imageService;
    private final SellerService sellerService;
    private final UserService userService;
    private final EcotagService ecotagService;
    private final FavoriteService favoriteService;


    @Autowired
    public ProductServiceImpl(final ProductDao productDao, final ImageService imageService,
                              SellerService sellerService,
                              UserService userService, EcotagService ecotagService,
                              FavoriteService favoriteService){
        this.productDao = productDao;
        this.imageService = imageService;
        this.sellerService = sellerService;
        this.userService = userService;
        this.ecotagService = ecotagService;
        this.favoriteService = favoriteService;
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

    private List<Product> findBySellerNoEcotag(long sellerId) {
        return productDao.findBySellerNoEcotag(sellerId);
    }

    @Override
    public List<Product> findBySeller(long sellerId, boolean ecotag) {
        if(ecotag){
            return findBySeller(sellerId);
        }
        else{
            return findBySellerNoEcotag(sellerId);
        }
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
    public List<Product> getAvailable(int limit){return productDao.getAvailable(limit);}

    @Override
    public List<Product> filter(String name, long category, List<Ecotag> tags, Integer maxPrice, long areaId) {
        List<Long> ecotags = new ArrayList<>();
        for(Ecotag tag : tags) {
            ecotags.add(tag.getId());
        }
        return productDao.filter(parseString(name), category, ecotags, maxPrice, areaId);
    }

    private int getSales(String productName) {
        return productDao.getSales(productName);
    }
    @Override
    public void sortProducts(List<Product> productList, int sort, int direction) {
        productList.sort((o1, o2) -> {
            if (sort == Sort.SORT_POPULAR.getId()) {
                if (direction == ASCENDING) {
                    return (getSales(o1.getName())- getSales(o2.getName()));
                } else {
                    return (getSales(o2.getName()) - getSales(o1.getName()));
                }

            } else if (sort == Sort.SORT_PRICE.getId()) {
                if (direction == ASCENDING) {
                    return (o1.getPrice() - o2.getPrice());
                } else {
                    return (o2.getPrice() - o1.getPrice());
                }
            } else if (sort == Sort.SORT_ALPHABETIC.getId()) {
                if (direction == ASCENDING) {
                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                } else {
                    return o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase());
                }
            } else if (sort == Sort.SORT_CHRONOLOGIC.getId()) {
                if (direction == ASCENDING)
                    return (int) (o1.getProductId() - o2.getProductId());
                else {
                    return (int) (o2.getProductId() - o1.getProductId());
                }
            }
            return 0;
        });
    }

    @Override
    public <T> List<List<T>> divideIntoPages(List<T> list, int pageSize) {
        List<List<T>> pageList = new ArrayList<>();

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
    public List<Product> exploreProcess(String name, long category, List<Ecotag> tags,
                                        Integer maxPrice, long areaId, int sort, int direction,
                                        boolean favorite) {
        List<Product> productList = filter(name, category, tags, maxPrice, areaId);
        setTagList(productList);
        sortProducts(productList, sort, direction);
        if(favorite) {
            List<Seller> sellers = favoriteService.getFavoriteSellersByUserId();
            productList.removeIf(product -> !sellers.contains(product.getSeller()));
        }
        return productList;
    }

    @Transactional
    @Override
    public void deleteProduct(long productId) {
        /*
        Opción 1: La que estaba antes, una baja física.
         */
        // productDao.deleteProduct(productId);

        /*
        Opción 2: Baja lógica, hay que discutir si creamos una manera de "recuperarlos"
         */
        Optional<Product> product = getById(productId);
        if(!product.isPresent()) throw new ProductNotFoundException();
        product.get().setStatus(ProductStatus.DELETED);
    }

    @Transactional
    @Override
    public void attemptDelete(long productId) {
        if(checkForOwnership(productId)){
            deleteProduct(productId);
        }
    }

    @Transactional
    @Override
    public void attemptPause(long productId) {
        if(checkForOwnership(productId)){
            Optional<Product> prod = getById(productId);
            if(!prod.isPresent()) throw new ProductNotFoundException();
            Product product = prod.get();
            if(product.getStatus().getId() == ProductStatus.AVAILABLE.getId()){
                prod.get().setStatus(ProductStatus.PAUSED);
            }
            // Aclaración: Si el status está out of stock, no tiene sentido pausar.
            // Tecnicamente, "ya está pausado". Si esta deleted, ni siquiera debería ser alcanzable.
            // Por lo que el único cambio posible es si está AVAILABLE, pasarlo a PAUSED.
            // (Si esta paused obviamente no es necesario modificar nada)
        }
    }

    @Transactional
    @Override
    public void attemptRepublish(long productId) {
        if(checkForOwnership(productId)){
            Optional<Product> prod = getById(productId);
            if(!prod.isPresent()) throw new ProductNotFoundException();
            Product product = prod.get();
            if(product.getStatus().getId() == ProductStatus.PAUSED.getId()){
                product.setStatus(ProductStatus.AVAILABLE);
            }
        }
    }

    public String parseString(String str){
        char[] sqlSpecialChars = {'%', '_'};
        char[] charArray = str.toCharArray();
        StringBuilder out = new StringBuilder();
        boolean flag;
        for(char c : charArray) {
            flag = false;
            for (char s : sqlSpecialChars) {
                if (c == s) {
                    out.append("\\").append(c);
                    flag = true;
                    break;
                }
            }
            if(!flag) out.append(c);
        }
        return out.toString();
    }

    @Override
    public boolean checkForAvailableStock(Product p, int amount) {
        return p.getStock() >= amount;
    }

    @Override
    public boolean checkForOwnership(long prodId) {
        User user = userService.getLoggedUser();
        if(user == null) throw new UnauthorizedRoleException();
        Optional<Product> prodToDelete = getById(prodId);
        if(!prodToDelete.isPresent()) throw new ProductNotFoundException();
        else{
            Product prod = prodToDelete.get();
            Optional<Seller> prodOwner = sellerService.findById(prod.getSeller().getId());
            if(!prodOwner.isPresent()) throw new UserNotFoundException();
            Optional<User> userProdOwner = userService.findById(prodOwner.get().getUser().getId());
            if(!userProdOwner.isPresent()) throw new UserNotFoundException();

            return userProdOwner.get().getEmail().equals(user.getEmail());
        }
    }

    @Transactional
    @Override
    public void decreaseStock(long prodId, int amount) {
        Optional<Product> product = productDao.getById(prodId);
        if(!product.isPresent()) return;
        Product prod = product.get();
        prod.setStock(prod.getStock() - amount);
        //productDao.updateStock(prodId, (prod.getStock()-amount));
        if(prod.getStock() == 0) prod.setStatus(ProductStatus.OUTOFSTOCK);
    }

    @Transactional
    @Override
    public void updateProduct(long prodId, int amount, int price) {
        boolean isOwner = checkForOwnership(prodId);
        if(!isOwner) throw new ForbiddenActionException();
        Optional<Product> product = productDao.getById(prodId);
        if(!product.isPresent()) throw new ProductNotFoundException();
        Product prod = product.get();
        if(prod.getStatus().getId() == ProductStatus.OUTOFSTOCK.getId() && amount > 0)
            prod.setStatus(ProductStatus.AVAILABLE);
        prod.setStock(amount);
        if(prod.getStock() == 0) prod.setStatus(ProductStatus.OUTOFSTOCK);
        prod.setPrice(price);
    }

    @Transactional
    @Override
    public void addStock(String prodName, int amount) {
        Optional<Product> prod = getByName(prodName);
        if(!prod.isPresent()) return;
        Product product = prod.get();
        if(product.getStatus() == ProductStatus.OUTOFSTOCK){
            product.setStatus(ProductStatus.AVAILABLE);
        }
        product.setStock(amount + product.getStock());
    }

    @Transactional
    @Override
    public void addStock(long prodId, int amount){
        Optional<Product> product = getById(prodId);
        if(!product.isPresent()) return;
        addStock(product.get().getName(), amount);
    }

    @Override
    public String buildPath(String[] strings) {
        StringBuilder str = new StringBuilder();
        for(String s : strings) {
            str.append("&strings=").append(s);
        }
        return str.toString();
    }

    private void addIfNotPresent(List<Product> toReturn, List<Product> list, int amount, Product product) {
        long availableId = ProductStatus.AVAILABLE.getId();
        for(Product prod : list) {
            if(prod.getStatus().getId() == availableId &&
                    prod.getProductId() != product.getProductId() &&
                    !toReturn.contains(prod) && toReturn.size() < amount) {
                toReturn.add(prod);
            }
        }
    }

    @Override
    public List<Product> getInteresting(Product product, int amount) {
        List<Product> toReturn = new ArrayList<>();
        List<Product> bySellerAndCategory = findBySeller(product.getSeller().getId(), true);
        long availableId = ProductStatus.AVAILABLE.getId();
        for(Product prod : bySellerAndCategory) {
            if(prod.getStatus().getId() == availableId &&
                    prod.getCategoryId() == product.getCategoryId() &&
                    prod.getProductId() != product.getProductId() && toReturn.size() < amount) {
                toReturn.add(prod);
            }
        }
        if(toReturn.size() < amount) {
            List<Product> byCategory = filter("", product.getCategoryId(), new ArrayList<>(), -1, 0);
            addIfNotPresent(toReturn, byCategory, amount, product);

        }
        if(toReturn.size() < amount) {
            List<Product> bySeller = findBySeller(product.getSeller().getId(), true);
            addIfNotPresent(toReturn, bySeller, amount, product);
        }
        if(toReturn.size() < amount) {
            List<Product> sorted = getAvailable();
            sortProducts(sorted, Sort.SORT_CHRONOLOGIC.getId(), DESCENDING);
            addIfNotPresent(toReturn, sorted, amount, product);
        }
        setTagList(toReturn);
        return toReturn;
    }

    @Override
    public List<Product> getInterestingForUser(List<Order> orders, int amount) {
        return getPopular(N_LANDING);
        // TODO: Rebuild this whole method
        /*List<Product> interesting = new ArrayList<>();
        int i=0;
        while(interesting.size() < amount) {
            for(Order order : orders) {
                Optional<Product> aux = getByName(order.getProductName());
                if(aux.isPresent()) {
                    Product product = aux.get();
                    Product candidate = getInteresting(product, i+1).get(i);
                    if (!interesting.contains(candidate) && interesting.size() < amount)
                        interesting.add(candidate);
                }
            }
            i++;
        }
        return interesting;*/
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

    @Override
    public List<Product> getLandingProducts(User loggedUser, List<Order> ordersForUser) {
        List<Product> products;
        if(loggedUser == null) {
            products = getPopular(N_LANDING);
        }
        else {
            if(ordersForUser.isEmpty()){
                products = getPopular(N_LANDING);
            } else {
                //TODO: Rebuild getInterestingForUser method
                products = getInterestingForUser(ordersForUser, N_LANDING);
                //products = getPopular(4);
            }
        }
        return products;
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

        List<Product> products = getAvailable(4);

        products.sort((o1, o2) -> getSales(o2.getName()) - getSales(o1.getName()));

        //List<Product> popular = products.size() < amount? products:products.subList(0, amount);

        /*for(Product product : popular) {
            product.setTagList(ecotagService.getTagsFromProduct(product.getProductId()));
        }*/
        return products;
    }

    @Transactional
    @Override
    public Product createProduct(Integer stock, Integer price, long categoryId, String name,
                                 String description, byte[] image, long[] ecotagIds){
        User user = userService.getLoggedUser();
        if(user == null)  throw new UnauthorizedRoleException();
        Optional<Seller> seller = sellerService.findByUserId(user.getId());
        if(!seller.isPresent()) throw new UserNotFoundException();
        Product product = create(seller.get(), categoryId, name, description, stock, price, image);
        if(product == null) throw new ProductCreationException();
        for (long id : ecotagIds) {
            ecotagService.addTag(Ecotag.getById(id), product.getProductId());
        }
        return product;
    }

    @Override
    public boolean atLeastOneProduct(){return productDao.atLeastOneProduct();}
}
