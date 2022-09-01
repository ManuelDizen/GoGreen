package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    private final ProductService ps;

    @Autowired
    public ProductController(final ProductService ps){
        this.ps = ps;
    }

    @RequestMapping("/explore")
    public ModelAndView exploreProducts(){
        final ModelAndView mav = new ModelAndView("explore");
        final List<Product> products = ps.getAll();
        mav.addObject("products", products);
        return mav;
    }
}
