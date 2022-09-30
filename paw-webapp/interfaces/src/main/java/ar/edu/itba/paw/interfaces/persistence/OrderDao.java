package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order create(String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, float price, LocalDateTime dateTime, String message);

    Optional<Order> getById(long orderId);
    List<Order> getBySellerEmail(String sellerEmail);
    List<Order> getByBuyerEmail(String buyerEmail);

    Boolean deleteOrder(long orderId);

}
