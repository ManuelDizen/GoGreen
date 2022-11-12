package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.OrderService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
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
        //  The orders are called from the controller as creating a method on either the productService
        //  or the orderService would produce a circular dependency
        //TODO: Refactor this logic to a service (at least to get popular products or not)
        // Acá hay otro error conceptual donde me traigo TODAS las tuplas por vendedor
        // solo para ver si es un conjunto vacio. Revisar.
        if(loggedUser!=null) ordersForUser = orderService.getByBuyerEmail(loggedUser.getEmail());
        List<Product> products = productService.getLandingProducts(loggedUser, ordersForUser);
        boolean popular = loggedUser != null &&
                (orderService.getByBuyerEmail(loggedUser.getEmail()).size() != 0);
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);
        mav.addObject("popular", popular);
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
