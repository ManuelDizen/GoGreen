package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductHibernateDao implements ProductDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Product create(Seller seller, long categoryId, String name, String description, int stock, Integer price, Image image) {
        final Product prod = new Product(seller, categoryId, name, description, stock, price, image);
        em.persist(prod);
        return prod;
    }

    @Override
    public List<Product> findBySeller(long sellerId) {
        final TypedQuery<Product> query = em.createQuery("FROM Product AS p WHERE seller.id = :sellerId",
                Product.class);
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }

    @Override
    public Optional<Product> getById(long productId) {
        return Optional.ofNullable(em.find(Product.class, productId));
    }

    @Override
    public Optional<Product> getByName(String name) {
        final TypedQuery<Product> query = em.createQuery("FROM Product AS p WHERE p.name = :name",
                Product.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Product> getAvailable() {
        final TypedQuery<Product> query = em.createQuery("FROM Product AS p WHERE p.stock > 0",
                Product.class);
        return query.getResultList();
    }

    @Override
    public List<Product> filter(String name, long category, List<Long> tags, Integer maxPrice, long areaId) {

//        StringBuilder query = new StringBuilder("FROM Product AS p WHERE ");
//        List<Object> args = new ArrayList<>();
//        if(name != null){
//            query.append("LOWER(p.name) like ? ");
//            args.add('%' + name.toLowerCase() + '%');
//        }
//        if(category != 0){
//            query.append("AND p.categoryId = ? ");
//            args.add(category);
//        }
////        if(tags.size() != 0){
////            for(long tag : tags) {
////                query.append("AND id IN (SELECT productId from tags_to_products WHERE tag = ?)");
////                args.add(tag);
////            }
////        }
//        if(maxPrice != -1.0){
//            query.append("AND p.price <= ?");
//            args.add(maxPrice);
//        }
////        if(areaId > 0){
////            query.append("AND sellerId IN (SELECT id FROM sellers WHERE areaId = ?)");
////            args.add(areaId);
////        }
//        query.append("AND p.stock <> 0 ORDER BY p.id DESC");
//
//        final TypedQuery<Product> finalQuery = em.createQuery(query.toString(),
//                Product.class);
//        return finalQuery.getResultList();
        return getAvailable();
    }

    @Override
    public List<Product> getRecent(int amount) {
        List<Product> products = getAvailable();
        if (products.size() < amount) return products;
        return products.subList(0, amount);
    }

    @Override
    public void deleteProduct(long productId) {
        em.remove(em.find(Product.class, productId));
    }

    @Override
    public void updateStock(long productId, int amount) {
        final Product product = em.find(Product.class, productId);
        product.setStock(amount);
        //em.merge(product); //TODO: Is em.merge() permitted?
    }

    @Override
    public void updatePrice(long productId, int price) {
        final Product product = em.find(Product.class, productId);
        product.setPrice(price);
    }

    @Override
    public Boolean addStock(String name, int amount) {
        //TODO: This logic could be moved completely to service
        final Optional<Product> product = getByName(name);
        if(!product.isPresent()) throw new ProductNotFoundException();
        product.get().setStock(amount);
        return true; //TODO: This booleans should be removed, no purpose at all
    }
}
