package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Pagination;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order create(long productId, int amount, String message);
    Optional<Order> getById(long orderId);
    Pagination<Order> getByBuyerEmail(String buyerEmail, int page);
    Pagination<Order> getBySellerEmail(String sellerEmail, int page);
    boolean checkForOrderOwnership(long orderId);
    void deleteOrder(long orderId);
    int getTotalOrdersForSeller(String sellerEmail);
    List<String> getFirstNDistinct(int amount);
}
