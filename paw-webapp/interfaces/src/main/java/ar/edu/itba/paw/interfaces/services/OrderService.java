package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order create(String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, float price, LocalDateTime dateTime, String message);

    Optional<Order> getById(long orderId);
    List<Order> getBySellerEmail(String sellerEmail);
    List<Order> getByBuyerEmail(String buyerEmail);
    Boolean createAndNotify(Product product, User user, Seller seller, int amount,
                         String message);

    List<List<Order>> divideIntoPages(List<Order> list);

    Boolean checkForOrderOwnership(long orderId);

    Boolean deleteOrder(long orderId);
}
