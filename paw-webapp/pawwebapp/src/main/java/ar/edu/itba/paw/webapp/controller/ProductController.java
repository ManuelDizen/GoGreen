package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.webapp.form.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService ps;

    private final SellerService sellerService;

    private final EmailService es;

    @Autowired
    public ProductController(final ProductService ps, final SellerService sellerService, final EmailService es){
        this.ps = ps;
        this.sellerService = sellerService;
        this.es = es;
    }

    @RequestMapping("/explore")
    public ModelAndView exploreProducts(){
        final ModelAndView mav = new ModelAndView("explore");
        final List<Product> products = ps.getAll();
        mav.addObject("products", products);
        return mav;
    }

    /* TODO: Discutir sobre esta solución (doble boolean). Explicación para acordarme
    cuando lo charlemos: 3 estados posibles, consulta de página, form falló, form exitoso.
    No me alcanza con un solo boolean, pero siento que es ineficiente dos variables, aunque de
    momento no se me ocurre algo mejor.
     */
    @RequestMapping("/product/{productId:[0-9]+}")
    public ModelAndView productPage(
            @PathVariable("productId") final long productId,
            @Valid @ModelAttribute("orderForm") final OrderForm form,
            @RequestParam(name="formSuccess", defaultValue = "false") final boolean formSuccess,
            @RequestParam(name="formFailure", defaultValue = "false") final boolean formFailure){

        final ModelAndView mav = new ModelAndView("productPage");
        final Optional<Product> product = ps.getById(productId);
        if(!product.isPresent()) throw new RuntimeException("Product not found");
        final Product productObj = product.get();
        mav.addObject("product", productObj);

        final Optional<Seller> seller = sellerService.findById(productObj.getSellerId());
        if(!seller.isPresent()) throw new RuntimeException("Seller not found");
        //Should never have that exception, the product exists and sellerID is FK...
        mav.addObject("seller", seller.get());
        mav.addObject("formSuccess", formSuccess);
        mav.addObject("formFailure", formFailure);
        return mav;
    }

    @RequestMapping(value="/process/{prodId}", method = {RequestMethod.POST})
    public ModelAndView process(@PathVariable final long prodId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                final BindingResult errors){
        if(errors.hasErrors()){
            /*List<FieldError> errorsAux = errors.getFieldErrors();
            List<String> errorMsgs = new ArrayList<String>();
            for(FieldError error : errorsAux){
                errorMsgs.add(error.getObjectName() + " - " + error.getDefaultMessage());
            }*/
            return productPage(prodId, form, false, true);
        }
        final Optional<Product> product = ps.getById(prodId);
        if(product.isPresent()) {
            final Optional<Seller> seller = sellerService.findById(product.get().getSellerId());
            if(seller.isPresent()) {
                final Seller s = seller.get();
                es.purchase(form.getMail(), form.getName(), product.get(), form.getAmount(),
                        product.get().getPrice(), s.getName(), s.getPhone(), s.getMail());

                es.itemsold(s.getMail(), s.getName(), product.get(),
                        form.getAmount(), product.get().getPrice(),
                        form.getName(), form.getMail(), form.getPhone());
            }
        }
        System.out.println("mail sent");

        final ModelAndView mav = new ModelAndView("redirect:/product/" + prodId);
        mav.addObject("product", ps.getById(prodId).orElseThrow(ProductNotFoundException::new));
        mav.addObject("formSuccess", true);
        return mav;
    }

    @RequestMapping(value="/filter", method=RequestMethod.POST)
    public ModelAndView filter(){
        final ModelAndView mav = new ModelAndView("explore");
        return mav;
    }



}
