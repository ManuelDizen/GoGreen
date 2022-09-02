package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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

    @RequestMapping("/product/{productId:[0-9]+}")
    public ModelAndView productPage(@PathVariable("productId") final long productId){
        final ModelAndView mav = new ModelAndView("productPage");
        final Optional<Product> product = ps.getById(productId);
        if(!product.isPresent()) throw new RuntimeException("Product not found");
        final Product productObj = product.get();
        mav.addObject("product", productObj);

        //TODO: Preguntar si está bien consultar los getters de los models
        final Optional<Seller> seller = ps.getProductSeller(productObj.getSellerId());
        if(!seller.isPresent()) throw new RuntimeException("Seller not found");
        //Should never have that exception, the product exists and sellerID is FK...
        mav.addObject("seller", seller.get());

        return mav;
    }
}
