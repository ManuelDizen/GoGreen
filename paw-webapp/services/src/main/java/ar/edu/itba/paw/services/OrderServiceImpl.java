package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.interfaces.services.OrderService;
import ar.edu.itba.paw.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order create(String productName, String buyerName, String buyerSurname,
                        String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                        Integer amount, float price, LocalDateTime dateTime, String message) {
        return orderDao.create(productName, buyerName, buyerSurname, buyerEmail,
                sellerName, sellerSurname, sellerEmail, amount, price, dateTime, message);
    }

    @Override
    public Optional<Order> getById(long orderId) {
        return orderDao.getById(orderId);
    }

    @Override
    public List<Order> getBySellerEmail(String sellerEmail) {
        return orderDao.getBySellerEmail(sellerEmail);
    }

    @Override
    public List<Order> getBuBuyerEmail(String buyerEmail) {
        return orderDao.getByBuyerEmail(buyerEmail);
    }
}
