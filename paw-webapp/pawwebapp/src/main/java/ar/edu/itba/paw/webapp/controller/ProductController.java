package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import java.util.*;


@Controller
public class ProductController {

    private final ProductService ps;

    private final SellerService sellerService;

    private final EmailService es;

    private final ImageService is;

    private final UserService us;

    private final EcotagService ecos;

    private final AuthenticationController authController;

    private final OrderService os;




    @Autowired
    public ProductController(final ProductService ps, final SellerService sellerService,
                             final EmailService es, final ImageService is, final UserService us, final EcotagService ecos, AuthenticationController authController, OrderService os){
        this.ps = ps;
        this.sellerService = sellerService;
        this.es = es;
        this.is = is;
        this.us = us;
        this.ecos = ecos;
        this.authController = authController;
        this.os = os;
    }

    @RequestMapping(value="/explore")
    public ModelAndView exploreProducts(
            @RequestParam(name="name", defaultValue="") final String name,
            @RequestParam(name="category", defaultValue="") final String category,
            @RequestParam(name="ecotagRecycle", defaultValue="false") final boolean ecotagRecycle,
            @RequestParam(name="ecotagForest", defaultValue="false") final boolean ecotagForest,
            @RequestParam(name="ecotagEnergy", defaultValue="false") final boolean ecotagEnergy,
            @RequestParam(name="maxPrice", defaultValue = "-1.0") final float maxPrice
    ){
        final ModelAndView mav = new ModelAndView("explore");

        List<Ecotag> tagsToFilter = ecos.filterByTags(new boolean[]{ecotagRecycle, ecotagForest, ecotagEnergy});

        List<Product> productList = ps.filter(name, category, tagsToFilter, maxPrice);

        List<List<Ecotag>> productTags = new ArrayList<>();
        for(Product product : productList) {
            product.setTagList(ecos.getTagFromProduct(product.getProductId()));
        }

        List<Ecotag> ecotagList = Arrays.asList(Ecotag.values());

        mav.addObject("ecotagList", ecotagList);
        mav.addObject("products", productList);
        mav.addObject("isEmpty", productList.isEmpty());

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

        //TODO ojo esta excepción
        //if(!product.isPresent()) throw new ProductNotFoundException();
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

        //TODO: Change ALL this logic to service.

        if(errors.hasErrors()){
            return productPage(prodId, form, false, true);
        }
        final Optional<Product> product = ps.getById(prodId);
        //TODO ojo esta excepción
        //if(!product.isPresent()) throw new ProductNotFoundException();

        final Product p = product.get();

        final Optional<User> user = us.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No hay un usuario loggeado");

        final User u = user.get();

        final Optional<Seller> seller = sellerService.findById(product.get().getSellerId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        final Seller s = seller.get();

        es.purchase(u.getEmail(), u.getFirstName(),
                p, form.getAmount(),
                p.getPrice(), sellerService.getName(s.getUserId()),
                s.getPhone(), sellerService.getEmail(s.getUserId()));

        es.itemsold(sellerService.getEmail(s.getUserId()), sellerService.getName(s.getUserId()),
                p,
                form.getAmount(), p.getPrice(),
                u.getFirstName(), u.getEmail(),
                form.getMessage());

        LocalDateTime dateTime = LocalDateTime.now();

        Order order = os.create(p.getName(), u.getFirstName(),
                u.getSurname(), u.getEmail(), sellerService.getName(s.getUserId()),
                sellerService.getSurname(s.getUserId()),
        sellerService.getEmail(s.getUserId()), form.getAmount(), p.getPrice(), dateTime);

        if(order == null) throw new IllegalStateException("No se instanció la orden");

        final ModelAndView mav = new ModelAndView("redirect:/product/" + prodId);
        mav.addObject("formSuccess", true);
        return mav;
    }

    @RequestMapping(value="/createProduct", method=RequestMethod.GET)
    public ModelAndView createProduct(@ModelAttribute("productForm") final ProductForm form) {
        List<Ecotag> tagList = Arrays.asList(Ecotag.values());
        final ModelAndView mav = new ModelAndView("createProducts");
        mav.addObject("tagList", tagList);
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

        // UPDATE: Category is hardcoded. Discuss.

        Optional<User> user = us.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByUserId(user.get().getId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        Product product = ps.create(seller.get().getId(),
                1, form.getName(), form.getDescription(),
                form.getStock(), form.getPrice(), image);

        for(long id : form.getEcotag()) {
            ecos.addTag(Ecotag.getById(id), product.getProductId());
        }

        return new ModelAndView("redirect:/product/" + product.getProductId());
    }



}
