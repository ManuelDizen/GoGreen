package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.OrderService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BasicController {
    private static final int N_LANDING = 4;
    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public BasicController(final ProductService productService, final UserService userService,
                           final OrderService orderService) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
    }

    @RequestMapping("/")
    public ModelAndView landingPage() {
        User loggedUser = userService.getLoggedUser();
        List<Order> ordersForUser = new ArrayList<>();
        List<String> popularOrders = new ArrayList<>();
        if(loggedUser!=null) ordersForUser =
            orderService.getByBuyerEmail(loggedUser.getEmail(), 1).getItems();
        if(loggedUser == null || ordersForUser.isEmpty()){
            popularOrders = orderService.getFirstNDistinct(N_LANDING);
        }
        List<Product> products = productService.getLandingProducts(loggedUser,
                ordersForUser, popularOrders);
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);
        mav.addObject("categories", Category.values());
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final String referer = request.getHeader("Referer");
        if (session != null && referer != null && !referer.contains("login")) {
            session.setAttribute("url_prior_login", referer);
        }
        return new ModelAndView("login");
    }

}
