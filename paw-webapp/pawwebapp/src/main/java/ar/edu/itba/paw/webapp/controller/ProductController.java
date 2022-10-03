package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import ar.edu.itba.paw.webapp.form.StockForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private final ProductService productService;

    private final SellerService sellerService;

    private final EmailService emailService;

    private final ImageService imageService;

    private final UserService userService;

    private final EcotagService ecotagService;

    private final SecurityService securityService;

    private final OrderService orderService;

    
    @Autowired
    public ProductController(final ProductService productService, final SellerService sellerService,
                             final EmailService emailService, final ImageService imageService, final UserService userService,
                             final SecurityService securityService, EcotagService ecotagService, final OrderService orderService) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.emailService = emailService;
        this.imageService = imageService;
        this.userService = userService;
        this.securityService = securityService;
        this.ecotagService = ecotagService;
        this.orderService = orderService;
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

        List<Product> allProducts = productService.getAvailable();
        mav.addObject("isEmpty", allProducts.isEmpty());

        //Parameters for filter
        mav.addObject("name", name);
        mav.addObject("categories", Category.values());
        mav.addObject("chosenCategory", category);
        if(maxPrice > -1.0)
            mav.addObject("maxPrice", maxPrice);
        else
            mav.addObject("maxPrice", null);

        //Ecotag management
        mav.addObject("ecoStrings", new String[]{"1", "2", "3", "4", "5"});
        for(String s : strings)
            System.out.println(s);
        mav.addObject("path", productService.buildPath(strings));

        final boolean[] boolTags = new boolean[Ecotag.values().length];
        mav.addObject("boolTags", boolTags);

        List<Ecotag> tagsToFilter = ecotagService.filterByTags(strings, boolTags);
        List<Ecotag> ecotagList = Arrays.asList(Ecotag.values());
        mav.addObject("ecotagList", ecotagList);

        //Product filter
        List<List<Product>> productPages = productService.exploreProcess(name, category, tagsToFilter, maxPrice, sort, direction);

        //Sorting
        mav.addObject("sort", sort);
        mav.addObject("direction", direction);
        mav.addObject("sortName", Sort.getById(sort).getName());
        mav.addObject("sorting", Sort.values());

        //Pagination
        mav.addObject("currentPage", page);
        mav.addObject("pages", productPages);

        List<Product> displayProducts = productService.getProductPage(page, productPages);
        mav.addObject("products", displayProducts);

        return mav;
    }

    @RequestMapping(value = "/deleteProduct/{prodId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long prodId){
        Boolean bool = productService.attemptDelete(prodId);
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
        final Optional<Product> product = productService.getById(productId);

        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product productObj = product.get();

        if(productObj.getStock() == 0){
            return new ModelAndView("redirect:/404");
        }

        mav.addObject("product", productObj);
        mav.addObject("categories", Category.values());
        List<Product> interesting = productService.getInteresting(productObj);
        mav.addObject("interesting", interesting);

        final Optional<Seller> seller = sellerService.findById(productObj.getSellerId());
        if(!seller.isPresent()) throw new RuntimeException("Seller not found");
        //Should never have that exception, the product exists and sellerID is FK

        List<Ecotag> ecotags = ecotagService.getTagFromProduct(productObj.getProductId());
        mav.addObject("area", Area.getById(seller.get().getAreaId()));
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
        final Optional<Product> product = productService.getById(prodId);
        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product p = product.get();

        Boolean enough = productService.checkForAvailableStock(p, form.getAmount());
        if(!enough){
            errors.addError(new ObjectError("amount",
                    "El stock disponible es insuficiente para su pedido. Por favor, reviselo" +
                            "e intente nuevamente"));
            return productPage(prodId, form, true);
        }

        final Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No hay un usuario loggeado");
        final User u = user.get();

        final Optional<Seller> seller = sellerService.findById(product.get().getSellerId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");
        final Seller s = seller.get();

        Boolean created = orderService.createAndNotify(p, u, s, form.getAmount(), form.getMessage());
        if(!created) throw new IllegalStateException();

        final ModelAndView mav = new ModelAndView("redirect:/userProfile/true#test2");
        return mav;
    }

    @RequestMapping(value="/createProduct", method=RequestMethod.GET)
    public ModelAndView createProduct(@ModelAttribute("productForm") final ProductForm form) {
        final ModelAndView mav = new ModelAndView("createProducts");
        mav.addObject("tagList", Arrays.asList(Ecotag.values()));
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

        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if (!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByUserId(user.get().getId());
        if (!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        int stock = Integer.parseInt(form.getStock());
        int price = Integer.parseInt(form.getPrice());

        Product product = productService.create(seller.get().getId(),
                form.getCategory(), form.getName(), form.getDescription(),
                stock, price, image);

        for (long id : form.getEcotag()) {
            ecotagService.addTag(Ecotag.getById(id), product.getProductId());
        }

        return new ModelAndView("redirect:/product/" + product.getProductId());
    }

}
