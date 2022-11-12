package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order create(String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, Integer price, LocalDateTime dateTime, String message);

    Optional<Order> getById(long orderId);
    List<Order> getBySellerEmail(String sellerEmail);
    List<Order> getByBuyerEmail(String buyerEmail);
    void createAndNotify(long productId, int amount, String message);


    boolean checkForOrderOwnership(long orderId);

    void deleteOrder(long orderId);
    int getTotalOrdersForSeller(String sellerEmail);
    int getTotalOrdersForUser(String userEmail);
}
