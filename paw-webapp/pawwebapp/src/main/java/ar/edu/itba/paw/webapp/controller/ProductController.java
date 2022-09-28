package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import ar.edu.itba.paw.webapp.form.StockForm;
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

import static java.lang.Integer.parseInt;


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
            @RequestParam(name="category", defaultValue="0") final long category,
            @RequestParam(name="strings", defaultValue = "null") final String[] strings,
            @RequestParam(name="maxPrice", defaultValue = "-1.0") final float maxPrice,
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="sort", defaultValue = "0") final int sort,
            @RequestParam(name="direction", defaultValue = "1") final int direction
    ) {
        final ModelAndView mav = new ModelAndView("explore");

        //Ecotag management

        mav.addObject("ecoStrings", new String[]{"1", "2", "3", "4", "5"});
        mav.addObject("path", ps.buildPath(strings));

        final boolean[] boolTags = new boolean[Ecotag.values().length];

        List<Ecotag> tagsToFilter = new ArrayList<>();

        if(!strings[0].equals("null")) {
            for(String s : strings) {
                tagsToFilter.add(Ecotag.getById(parseInt(s)));
                boolTags[parseInt(s)-1] = true;
            }
        }

        List<Ecotag> ecotagList = Arrays.asList(Ecotag.values());

        mav.addObject("ecotagList", ecotagList);
        mav.addObject("boolTags", boolTags);

        //Product filter

        List<Product> productList = ps.filter(name, category, tagsToFilter, maxPrice);
        List<Product> allProducts = ps.getAvailable();

        mav.addObject("isEmpty", allProducts.isEmpty());


        for(Product product : productList) {
            product.setTagList(ecos.getTagFromProduct(product.getProductId()));
        }

        //Sorting

        mav.addObject("sort", sort);
        mav.addObject("direction", direction);

        ps.sortProducts(productList, sort, direction);

        mav.addObject("sortName", Sort.getById(sort).getName());
        mav.addObject("sorting", Sort.values());

        //Pagination

        List<List<Product>> productPages = ps.divideIntoPages(productList);

        mav.addObject("currentPage", page);
        if(productPages.size() != 0)
            mav.addObject("products", productPages.get(page-1));
        else
            mav.addObject("products", new ArrayList<>());

        mav.addObject("pages", productPages);

        //Parameters for filter
        mav.addObject("name", name);
        mav.addObject("categories", Category.values());
        mav.addObject("chosenCategory", category);
        if(maxPrice > -1.0)
            mav.addObject("maxPrice", maxPrice);
        else
            mav.addObject("maxPrice", null);

        return mav;
    }

    @RequestMapping(value = "/deleteProduct/{prodId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long prodId){
        Boolean bool = ps.attemptDelete(prodId);
        if(!bool) throw new IllegalStateException();
        ModelAndView mav = new ModelAndView("redirect:/sellerProfile");
        return mav;
    }

    @RequestMapping("/product/{productId:[0-9]+}")
    public ModelAndView productPage(
            @PathVariable("productId") final long productId,
            @Valid @ModelAttribute("orderForm") final OrderForm form,
            @RequestParam(name="formFailure", defaultValue = "false") final boolean formFailure){

        final ModelAndView mav = new ModelAndView("productPage");
        final Optional<Product> product = ps.getById(productId);

        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product productObj = product.get();

        if(productObj.getStock() == 0){
            return new ModelAndView("redirect:/404");
        }

        mav.addObject("product", productObj);

        final Optional<Seller> seller = sellerService.findById(productObj.getSellerId());
        if(!seller.isPresent()) throw new RuntimeException("Seller not found");
        //Should never have that exception, the product exists and sellerID is FK

        List<Ecotag> ecotags = ecos.getTagFromProduct(productObj.getProductId());
        mav.addObject("seller", seller.get());
        mav.addObject("formFailure", formFailure);
        mav.addObject("ecotags", ecotags);
        return mav;
    }

    @RequestMapping(value="/process/{prodId}", method = {RequestMethod.POST})
    public ModelAndView process(@PathVariable final long prodId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                final BindingResult errors){
        if(errors.hasErrors() || form.getAmount() == null){
            return productPage(prodId, form, true);
        }
        final Optional<Product> product = ps.getById(prodId);
        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product p = product.get();

        Boolean enough = ps.checkForAvailableStock(p, form.getAmount());
        if(!enough){
            errors.addError(new ObjectError("amount",
                    "El stock disponible es insuficiente para su pedido. Por favor, reviselo" +
                            "e intente nuevamente"));
            return productPage(prodId, form, true);
        }

        final Optional<User> user = us.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No hay un usuario loggeado");
        final User u = user.get();

        final Optional<Seller> seller = sellerService.findById(product.get().getSellerId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");
        final Seller s = seller.get();

        Boolean created = os.createAndNotify(p, u, s, form.getAmount(), form.getMessage());
        if(!created) throw new IllegalStateException();

        final ModelAndView mav = new ModelAndView("redirect:/userProfile#test2");
        return mav;
    }

    @RequestMapping(value="/createProduct", method=RequestMethod.GET)
    public ModelAndView createProduct(@ModelAttribute("productForm") final ProductForm form) {
        List<Ecotag> tagList = Arrays.asList(Ecotag.values());
        final ModelAndView mav = new ModelAndView("createProducts");
        mav.addObject("tagList", tagList);
        mav.addObject("categories", Category.values());
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
