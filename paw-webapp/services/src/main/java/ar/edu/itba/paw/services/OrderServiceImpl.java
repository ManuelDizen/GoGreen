package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    final static int PAGE_SIZE = 2;
    private final OrderDao orderDao;
    private final ProductService productService;
    private final EmailService emailService;
    private final SellerService sellerService;
    private final SecurityService securityService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ProductService productService,
                            EmailService emailService, SellerService sellerService, SecurityService securityService) {
        this.orderDao = orderDao;
        this.productService = productService;
        this.emailService = emailService;
        this.sellerService = sellerService;
        this.securityService = securityService;
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

        productService.updateStock(product.getProductId(), amount);

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
        if(list.size() == 0) pageList.add(new ArrayList<>());
        return pageList;

    }

    @Override
    public Boolean checkForOrderOwnership(long orderId) {
        User user = securityService.getLoggedUser();
        if(user == null) return false;
        Optional<Order> maybeOrder = getById(orderId);
        if(maybeOrder.isPresent()){
            Order order = maybeOrder.get();
            System.out.println("Entre a order.get");
            System.out.println("Seller email: " + order.getSellerEmail() + " userEmail: " + user.getEmail());
            return order.getSellerEmail().equals(user.getEmail());
        }
        return false;
    }

    @Override
    public Boolean deleteOrder(long orderId) {
        /* Primero, borro instancia de orden. Después, reestablezo stock */
        Optional<Order> order = orderDao.getById(orderId);
        if(!order.isPresent()) throw new IllegalStateException();
        Boolean delete = orderDao.deleteOrder(orderId);
        if(!delete) throw new RuntimeException("No volví de deleted");
        return productService.addStock(order.get().getProductName(), order.get().getAmount());
    }
}
