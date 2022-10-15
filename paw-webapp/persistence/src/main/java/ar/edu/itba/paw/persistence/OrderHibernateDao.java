package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.models.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
                        Integer price, LocalDateTime dateTime, String message) {
        final Order order = new Order(productName, buyerName, buyerSurname, buyerEmail, sellerName,
                sellerSurname, sellerEmail, amount, price, dateTime, message);
        em.persist(order);
        return order;
    }

    @Override
    public Optional<Order> getById(long orderId) {
        return Optional.ofNullable(em.find(Order.class, orderId));
    }

    @Override
    public List<Order> getBySellerEmail(String sellerEmail) {
        final TypedQuery<Order> query = em.createQuery("FROM Order AS o WHERE o.sellerEmail = :sellerEmail",
                Order.class);
        query.setParameter("sellerEmail", sellerEmail);
        return query.getResultList();
    }

    @Override
    public List<Order> getByBuyerEmail(String buyerEmail) {
        final TypedQuery<Order> query = em.createQuery("FROM Order AS o WHERE o.buyerEmail = :buyerEmail",
                Order.class);
        query.setParameter("buyerEmail", buyerEmail);
        return query.getResultList();
    }

    @Override
    public Boolean deleteOrder(long orderId) {
        em.remove(em.find(Order.class, orderId));
        return true;
        // TODO: MODIFY!!!
    }
}
