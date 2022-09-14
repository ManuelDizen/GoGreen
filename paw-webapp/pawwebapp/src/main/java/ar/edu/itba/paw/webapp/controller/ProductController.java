package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductService ps;

    private final SellerService sellerService;

    private final EmailService es;

    private final ImageService is;

    private final UserService us;

    private final AuthenticationController authController;



    @Autowired
    public ProductController(final ProductService ps, final SellerService sellerService,
                             final EmailService es, final ImageService is, UserService us, AuthenticationController authController){
        this.ps = ps;
        this.sellerService = sellerService;
        this.es = es;
        this.is = is;
        this.us = us;
        this.authController = authController;
    }

    @RequestMapping(value="/explore")
    public ModelAndView exploreProducts(
            @RequestParam(name="name", defaultValue="") final String name,
            @RequestParam(name="category", defaultValue="") final String category,
            @RequestParam(name="maxPrice", defaultValue = "-1.0") final float maxPrice
    ){
        final ModelAndView mav = new ModelAndView("explore");
        mav.addObject("products", ps.filter(name, category, maxPrice));
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
                        product.get().getPrice(), sellerService.getName(s.getUserId()), s.getPhone(), sellerService.getEmail(s.getUserId()));

                es.itemsold(sellerService.getEmail(s.getUserId()), sellerService.getName(s.getUserId()), product.get(),
                        form.getAmount(), product.get().getPrice(),
                        form.getName(), form.getMail(), form.getPhone(), form.getMessage());
            }
        }
        System.out.println("mail sent");

        final ModelAndView mav = new ModelAndView("redirect:/product/" + prodId);
        //mav.addObject("product", ps.getById(prodId).orElseThrow(ProductNotFoundException::new));
        mav.addObject("formSuccess", true);
        return mav;
    }

    @RequestMapping(value="/createProduct", method=RequestMethod.GET)
    public ModelAndView createProduct(@ModelAttribute("productForm") final ProductForm form) {
        final ModelAndView mav = new ModelAndView("createProducts");
        return mav;
    }

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST)
    public ModelAndView createProductPost(
            @Valid @ModelAttribute("productForm") final ProductForm form,
            final BindingResult errors){
        if(errors.hasErrors())
            return createProduct(form);
        //llamada al backend
        byte[] image;
        try {
            image = form.getImage().getBytes();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //TODO: Hardcoded sellerId. Should retrieve from session.
        //  THis view must only be accessed by users with SELLER role
        // UPDATE: sellerId now parametrized to logged user, see what to do with category

        Optional<User> user = us.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByUserId(user.get().getId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        Product product = ps.create(seller.get().getId(),
                1, form.getName(), form.getDescription(),
                form.getStock(), form.getPrice(), image);
        return new ModelAndView("redirect:/product/" + product.getProductId());
    }



}
