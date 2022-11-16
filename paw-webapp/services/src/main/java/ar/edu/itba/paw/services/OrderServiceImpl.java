package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    final static int PAGE_SIZE = 8;
    private final OrderDao orderDao;
    private final ProductService productService;
    private final EmailService emailService;
    private final SellerService sellerService;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ProductService productService,
                            EmailService emailService, SellerService sellerService,
                            UserService userService) {
        this.orderDao = orderDao;
        this.productService = productService;
        this.emailService = emailService;
        this.sellerService = sellerService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public Order create(long productId, int amount, String message) {
        final Optional<Product> maybeProduct = productService.getById(productId);
        if(!maybeProduct.isPresent()) throw new ProductNotFoundException();
        final Product product = maybeProduct.get();

        boolean enough = productService.checkForAvailableStock(product, amount);
        if(!enough){
            //TODO: Preguntar que hacer acá. Capaz podríamos handlearlo sin un 500
            // De momento quedo con un 400, pero preguntar.
            throw new InsufficientStockException();
        }

        User user = userService.getLoggedUser();
        if(user == null || userService.isLoggedSeller()) throw new UnauthorizedRoleException();

        final Optional<Seller> maybeSeller = sellerService.findById(product.getSeller().getId());
        if(!maybeSeller.isPresent()) throw new UserNotFoundException();
        final Seller seller = maybeSeller.get();

        emailService.itemsold(sellerService.getEmail(seller.getUser().getId()),
                sellerService.getName(seller.getUser().getId()), product,
                amount, product.getPrice(), user.getFirstName(), user.getEmail(),
                message, sellerService.getLocale(seller.getUser().getId()));

        LocalDateTime dateTime = LocalDateTime.now();

        Order order = orderDao.create(product.getName(), user.getFirstName(),
                user.getSurname(), user.getEmail(), sellerService.getName(seller.getUser().getId()),
                sellerService.getSurname(seller.getUser().getId()),
                sellerService.getEmail(seller.getUser().getId()), amount, product.getPrice(), dateTime,
                message, seller);
        if(order == null) throw new OrderCreationException();

        productService.decreaseStock(product.getProductId(), amount);
        Optional<Product> modified = productService.getById(product.getProductId());
        if (!modified.isPresent()) throw new ProductNotFoundException();
        if (modified.get().getStock() == 0) {
            Optional<User> seller2 = userService.findById(seller.getUser().getId());
            if (!seller2.isPresent()) throw new UserNotFoundException();
            User u = seller2.get();
            emailService.noMoreStock(modified.get(), u.getEmail(), u.getFirstName(),
                    u.getSurname(), u.getLocale());
            modified.get().setStatus(ProductStatus.OUTOFSTOCK);
        }
        return order;
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
    public List<Order> getByBuyerEmail(String buyerEmail) {
        return orderDao.getByBuyerEmail(buyerEmail);
    }

    @Override
    public Pagination<Order> getByBuyerEmail(String buyerEmail, int page){
        return orderDao.getByBuyerEmail(buyerEmail, page, PAGE_SIZE);
    }

    @Override
    public Pagination<Order> getBySellerEmail(String sellerEmail, int page){
        return orderDao.getBySellerEmail(sellerEmail, page, PAGE_SIZE);
    }

    @Override
    public boolean checkForOrderOwnership(long orderId) {
        User user = userService.getLoggedUser();
        if(user == null) throw new UnauthorizedRoleException();
        Optional<Order> maybeOrder = getById(orderId);
        if(maybeOrder.isPresent()){
            Order order = maybeOrder.get();
            return order.getSellerEmail().equals(user.getEmail());
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteOrder(long orderId) {
        boolean isOwner = checkForOrderOwnership(orderId);
        if(!isOwner) throw new UnauthorizedRoleException();

        Optional<Order> order = orderDao.getById(orderId);
        if(!order.isPresent()) throw new OrderNotFoundException();

        orderDao.deleteOrder(orderId);

        Optional<User> buyer = userService.findByEmail(order.get().getBuyerEmail());
        if(!buyer.isPresent()) throw new UserNotFoundException();
        Optional<User> seller = userService.findByEmail(order.get().getSellerEmail());
        if(!seller.isPresent()) throw new UserNotFoundException();
        emailService.orderCancelled(order.get(), buyer.get().getLocale(), seller.get().getLocale());
        productService.addStock(order.get().getProductName(), order.get().getAmount());
    }

    @Override
    public int getTotalOrdersForUser(String userEmail){
        return orderDao.getTotalOrdersForSeller(userEmail);
    }

    @Override
    public List<String> getFirstNDistinct(int amount) {
        return orderDao.getFirstNDistinct(amount);
    }

    @Override
    public int getTotalOrdersForSeller(String sellerEmail){
        return orderDao.getTotalOrdersForSeller(sellerEmail);
    }
}
