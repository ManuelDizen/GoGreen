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

    @Override
    public Order create(String productName, String buyerName, String buyerSurname,
                        String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                        Integer amount, Integer price, LocalDateTime dateTime, String message) {
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
    public List<Order> getByBuyerEmail(String buyerEmail) {
        return orderDao.getByBuyerEmail(buyerEmail);
    }

    @Transactional
    @Override
    public void createAndNotify(long productId, int amount, String message) {
        //TODO: Cambiar nombre (métodos tienen que usar unitarios)
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
        if(user == null) throw new UnauthorizedRoleException();

        final Optional<Seller> maybeSeller = sellerService.findById(product.getSeller().getId());
        if(!maybeSeller.isPresent()) throw new UserNotFoundException();
        final Seller seller = maybeSeller.get();

        // Sobre el locale de los mails: Si bien almacenamos el locale de los usuarios a la
        // hora de registrarse, si el usuario está navegando en ese momento en otro Locale,
        // resulta pertinente enviarle el mail en ese idioma. Es por eso que el mail de
        // user sale con el locale del navegador actual, y el del vendedor con el guardado
        // (no lo tenemos guardado)

        //Update 28/10/22: Por corrección de cátedra, se remueve el mail enviado a comprador.
        // El mismo no debe ser notificado de acciones que él realizó dentro de la página

        /*emailService.purchase(user.getEmail(), user.getFirstName(),
                product, amount,
                product.getPrice(), sellerService.getName(seller.getUser().getId()),
                seller.getPhone(), sellerService.getEmail(seller.getUser().getId()),
                user.getLocale());*/

        emailService.itemsold(sellerService.getEmail(seller.getUser().getId()),
                sellerService.getName(seller.getUser().getId()), product,
                amount, product.getPrice(), user.getFirstName(), user.getEmail(),
                message, sellerService.getLocale(seller.getUser().getId()));

        LocalDateTime dateTime = LocalDateTime.now(); //TODO: Explorar acá

        Order order = orderDao.create(product.getName(), user.getFirstName(),
                user.getSurname(), user.getEmail(), sellerService.getName(seller.getUser().getId()),
                sellerService.getSurname(seller.getUser().getId()),
                sellerService.getEmail(seller.getUser().getId()), amount, product.getPrice(), dateTime,
                message);
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
    public int getTotalOrdersForSeller(String sellerEmail){
        return orderDao.getTotalOrdersForSeller(sellerEmail);
    }
}
