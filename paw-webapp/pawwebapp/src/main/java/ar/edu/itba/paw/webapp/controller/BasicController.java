package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.OrderService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.SecurityService;
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
    private final SecurityService securityService;

    private final OrderService orderService;

    @Autowired
    public BasicController(final ProductService productService, final SecurityService securityService, final OrderService orderService) {
        this.productService = productService;
        this.securityService = securityService;
        this.orderService = orderService;
    }

    @RequestMapping("/")
    public ModelAndView landingPage() {
        //TODO: Pass all this logic onto a service
        User loggedUser = securityService.getLoggedUser();
        List<Order> ordersForUser = new ArrayList<>();
        if(loggedUser!=null) ordersForUser = orderService.getByBuyerEmail(loggedUser.getEmail());
        List<Product> products = productService.getLandingProducts(loggedUser, ordersForUser);
        boolean popular = loggedUser != null &&
                (orderService.getByBuyerEmail(loggedUser.getEmail()).size() != 0);
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);
        mav.addObject("popular", popular);
        //mav.addObject("products", new ArrayList<>());
        //mav.addObject("popular", true);
        mav.addObject("categories", Category.values());
        //mav.addObject("productsPerCategory", productService.productsPerCategory());
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
