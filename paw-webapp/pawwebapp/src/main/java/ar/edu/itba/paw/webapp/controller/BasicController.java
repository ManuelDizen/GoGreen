package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EcotagService;
import ar.edu.itba.paw.interfaces.services.FAQService;
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

    // Leave the constructor, as it prevents dependency injection (https://stackoverflow.com/questions/40620000/spring-autowire-on-properties-vs-constructor)

    private final UserService userService;

    private final FAQService faqService;

    private final ProductService productService;
    private final EcotagService ecotagService;

    @Autowired
    public BasicController(final UserService userService, final FAQService faqService, final ProductService productService, EcotagService ecotagService) {
        this.userService = userService;
        this.faqService = faqService;
        this.productService = productService;
        this.ecotagService = ecotagService;
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
    public ModelAndView login(HttpServletRequest request){
        final ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping(value="/faq")
    public ModelAndView faq(){
        final ModelAndView mav = new ModelAndView("FAQ");
        mav.addObject("faqs", faqService.getFAQs());
        return mav;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("404");
    }

}
