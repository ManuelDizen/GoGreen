package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderHibernateDao implements OrderDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Order create(String productName, String buyerName, String buyerSurname, String buyerEmail,
                        String sellerName, String sellerSurname, String sellerEmail, Integer amount,
                        Integer price, LocalDateTime dateTime, String message, Seller seller) {
        final Order order = new Order(productName, buyerName, buyerSurname, buyerEmail, sellerName,
                sellerSurname, sellerEmail, amount, price, dateTime, message, seller);
        em.persist(order);
        return order;
    }

    @Override
    public Optional<Order> getById(long orderId) {
        return Optional.ofNullable(em.find(Order.class, orderId));
    }

    private Pagination<Order> queryResolver(boolean buyer, String email, int page, int amount) {
        String str = "FROM orders WHERE ";
        String str2 = "SELECT * FROM orders WHERE ";
        if(buyer) {
            str += "buyeremail";
            str2 += "buyeremail";
        } else {
            str += "selleremail";
            str2 += "selleremail";
        }
        str += " = :email";
        str2 += " = :email ORDER BY datetime DESC LIMIT :limit OFFSET :offset";

        final Query query = em.createNativeQuery(
                str2, Order.class);

        query.setParameter("email", email);
        query.setParameter("limit", amount);
        query.setParameter("offset", (page-1)*amount);

        final Query countQuery = em.createNativeQuery("SELECT COUNT(*) " + str);
        countQuery.setParameter("email", email);
        @SuppressWarnings("unchecked")
        int count =
                ((BigInteger)countQuery.getResultList().stream().findFirst().orElse(0)).intValue();
        return new Pagination<>(query.getResultList(), page,(count+amount-1)/amount);

    }

    @Override
    public Pagination<Order> getByBuyerEmail(String buyerEmail, int page, int amount) {
        return queryResolver(true, buyerEmail, page, amount);
    }

    @Override
    public Pagination<Order> getBySellerEmail(String sellerEmail, int page, int amount) {
        return queryResolver(false, sellerEmail, page, amount);
    }

    @Override
    public void deleteOrder(long orderId) {
        em.remove(em.find(Order.class, orderId));
    }

    @Override
    public int getTotalOrdersForSeller(String sellerEmail){
        String queryStr = "SELECT COALESCE(SUM(amount), 0) FROM orders WHERE selleremail = :selleremail";
        Query query = em.createNativeQuery(queryStr);
        query.setParameter("selleremail", sellerEmail);
        return ((BigInteger) query.getResultList().stream().findFirst().orElse(0)).intValue();
    }

    @Override
    public List<String> getFirstNDistinct(int amount) {
        String queryStr = "SELECT productname FROM orders GROUP BY productname ORDER BY " +
                "MAX(id) DESC LIMIT :limit";
        Query query = em.createNativeQuery(queryStr);
        query.setParameter("limit", amount);
        return query.getResultList();
    }
}
