package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Ecotag;
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
import java.util.List;
import java.time.LocalDateTime;
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

    private final SecurityService securityService;

    private final OrderService os;

    
    @Autowired
    public ProductController(final ProductService ps, final SellerService sellerService,
                             final EmailService es, final ImageService is, final UserService us,
                             final SecurityService securityService, EcotagService ecos, final OrderService os) {
        this.ps = ps;
        this.sellerService = sellerService;
        this.es = es;
        this.is = is;
        this.us = us;
        this.securityService = securityService;
        this.ecos = ecos;
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

        final boolean[] boolTags = new boolean[]{ecotagRecycle, ecotagForest, ecotagEnergy};

        List<Ecotag> tagsToFilter = ecos.filterByTags(boolTags);

        //TODO: mejorar forma de filtrar por ecotags
        //TODO: filtro por categorías

        List<Product> productList = ps.filter(name, category, tagsToFilter, maxPrice);
        List<Product> allProducts = ps.getAll();

        List<List<Ecotag>> productTags = new ArrayList<>();
        for(Product product : productList) {
            product.setTagList(ecos.getTagFromProduct(product.getProductId()));
        }

        List<Ecotag> ecotagList = Arrays.asList(Ecotag.values());

        mav.addObject("boolTags", boolTags);
        mav.addObject("name", name);
        mav.addObject("category", category);
        if(maxPrice > -1.0)
            mav.addObject("maxPrice", maxPrice);
        else
            mav.addObject("maxPrice", null);

        mav.addObject("ecotagList", ecotagList);
        mav.addObject("products", productList);
        mav.addObject("isEmpty", allProducts.isEmpty());

        return mav;
    }

    /* TODO: Discutir sobre esta solución (doble boolean). Explicación para acordarme
        cuando lo charlemos: 3 estados posibles, consulta de página, form falló, form exitoso.
        No me alcanza con un solo boolean, pero siento que es ineficiente dos variables, aunque de
        momento no se me ocurre algo mejor.
     */

    @RequestMapping(value = "/deleteProduct/{prodId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long prodId){

        /* We need to validate that the product is in fact ownership of the
        logged user. If not, throwing a random /deleteProduct/{prodId} would
        be enough to destroy the application
         */
        Boolean bool = ps.attemptDelete(prodId);

        ModelAndView mav = new ModelAndView("redirect:/sellerProfile");
        return mav;
    }

    @RequestMapping("/product/{productId:[0-9]+}")
    public ModelAndView productPage(
            @PathVariable("productId") final long productId,
            @Valid @ModelAttribute("orderForm") final OrderForm form,
            @RequestParam(name="formSuccess", defaultValue = "false") final boolean formSuccess,
            @RequestParam(name="formFailure", defaultValue = "false") final boolean formFailure){

        final ModelAndView mav = new ModelAndView("productPage");
        final Optional<Product> product = ps.getById(productId);

        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product productObj = product.get();
        mav.addObject("product", productObj);

        final Optional<Seller> seller = sellerService.findById(productObj.getSellerId());
        if(!seller.isPresent()) throw new RuntimeException("Seller not found");
        //Should never have that exception, the product exists and sellerID is FK

        List<Ecotag> ecotags = ecos.getTagFromProduct(productObj.getProductId());
        mav.addObject("seller", seller.get());
        mav.addObject("formSuccess", formSuccess);
        mav.addObject("formFailure", formFailure);
        mav.addObject("ecotags", ecotags);
        return mav;
    }

    @RequestMapping(value="/process/{prodId}", method = {RequestMethod.POST})
    public ModelAndView process(@PathVariable final long prodId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                final BindingResult errors){

        if(errors.hasErrors()){
            return productPage(prodId, form, false, true);
        }

        final Optional<Product> product = ps.getById(prodId);
        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product p = product.get();

        final Optional<User> user = us.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No hay un usuario loggeado");
        final User u = user.get();

        final Optional<Seller> seller = sellerService.findById(product.get().getSellerId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");
        final Seller s = seller.get();

        os.createAndNotify(p, u, s, form.getAmount(), form.getMessage());

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
            final BindingResult errors) {
        if (errors.hasErrors())
            return createProduct(form);
        //llamada al backend
        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO: Category is hardcoded. Discuss.

        Optional<User> user = us.findByEmail(securityService.getLoggedEmail());
        if (!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByUserId(user.get().getId());
        if (!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        Product product = ps.create(seller.get().getId(),
                1, form.getName(), form.getDescription(),
                form.getStock(), form.getPrice(), image);

        for (long id : form.getEcotag()) {
            ecos.addTag(Ecotag.getById(id), product.getProductId());
        }

        return new ModelAndView("redirect:/product/" + product.getProductId());
    }



}
