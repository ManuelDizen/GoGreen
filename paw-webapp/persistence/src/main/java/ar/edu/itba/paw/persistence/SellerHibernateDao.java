package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

@Repository
public class SellerHibernateDao implements SellerDao {
    private static int EXPLORE_SELLER_PAGE_SIZE = 4;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Seller create(User user, String phone, String address, Area area) {
        final Seller seller = new Seller(user, phone, address, area);
        em.persist(seller);
        return seller;
    }

    @Override
    public Optional<Seller> findById(long id) {
        return Optional.ofNullable(em.find(Seller.class, id));
    }

    private Integer getCount(String name,Area area, boolean favorite,
                         long userId) {

        StringBuilder nativeQuery = new StringBuilder();
        Map<String, Object> args = new HashMap<>();
        nativeQuery.append("SELECT COUNT(*) FROM sellers WHERE true");

        if(name != null && !name.equals("")){
            nativeQuery.append(" AND userid IN (SELECT id FROM users WHERE LOWER(firstName) like :name)");
            args.put("name", '%' + name.toLowerCase() + '%');
        }
        if(area != null){
            nativeQuery.append(" AND areaid = :areaid");
            args.put("areaid", area.getId());
        }
        if(favorite) {
            nativeQuery.append(" AND id IN (SELECT seller_id FROM favorites WHERE user_id = :userId)");
            args.put("userId", userId);
        }

        Query finalNativeQuery = em.createNativeQuery(nativeQuery.toString());
        for(String key : args.keySet()){
            finalNativeQuery.setParameter(key, args.get(key));
        }

        return ((BigInteger)finalNativeQuery.getSingleResult()).intValue();

    }

    @Override
    public Optional<Seller> findByUserId(long userId) {
        //TODO: Redo this method
        final TypedQuery<Seller> query = em.createQuery("FROM Seller WHERE userid = :userid", Seller.class);
        query.setParameter("userid", userId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Seller> findByMail(String mail) {
        final TypedQuery<Seller> query = em.createQuery("FROM Seller where userid = " +
                        "(SELECT id from User where email = :email)", Seller.class);
        query.setParameter("email", mail);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Seller> getAll() {
        return em.createQuery("FROM Seller", Seller.class).getResultList();
    }

    public Pagination<Seller> filter(String name, Area area, boolean favorite, int page,
                              long userId){

        //TODO: Creo que direction no tiene mucho sentido acá considerando que después vamos a sortear
        StringBuilder nativeQuery = new StringBuilder();
        Map<String, Object> args = new HashMap<>();
        nativeQuery.append("SELECT id FROM sellers WHERE true");

        if(name != null && !name.equals("")){
            nativeQuery.append(" AND userid IN (SELECT id FROM users WHERE LOWER(firstName) like :name)");
            args.put("name", '%' + name.toLowerCase() + '%');
        }
        if(area != null){
            nativeQuery.append(" AND areaid = :areaid");
            args.put("areaid", area.getId());
        }
        if(favorite) {
            nativeQuery.append(" AND id IN (SELECT seller_id FROM favorites WHERE user_id = :userId)");
            args.put("userId", userId);
        }

        nativeQuery.append(" LIMIT :limit OFFSET :offset");

        Query finalNativeQuery = em.createNativeQuery(nativeQuery.toString());
        for(String key : args.keySet()){
            finalNativeQuery.setParameter(key, args.get(key));
        }
        finalNativeQuery.setParameter("limit", EXPLORE_SELLER_PAGE_SIZE);
        finalNativeQuery.setParameter("offset", (page-1)*EXPLORE_SELLER_PAGE_SIZE);

        final List<Long> sellerIds = new ArrayList<>();
        for (Object o : finalNativeQuery.getResultList()) {
            sellerIds.add(((BigInteger) o).longValue());
        }

        if(sellerIds.isEmpty())
            return new Pagination<>(new ArrayList<>(), (long) page,
                    0);

        final TypedQuery<Seller> finalQuery =
                em.createQuery("SELECT DISTINCT s FROM Seller s WHERE s.id IN :sellers", Seller.class);
        finalQuery.setParameter("sellers", sellerIds);

        List<Seller> sellerPage = finalQuery.getResultList();

        return new Pagination<>(sellerPage, (long) page,
                (getCount(name, area, favorite, userId) + EXPLORE_SELLER_PAGE_SIZE - 1)/EXPLORE_SELLER_PAGE_SIZE);



    }
}
