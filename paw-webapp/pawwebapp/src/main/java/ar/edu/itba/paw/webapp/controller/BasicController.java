package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EcotagService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Category;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class BasicController {

    private final ProductService productService;

    @Autowired
    public BasicController(final ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/")
    public ModelAndView landingPage() {
        List<Product> recent = productService.getRecent(3);
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("recent", recent);
        mav.addObject("categories", Category.values());
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
