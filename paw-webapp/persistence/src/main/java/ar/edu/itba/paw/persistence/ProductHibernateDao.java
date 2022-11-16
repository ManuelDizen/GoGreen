package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.models.*;
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
    public Product create(Seller seller, long categoryId, String name, String description, int stock,
                          Integer price, Image image) {
        final Product prod = new Product(seller, categoryId, name, description, stock, price, image);
        em.persist(prod);
        return prod;
    }

    @Override
    public List<Product> findBySeller(long sellerId) {
        ProductStatus deleted = ProductStatus.DELETED;
        final TypedQuery<Product> query = em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.seller.id = :sellerId " +
                        "AND p.status <> :deleted ORDER BY p.id DESC",
                Product.class);
        query.setParameter("sellerId", sellerId);
        query.setParameter("deleted", deleted);
        return query.getResultList();
    }

    @Override
    public List<Product> findBySellerNoEcotag(long sellerId) {
        ProductStatus deleted = ProductStatus.DELETED;
        final TypedQuery<Product> query = em.createQuery("SELECT DISTINCT p FROM Product p" +
                        " WHERE p.seller.id = :sellerId " +
                        "AND p.status <> :deleted ORDER BY p.id DESC",
                Product.class);
        query.setParameter("sellerId", sellerId);
        query.setParameter("deleted", deleted);
        return query.getResultList();
    }

    @Override
    public Optional<Product> getById(long productId) {
        //TODO: Preguntar si esta manera es mejor, me queda la duda si poner lazy pero traer los
        // taglist de esta manera es mejor que ponerlo como eager directamente (la realidad es que casi
        // siempre que necesitamos el producto necesitamos el tagList).
        return em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.id = :id " +
                        "ORDER BY p.id DESC",
                Product.class).setParameter("id", productId).getResultList().stream().findFirst();
        //return Optional.ofNullable(em.find(Product.class, productId));
    }

    @Override
    public Optional<Product> getByName(String name) {
        final TypedQuery<Product> query = em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.name = :name " +
                        "AND p.status <> :deleted " +
                        "ORDER BY p.id DESC",
                Product.class);
        query.setParameter("name", name);
        query.setParameter("deleted", ProductStatus.DELETED);
        return query.getResultList().stream().findFirst();
    }

    private TypedQuery<Product> buildAvailableQuery(){
        ProductStatus available = ProductStatus.AVAILABLE;
        final TypedQuery<Product> query = em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.stock > 0 " +
                        "AND p.status = :available " +
                        "ORDER BY p.id DESC",
                Product.class);
        query.setParameter("available", available);
        return query;
    }
    @Override
    public List<Product> getAvailable() {
        final TypedQuery<Product> query = buildAvailableQuery();
        return query.getResultList();
    }

    @Override
    public List<Product> getAvailable(int limit) {
        final TypedQuery<Product> query = buildAvailableQuery();
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Product> filter(String name, long category, List<Long> tags, Integer maxPrice,
                                long areaId) {

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
                em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.status = :available AND p.id IN :products", Product.class);
        finalQuery.setParameter("products", products);
        finalQuery.setParameter("available", ProductStatus.AVAILABLE);

        return finalQuery.getResultList();
    }

    public int getSales(String productName) {
        int cant = 0;
        String query = "SELECT amount FROM orders WHERE productname = :productName";
        Query jpaQuery = em.createNativeQuery(query);
        jpaQuery.setParameter("productName", productName);
        for (Object o : jpaQuery.getResultList()) {
            cant += (Integer) o;
        }
        return cant;
    }

    @Override
    public List<Product> getByCategory(Long categoryId){
        final TypedQuery<Product> query = em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.categoryId = :categoryId " +
                        "AND p.image.id <> 0 ORDER BY p.id DESC",
                Product.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Override
    public boolean atLeastOneProduct(){
        String queryStr = "SELECT COUNT(*) FROM products";
        Query query = em.createNativeQuery(queryStr);
        return ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).intValue() > 0;
    }

    //TODO: :Refactor the two methods below (casi el mismo código)

    @Override
    public Pagination<Product> findBySeller(long sellerId, int page, int amount) {
        ProductStatus deleted = ProductStatus.DELETED;
        final TypedQuery<Product> query = em.createQuery(
                "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.seller.id = :sellerId " +
                        "AND p.status <> :deleted ORDER BY p.id DESC",
                Product.class);
        query.setParameter("sellerId", sellerId);
        query.setParameter("deleted", deleted);
        query.setMaxResults(amount);
        query.setFirstResult((page-1)*amount);

        String str = "FROM products WHERE sellerid = :sellerId";
        final Query countQuery = em.createNativeQuery("SELECT COUNT(*) " + str);
        countQuery.setParameter("sellerId", sellerId);
        @SuppressWarnings("unchecked")
        long count =
                ((BigInteger)countQuery.getResultList().stream().findFirst().orElse(0)).longValue();
        return new Pagination<>(query.getResultList(), page, (count+amount-1)/amount);
    }

    @Override
    public Pagination<Product> findBySellerNoEcotag(long sellerId, int page, int amount) {
        ProductStatus deleted = ProductStatus.DELETED;
        final TypedQuery<Product> query = em.createQuery("SELECT DISTINCT p FROM Product p" +
                        " WHERE p.seller.id = :sellerId " +
                        "AND p.status <> :deleted ORDER BY p.id DESC",
                Product.class);
        query.setParameter("sellerId", sellerId);
        query.setParameter("deleted", deleted);
        query.setMaxResults(amount);
        query.setFirstResult((page-1)*amount);

        String str = "FROM products WHERE sellerid = :sellerId AND productstatus_id <> :deletedId";
        final Query countQuery = em.createNativeQuery("SELECT COUNT(*) " + str);
        countQuery.setParameter("sellerId", sellerId);
        countQuery.setParameter("deletedId", deleted.getId());
        @SuppressWarnings("unchecked")
        long count =
                ((BigInteger)countQuery.getResultList().stream().findFirst().orElse(0)).longValue();
        return new Pagination<>(query.getResultList(), page, (count+amount-1)/amount);
    }
}
