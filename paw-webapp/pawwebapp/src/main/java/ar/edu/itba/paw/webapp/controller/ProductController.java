package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import ar.edu.itba.paw.webapp.form.UpdateProdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import java.util.*;

import static java.lang.Integer.parseInt;


@Controller
public class ProductController {
    private final ProductService productService;

    private final SellerService sellerService;

    private final EcotagService ecotagService;

    private final OrderService orderService;

    
    @Autowired
    public ProductController(final ProductService productService, final SellerService sellerService,
                             final UserService userService, final SecurityService securityService,
                             final EcotagService ecotagService, final OrderService orderService) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.ecotagService = ecotagService;
        this.orderService = orderService;
    }

    @RequestMapping(value="/explore")
    public ModelAndView exploreProducts(
            @RequestParam(name="name", defaultValue="") final String name,
            @RequestParam(name="category", defaultValue="0") final long category,
            @RequestParam(name="strings", defaultValue = "null") final String[] strings,
            @RequestParam(name="maxPrice", defaultValue = "-1") final Integer maxPrice,
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="sort", defaultValue = "0") final int sort,
            @RequestParam(name="direction", defaultValue = "1") final int direction
    ) {
        final ModelAndView mav = new ModelAndView("explore");

        //Ecotag management

        mav.addObject("ecoStrings", new String[]{"1", "2", "3", "4", "5"});
        mav.addObject("path", productService.buildPath(strings));

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

        List<Product> productList = productService.filter(name, category, tagsToFilter, maxPrice);
        List<Product> allProducts = productService.getAvailable();

        mav.addObject("isEmpty", allProducts.isEmpty());


        for(Product product : productList) {
            product.setTagList(ecotagService.getTagFromProduct(product.getProductId()));
        }

        //Sorting

        mav.addObject("sort", sort);
        mav.addObject("direction", direction);

        productService.sortProducts(productList, sort, direction);

        mav.addObject("sortName", Sort.getById(sort).getName());
        mav.addObject("sorting", Sort.values());

        //Pagination

        List<List<Product>> productPages = productService.divideIntoPages(productList);

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

    @RequestMapping(value = "/deleteProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long productId){
        Boolean bool = productService.attemptDelete(productId);
        if(!bool) throw new IllegalStateException();
        return new ModelAndView("redirect:/sellerProfile");
    }

    @RequestMapping("/product/{productId:[0-9]+}")
    public ModelAndView productPage(
            @PathVariable("productId") final long productId,
            @Valid @ModelAttribute("orderForm") final OrderForm form,
            @RequestParam(name="formFailure", defaultValue = "false") final boolean formFailure){

        final ModelAndView mav = new ModelAndView("productPage");
        final Optional<Product> product = productService.getById(productId);

        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product productObj = product.get();

        if(productObj.getStock() == 0){
            return new ModelAndView("redirect:/404");
        }

        mav.addObject("product", productObj);

        final Optional<Seller> seller = sellerService.findById(productObj.getSellerId());
        if(!seller.isPresent()) throw new RuntimeException("Seller not found");

        List<Ecotag> ecotags = ecotagService.getTagFromProduct(productObj.getProductId());
        mav.addObject("seller", seller.get());
        mav.addObject("formFailure", formFailure);
        mav.addObject("ecotags", ecotags);
        return mav;
    }

    @RequestMapping(value="/process/{productId}", method = {RequestMethod.POST})
    public ModelAndView process(@PathVariable final long productId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                final BindingResult errors){
        if(errors.hasErrors() || form.getAmount() == null){
            return productPage(productId, form, true);
        }

        Boolean created = orderService.createAndNotify(productId, form.getAmount(), form.getMessage());
        if(!created) throw new IllegalStateException();

        final ModelAndView mav = new ModelAndView("redirect:/userProfile/true#orders");
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
        if (errors.hasErrors()) return createProduct(form);

        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {throw new RuntimeException(e);}

        Product product = productService.createProduct(Integer.parseInt(form.getStock()), Integer.parseInt(form.getPrice()),
                form.getCategory(), form.getName(), form.getDescription(), image, form.getEcotag());
        if(product == null) throw new IllegalStateException();

        return new ModelAndView("redirect:/product/" + product.getProductId());
    }

    @RequestMapping(value="/updateProduct/{productId:[0-9]+}", method=RequestMethod.GET)
    public ModelAndView updateProduct(
            @PathVariable("productId") final long productId,
            @ModelAttribute("updateProdForm") final UpdateProdForm form
    ){
        Boolean isOwner = productService.checkForOwnership(productId);
        if(!isOwner) throw new IllegalStateException();

        Optional<Product> product = productService.getById(productId);
        if(!product.isPresent()) throw new IllegalStateException();

        ModelAndView mav = new ModelAndView("/updateProduct");
        mav.addObject("product", product.get());
        return mav;
    }

    @RequestMapping(value="/updateProduct/{productId:[0-9]+}", method=RequestMethod.POST)
    public ModelAndView updateProductPost(
            @PathVariable("productId") final long productId,
            @Valid @ModelAttribute("updateProdForm") final UpdateProdForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            return updateProduct(productId, form);
        }
        Boolean success = productService.updateProduct(productId, form.getNewStock(), form.getNewPrice());
        if(!success) throw new IllegalStateException();

        return new ModelAndView("redirect:/sellerProfile");
    }

}
