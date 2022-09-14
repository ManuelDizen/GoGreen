package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.FAQService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FAQController {

    private final FAQService faqService;

    @Autowired
    public FAQController(final FAQService faqService){
        this.faqService = faqService;
    }
    @RequestMapping(value="/faq")
    public ModelAndView faq(){
        final ModelAndView mav = new ModelAndView("FAQ");
        mav.addObject("faqs", faqService.getFAQs());
        return mav;
    }
}
