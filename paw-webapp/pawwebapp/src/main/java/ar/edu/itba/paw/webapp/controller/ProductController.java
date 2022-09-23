package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

    private final CategoryService cs;

    
    @Autowired
    public ProductController(final ProductService ps, final SellerService sellerService,
                             final EmailService es, final ImageService is, final UserService us,
                             final SecurityService securityService, EcotagService ecos, final OrderService os, final CategoryService cs) {
        this.ps = ps;
        this.sellerService = sellerService;
        this.es = es;
        this.is = is;
        this.us = us;
        this.securityService = securityService;
        this.ecos = ecos;
        this.os = os;
        this.cs = cs;
    }


    @RequestMapping(value="/explore")
    public ModelAndView exploreProducts(
            @RequestParam(name="name", defaultValue="") final String name,
            @RequestParam(name="category", defaultValue="0") final long category,
            @RequestParam(name="ecotagRecycle", defaultValue="false") final boolean ecotagRecycle,
            @RequestParam(name="ecotagForest", defaultValue="false") final boolean ecotagForest,
            @RequestParam(name="ecotagEnergy", defaultValue="false") final boolean ecotagEnergy,
            @RequestParam(name="ecotagAnimals", defaultValue="false") final boolean ecotagAnimals,
            @RequestParam(name="ecotagTransport", defaultValue="false") final boolean ecotagTransport,
            @RequestParam(name="maxPrice", defaultValue = "-1.0") final float maxPrice,
            @RequestParam(name="page", defaultValue = "1") final int page
    ){
        final ModelAndView mav = new ModelAndView("explore");

        final boolean[] boolTags = new boolean[]{ecotagRecycle, ecotagForest, ecotagEnergy, ecotagAnimals, ecotagTransport};

        List<Ecotag> tagsToFilter = ecos.filterByTags(boolTags);

        //TODO: mejorar forma de filtrar por ecotags
        //TODO: filtro por categorías -> hecho, pero hay un bug de materialize (revisar)

        List<Product> productList = ps.filter(name, category, tagsToFilter, maxPrice);
        List<Product> allProducts = ps.getAll();

        for(Product product : productList) {
            product.setTagList(ecos.getTagFromProduct(product.getProductId()));
        }

        List<Category> categories = cs.getAllCategories();

        List<List<Product>> productPages = ps.divideIntoPages(productList);

        List<Ecotag> ecotagList = Arrays.asList(Ecotag.values());


        mav.addObject("currentPage", page);
        mav.addObject("boolTags", boolTags);
        mav.addObject("name", name);
        mav.addObject("categories", categories);
        mav.addObject("chosenCategory", category);
        if(maxPrice > -1.0)
            mav.addObject("maxPrice", maxPrice);
        else
            mav.addObject("maxPrice", null);

        mav.addObject("ecotagList", ecotagList);
        if(productPages.size() != 0)
            mav.addObject("products", productPages.get(page-1));
        else
            mav.addObject("products", new ArrayList<>());

        mav.addObject("isEmpty", allProducts.isEmpty());

        mav.addObject("pages", productPages);

        mav.addObject("selected", true);

        return mav;
    }

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

    /*@RequestMapping(value="/editProduct/{prodId:[0-9]+")
    public ModelAndView editProduct(@PathVariable final long prodId){
        List<Product> recent = ps.getRecent(3);
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("recent", recent);
        return mav;
    }

    @RequestMapping("/editProduct/{prodId:[0-9]+}")
    public ModelAndView editProduct(@PathVariable final long prodId,
                                    @Valid @ModelAttribute("productForm") final ProductForm form,
                                    final BindingResult errors){
        Boolean isOwner = ps.checkForOwnership(prodId);
        if(!isOwner) return new ModelAndView("redirect:/");
        /*
        On the method "checkForOwnership": As with deleting, it is important to check that
        it is in fact the logged user who is attempting to impact the DB and not a random user
        who noticed how to play with the links. That is why the method is needed.

        On a side note, one should never be able to edit a product that is not of their
        ownership by browsing unless they type in the URL, which would lead to them being redirected
        to homepage.

        Optional<Product> product = ps.getById(prodId);
        if(!product.isPresent()) throw new IllegalStateException();
        ModelAndView mav = new ModelAndView("editProduct");
        List<Ecotag> tagList = Arrays.asList(Ecotag.values());
        mav.addObject("tagList", tagList);
        mav.addObject("product", product.get());
        System.out.println("No error in rendering");
        return mav;
    }*/

    /*@RequestMapping(value="/editProduct/{prodId:[0-9]+}", method = RequestMethod.POST)
    public ModelAndView editProduct(@PathVariable("prodId") final long prodId,
                                    @Valid @ModelAttribute("productForm") final ProductForm form,
                                    final BindingResult errors){
        if(errors.hasErrors()){
            return editProduct(prodId, form);
        }
        *//*Boolean worked = ps.attemptEdit(prodId, ...Insert parameters here...);
        if(!worked) throw new IllegalStateException();*//*

        ModelAndView mav = new ModelAndView("redirect:/sellerProfile");
        return mav;
    }*/

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

        /* TODO: Check how to workaroung in OrderForm the amount @NotNull annotation
            I tried setting it and app crashed
         */
        if(errors.hasErrors() || form.getAmount() == null){
            return productPage(prodId, form, false, true);
        }
        final Optional<Product> product = ps.getById(prodId);
        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product p = product.get();

        Boolean enough = ps.checkForAvailableStock(p, form.getAmount());
        if(!enough){
            errors.addError(new ObjectError("amount",
                    "El stock disponible es insuficiente para su pedido. Por favor, reviselo" +
                            "e intente nuevamente"));
            return productPage(prodId, form, false, true);
        }

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
        mav.addObject("categories", cs.getAllCategories());
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

        Optional<User> user = us.findByEmail(securityService.getLoggedEmail());
        if (!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByUserId(user.get().getId());
        if (!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        Product product = ps.create(seller.get().getId(),
                form.getCategory(), form.getName(), form.getDescription(),
                form.getStock(), form.getPrice(), image);

        for (long id : form.getEcotag()) {
            ecos.addTag(Ecotag.getById(id), product.getProductId());
        }

        return new ModelAndView("redirect:/product/" + product.getProductId());
    }



}
