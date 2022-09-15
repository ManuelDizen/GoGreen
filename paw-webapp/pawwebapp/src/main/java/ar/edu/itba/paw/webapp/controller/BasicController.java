package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.FAQService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BasicController {

    private final UserService us;
    private final FAQService faqService;

    @Autowired
    public BasicController(final UserService us, FAQService faqService){
        this.us = us;
        this.faqService = faqService;
    }

    //    @RequestMapping(value = "/", method = RequestMethod.GET, headers = ..., consumes = ..., produces = ...)
    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
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
