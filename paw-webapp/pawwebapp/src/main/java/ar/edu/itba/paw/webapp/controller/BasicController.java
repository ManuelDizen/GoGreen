package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.FAQService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BasicController {

    // Leave the constructor, as it prevents dependency injection (https://stackoverflow.com/questions/40620000/spring-autowire-on-properties-vs-constructor)

    private final UserService us;

    private final FAQService faqService;

    private final ProductService ps;

    @Autowired
    public BasicController(final UserService us, final FAQService faqService, final ProductService ps) {
        this.us = us;
        this.faqService = faqService;
        this.ps = ps;
    }

    @RequestMapping("/")
    public ModelAndView helloWorld() {
        List<Product> recent = ps.getRecent(3);
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("recent", recent);
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
