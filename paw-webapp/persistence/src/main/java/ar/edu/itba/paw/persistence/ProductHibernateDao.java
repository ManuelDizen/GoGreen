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

    private static final int EXPLORE_PAGE_SIZE = 12;

    private final static int ASCENDING = 0;
    private final static int DESCENDING = 1;

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
        return em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.id = :id " +
                        "ORDER BY p.id DESC",
                Product.class).setParameter("id", productId).getResultList().stream().findFirst();
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

    public Long getAmount() {
        return em.createQuery("SELECT COUNT(*) FROM Product", Long.class).getSingleResult();
    }

    public Integer getCount(String name, long category, List<Long> tags, Integer maxPrice, long areaId, boolean favorite, long userId) {

        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM products WHERE ");
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
        if(favorite) {
            query.append(" AND sellerId IN (SELECT seller_id FROM favorites WHERE user_id = :userId)");
            args.put("userId", userId);
        }

        query.append("AND productstatus_id = :available AND stock <> 0");

        Query jpaQuery = em.createNativeQuery(query.toString());

        for(Map.Entry<String, Object> entry : args.entrySet()) {
            jpaQuery.setParameter(entry.getKey(), entry.getValue());
        }

        jpaQuery.setParameter("available", ProductStatus.AVAILABLE.getId());

        return ((BigInteger)jpaQuery.getSingleResult()).intValue();

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

        List<Long> jpaList = jpaQuery.getResultList();

        List<Long> products = new ArrayList<>();
        for(Object o : jpaList) {
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

    private String getOrder(int sort, int direction) {
        String toRet = "";
        /*if (sort == Sort.SORT_POPULAR.getId()) {
            toRet += "price ";
        } else */
        if (sort == Sort.SORT_PRICE.getId()) {
            toRet += "price ";
        } else if (sort == Sort.SORT_ALPHABETIC.getId()) {
            toRet += "name ";
        } else if (sort == Sort.SORT_CHRONOLOGIC.getId()) {
            toRet += "id ";
        }
        if(direction == ASCENDING)
            toRet += "ASC";
        else if(direction == DESCENDING)
            toRet += "DESC";
        return toRet;
    }

    private String getOrder2(int sort, int direction) {

        String toRet = "";

        /*if (sort == Sort.SORT_POPULAR.getId()) {
            toRet += "p.price ";
        } else */
        if (sort == Sort.SORT_PRICE.getId()) {
            toRet += "p.price ";
        } else if (sort == Sort.SORT_ALPHABETIC.getId()) {
            toRet += "p.name ";
        } else if (sort == Sort.SORT_CHRONOLOGIC.getId()) {
            toRet += "p.id ";
        }

        if(direction == ASCENDING)
            toRet += "ASC";
        else if(direction == DESCENDING)
            toRet += "DESC";

        return toRet;
    }

    @Override
    public Pagination<Product> filter(String name, long category, List<Long> tags, Integer maxPrice, long areaId, boolean favorite, int page, int sort, int direction, long userId) {

        //Falta cambiar algo de la implementación de las ecotags para poder filtrar por ecotags correctamente

        Integer count = getCount(name, category, tags, maxPrice, areaId, favorite, userId);

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
        if(favorite) {
            query.append(" AND sellerId IN (SELECT seller_id FROM favorites WHERE user_id = :userId)");
            args.put("userId", userId);
        }

        query.append("AND productstatus_id = :available AND stock <> 0 ORDER BY ");
        String order = getOrder(sort, direction);
        query.append(order);
        query.append(" LIMIT :limit OFFSET :offset");

        Query jpaQuery = em.createNativeQuery(query.toString());

        for(Map.Entry<String, Object> entry : args.entrySet()) {
            jpaQuery.setParameter(entry.getKey(), entry.getValue());
        }

        jpaQuery.setParameter("available", ProductStatus.AVAILABLE.getId());
        jpaQuery.setParameter("limit", EXPLORE_PAGE_SIZE);
        jpaQuery.setParameter("offset", (page-1)*EXPLORE_PAGE_SIZE);

        List<Long> jpaList = jpaQuery.getResultList();

        List<Long> products = new ArrayList<>();
        for(Object o : jpaList) {
            products.add(((Integer)o).longValue());
        }

        if(products.isEmpty())
            return new Pagination<>(new ArrayList<>(), (long) page,
                    0);;
        String order2 = getOrder2(sort, direction);
        final TypedQuery<Product> finalQuery =
                em.createQuery("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.tagList" +
                        " WHERE p.id IN :products ORDER BY " + order2, Product.class);
        finalQuery.setParameter("products", products);

        List<Product> productList = finalQuery.getResultList();

        return new Pagination<>(productList, (long) page,
                (count + EXPLORE_PAGE_SIZE - 1)/EXPLORE_PAGE_SIZE);
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

    @Override
    public Pagination<Product> findBySeller(long sellerId, int page, int amount, boolean ecotag) {
        ProductStatus deleted = ProductStatus.DELETED;

        String str = "FROM products WHERE sellerid = :sellerId AND productstatus_id <> :deleted";

        Query query = em.createNativeQuery("SELECT id " + str + " ORDER BY id DESC LIMIT :limit OFFSET :offset");
        query.setParameter("sellerId", sellerId);
        query.setParameter("deleted", deleted.getId());
        query.setParameter("limit", amount);
        query.setParameter("offset", (page-1)*amount);

        List<Long> products = new ArrayList<>();
        for(Object o : query.getResultList()) {
//            BigInteger big = BigInteger.valueOf((Integer)o);
//            products.add(big.longValue());
            products.add(((Integer)o).longValue());
        }

        if(products.isEmpty())
            return new Pagination<>(new ArrayList<>(), (long) page,
                    0);;

        String strEcotag = "";

        if(ecotag)
            strEcotag = "LEFT JOIN FETCH p.tagList";

        final TypedQuery<Product> jpaQuery = em.createQuery(
                "SELECT DISTINCT p FROM Product p " + strEcotag +
                        " WHERE p.id IN :products " +
                        " ORDER BY p.id DESC",
                Product.class);
        jpaQuery.setParameter("products", products);

        final Query countQuery = em.createNativeQuery("SELECT COUNT(*) " + str);
        countQuery.setParameter("sellerId", sellerId);
        countQuery.setParameter("deleted", deleted.getId());
        @SuppressWarnings("unchecked")
        long count =
                ((BigInteger)countQuery.getResultList().stream().findFirst().orElse(0)).longValue();
        return new Pagination<>(jpaQuery.getResultList(), page, (count+amount-1)/amount);
    }


}
