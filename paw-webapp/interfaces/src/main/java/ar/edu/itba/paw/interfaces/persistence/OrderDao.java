package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Order;

import java.util.Optional;

public interface OrderDao {

    Order create(long id, String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, float price);

    Optional<Order> getById(long orderId);
    Optional<Order> getBySellerEmail(String sellerEmail);
    Optional<Order> getBuBuyerEmail(String buyerEmail);

}
