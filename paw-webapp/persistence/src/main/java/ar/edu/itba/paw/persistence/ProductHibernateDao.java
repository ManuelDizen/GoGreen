package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

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

        //Falta cambiar algo de la implementación de las ecotags para poder filtrar por ecotags correctamente

        StringBuilder query = new StringBuilder("SELECT id FROM products WHERE ");
        Map<String, Object> args = new HashMap<>();
        if(name != null){
            query.append("LOWER(name) like :name ");
            args.put("name", '%' + name.toLowerCase() + '%');
        }

        if(category != 0){
            query.append("AND categoryId = :category ");
            args.put("category", category);
        }
        if(tags.size() != 0){
            for(int i=0; i<tags.size(); i++) {
                query.append("AND id IN (SELECT productId from tags_to_products WHERE tag = :tag").append(i).append(") ");
                args.put("tag" + i, tags.get(i));
            }
        }
        if(maxPrice != -1.0){
            query.append("AND price <= :maxPrice ");
            args.put("maxPrice", maxPrice);

        }
        if(areaId > 0){
            query.append("AND sellerId IN (SELECT id FROM sellers WHERE areaId = :areaId) ");
            args.put("areaId", areaId);
        }
        query.append("AND stock <> 0 ORDER BY id DESC");

        Query jpaQuery = em.createNativeQuery(query.toString());

        for(Map.Entry<String, Object> entry : args.entrySet()) {
            jpaQuery.setParameter(entry.getKey(), entry.getValue());
        }

        List<Long> products = new ArrayList<>();
        for(Object o : jpaQuery.getResultList()) {
//            BigInteger big = BigInteger.valueOf((Integer)o);
//            products.add(big.longValue());
            products.add(((Integer)o).longValue());
        }


        if(products.isEmpty())
            return new ArrayList<>();

        final TypedQuery<Product> finalQuery =
                em.createQuery("FROM Product as p where p.id IN :products", Product.class);
        finalQuery.setParameter("products", products);

        return finalQuery.getResultList();
    }

    public int getSales(String productName) {
        int cant = 0;
        String query = "SELECT amount FROM orders WHERE productname = :productName";
        Query jpaQuery = em.createNativeQuery(query);
        jpaQuery.setParameter("productName", productName);
        for(Object o : jpaQuery.getResultList()) {
            cant += (Integer)o;
        }
        return cant;
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

    @Override
    public List<Product> getByCategory(Long categoryId){
        final TypedQuery<Product> query = em.createQuery("FROM Product WHERE categoryId = :categoryId " +
                        "AND image.id <> 0",
                Product.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }
}
