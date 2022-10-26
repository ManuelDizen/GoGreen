package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

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
        User loggedUser = securityService.getLoggedUser();
        List<Product> products;
        boolean popular = false;
        if(loggedUser == null) {
            products = productService.getPopular(4);
            popular = true;
        }
        else {
            List<Order> ordersForUser = orderService.getByBuyerEmail(loggedUser.getEmail());
            if(ordersForUser.isEmpty()){
                popular = true;
                products = productService.getPopular(4);
            } else {
                products = productService.getInterestingForUser(ordersForUser, 4);
            }
        }
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", products);
        mav.addObject("popular", popular);
        mav.addObject("categories", Category.values());
        mav.addObject("productsPerCategory", productService.productsPerCategory());
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("404");
    }

}
