package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.OrderService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    final static int PAGE_SIZE = 2;
    private OrderDao orderDao;
    private EmailService emailService;
    private SellerService sellerService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, EmailService emailService, SellerService sellerService) {
        this.orderDao = orderDao;
        this.emailService = emailService;
        this.sellerService = sellerService;
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

    @Override
    public void createAndNotify(Product product, User user, Seller seller, int amount, String message) {

        emailService.purchase(user.getEmail(), user.getFirstName(),
                product, amount,
                product.getPrice(), sellerService.getName(seller.getUserId()),
                seller.getPhone(), sellerService.getEmail(seller.getUserId()), user.getLocale());

        emailService.itemsold(sellerService.getEmail(seller.getUserId()),
                sellerService.getName(seller.getUserId()), product,
                amount, product.getPrice(), user.getFirstName(), user.getEmail(),
                message, sellerService.getLocale(seller.getUserId()));

        LocalDateTime dateTime = LocalDateTime.now();

        Order order = orderDao.create(product.getName(), user.getFirstName(),
                user.getSurname(), user.getEmail(), sellerService.getName(seller.getUserId()),
                sellerService.getSurname(seller.getUserId()),
                sellerService.getEmail(seller.getUserId()), amount, product.getPrice(), dateTime,
                message);

        if(order == null) throw new IllegalStateException("No se instanció la orden");
    }

    @Override
    public List<List<Order>> divideIntoPages(List<Order> list) {
        List<List<Order>> pageList = new ArrayList<>();

        int aux = 1;
        while(aux <= list.size()/PAGE_SIZE) {
            pageList.add(list.subList((aux-1)*PAGE_SIZE, aux*PAGE_SIZE));
            aux++;
        }
        if(list.size() % PAGE_SIZE != 0)
            pageList.add(list.subList((aux-1)*PAGE_SIZE, list.size()));
        return pageList;

    }
}
