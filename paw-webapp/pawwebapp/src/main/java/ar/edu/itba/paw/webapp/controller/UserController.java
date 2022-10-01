package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.StockForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final SellerService sellerService;

    private final SecurityService securityService;

    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public UserController(final UserService userService, final SellerService sellerService,
                          final SecurityService securityService, final OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.securityService = securityService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @RequestMapping(value="profile")
    public ModelAndView profile(){
        User user = securityService.getLoggedUser();
        if(user == null){
            throw new IllegalStateException("Only logged users can access their profile");
        }
        if(sellerService.findByMail(user.getEmail()).isPresent()){
            // It's a seller
            return new ModelAndView("redirect:/sellerProfile#information");
        }
        return new ModelAndView("redirect:/userProfile/false#information");
    }


    @RequestMapping(value="/userProfile/{fromSale}")
    public ModelAndView buyerProfile(
            @RequestParam(name="page", defaultValue = "1") final int page,
            @PathVariable("fromSale") final boolean fromSale){
        final ModelAndView mav = new ModelAndView("userProfile");
        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("no lo ovaf dkfds");
        mav.addObject("user", user.get());

        List<Order> orders = orderService.getByBuyerEmail(user.get().getEmail());

        List<List<Order>> orderPages = orderService.divideIntoPages(orders);

        mav.addObject("currentPage", page);
        mav.addObject("pages", orderPages);
        mav.addObject("orders", orderPages.get(page-1));
        mav.addObject("fromSale", fromSale);
        return mav;
    }

    @RequestMapping(value="/sellerProfile")
    public ModelAndView sellerProfile(@RequestParam(name="pageP", defaultValue="1") final int pageP,
                                      @RequestParam(name="pageO", defaultValue="1") final int pageO,
                                      @ModelAttribute("stockForm") final StockForm form){
        final ModelAndView mav = new ModelAndView("sellerProfile");

        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByMail(user.get().getEmail());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        List<Order> orders = orderService.getBySellerEmail(user.get().getEmail());
        List<List<Order>> orderPages = orderService.divideIntoPages(orders);
        List<Product> products = productService.findBySeller(seller.get().getId());
        List<List<Product>> productPages = productService.divideIntoPages(products);

        // TODO: Acá faltaría además buscar los productos que vende un seller,
        //  y las órdenes que tiene pendientes (para esto hay que agrandar la BDD)

        mav.addObject("seller", seller.get());
        mav.addObject("user", user.get());
        mav.addObject("currentPageP", pageP);
        mav.addObject("currentPageO", pageO);
        mav.addObject("productPages", productPages);
        mav.addObject("orderPages", orderPages);
        mav.addObject("orders", orderPages.get(pageO-1));
        mav.addObject("products", productPages.get(pageP-1));
        return mav;
    }

    @RequestMapping(value="/sellerProfile/products", method= RequestMethod.GET)
    public ModelAndView sellerProducts(){
        final ModelAndView mav = new ModelAndView("/sellerProducts");
        return mav;
    }

    @RequestMapping(value="/updateStock/{prodId:[0-9]+}", method=RequestMethod.POST)
    public ModelAndView updateStock(
            @PathVariable("prodId") final long prodId,
            @Valid @ModelAttribute("stockForm") final StockForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            //TODO: Display form errors,
            // QUE EL REDIRECT NO DEJE EL LINK DE UPDATESTOCK!!!
            // Dos soluciones viables: (1) hacer que updateStock en realidad sea un
            // updateProduct y sea una vista de verdad
            // (2) que updateStock mande de vuetla a sellerProfile con algún boolean de failure
            return sellerProfile(1,1,form);
        }

        //int newStock = parseInt(form.getNewStock());

        Boolean success = productService.attemptUpdate(prodId, form.getNewStock());
        if(!success) throw new IllegalStateException("Stock update could not go through");
        return new ModelAndView("redirect:/sellerProfile");
    }

}
