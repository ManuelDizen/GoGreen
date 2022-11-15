package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Seller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order create(String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, Integer price, LocalDateTime dateTime, String message, Seller seller);

    Optional<Order> getById(long orderId);
    List<Order> getBySellerEmail(String sellerEmail);
    List<Order> getByBuyerEmail(String buyerEmail);
    Pagination<Order> getByBuyerEmail(String buyerEmail, int page, int amount);
    Pagination<Order> getBySellerEmail(String sellerEmail, int page, int amount);

    void deleteOrder(long orderId);
    int getTotalOrdersForSeller(String sellerEmail);
    int getTotalOrdersForUser(String buyerEmail);

}
