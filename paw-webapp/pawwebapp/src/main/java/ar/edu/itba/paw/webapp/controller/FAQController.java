package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FAQController {
    @RequestMapping(value="/faq")
    public ModelAndView faq(){
        final ModelAndView mav = new ModelAndView("FAQ");
        //mav.addObject("products", productService.filter(name, category, maxPrice));
        return mav;
    }
}
