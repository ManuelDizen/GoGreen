package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Pagination;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order create(String productName, String buyerName, String buyerSurname,
                 String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                 Integer amount, Integer price, LocalDateTime dateTime, String message);

    Optional<Order> getById(long orderId);
    List<Order> getBySellerEmail(String sellerEmail);
    List<Order> getByBuyerEmail(String buyerEmail);
    Pagination<Order> getByBuyerEmail(String buyerEmail, int page, int amount);

    //TODO: Momentaneo, estaría bueno que parametrizes estos dos metodos dado que la unica diferencia
    // en todo el codigo es un parametro
    Pagination<Order> getBySellerEmail(String sellerEmail, int page, int amount);

    void deleteOrder(long orderId);
    int getTotalOrdersForSeller(String sellerEmail);
    int getTotalOrdersForUser(String buyerEmail);

}
