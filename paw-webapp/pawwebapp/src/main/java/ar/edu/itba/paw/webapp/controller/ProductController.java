package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.OrderForm;
import ar.edu.itba.paw.webapp.form.ProductForm;
import ar.edu.itba.paw.webapp.form.UpdateProdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
public class ProductController {
    private final ProductService productService;

    private final SellerService sellerService;

    private final EcotagService ecotagService;

    private final OrderService orderService;

    private final CommentService commentService;

    private final UserService userService;

    private static final int INTERESTING_SIZE = 4;
    private static final int PRODUCTS_PER_PAGE = 12;

    @Autowired
    public ProductController(final ProductService productService, final SellerService sellerService,
                             EcotagService ecotagService, final OrderService orderService,
                             final CommentService commentService, final UserService userService) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.ecotagService = ecotagService;
        this.orderService = orderService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(value="/explore")
    public ModelAndView exploreProducts(
            @RequestParam(name="name", defaultValue="") final String name,
            @RequestParam(name="category", defaultValue="0") final long category,
            @RequestParam(name="strings", defaultValue = "null") final String[] strings,
            @RequestParam(name="maxPrice", defaultValue = "-1") final Integer maxPrice,
            @RequestParam(name="areaId", defaultValue="-1") final long areaId,
            @RequestParam(name="favorite", defaultValue="false") final boolean favorite,
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="sort", defaultValue = "0") final int sort,
            @RequestParam(name="direction", defaultValue = "1") final int direction
    ) {
        final ModelAndView mav = new ModelAndView("explore");
        boolean atLeastOne = productService.atLeastOneProduct();
        mav.addObject("isEmpty", atLeastOne);

        //Parameters for filter
        mav.addObject("name", name);
        mav.addObject("categories", Category.values());
        mav.addObject("chosenCategory", category);
        mav.addObject("areas", Area.values());
        mav.addObject("chosenArea", areaId);
        mav.addObject("favorite", favorite);
        mav.addObject("maxPrice", maxPrice > -1?maxPrice:null);

        //Ecotag management
        mav.addObject("ecoStrings", new String[]{"1", "2", "3", "4", "5"});
        mav.addObject("path", productService.buildPath(strings));

        boolean[] boolTags = new boolean[Ecotag.values().length-1];

        List<Ecotag> tagsToFilter = ecotagService.filterByTags(strings, boolTags);
        mav.addObject("ecotagList", Ecotag.values());
        mav.addObject("boolTags", boolTags);
        mav.addObject("favoritePath", favorite?"favorite=on&":"");

        List<Product> filteredProducts = productService.exploreProcess(name, category, tagsToFilter,
                maxPrice, areaId, sort, direction, favorite);
        List<List<Product>> productPages = productService.divideIntoPages(filteredProducts,
                PRODUCTS_PER_PAGE);

        //Sorting
        mav.addObject("sort", sort);
        mav.addObject("direction", direction);
        String sortName = Objects.requireNonNull(Sort.getById(sort)).getName();
        mav.addObject("sortName", sortName);
        mav.addObject("sorting", Sort.values());

        //Pagination
        mav.addObject("currentPage", page);
        mav.addObject("pages", productPages);

        List<Product> displayProducts = productService.getProductPage(page, productPages);
        mav.addObject("products", displayProducts);

        return mav;
    }

    @RequestMapping(value = "/deleteProduct/{productId}", method = RequestMethod.GET)
    public ModelAndView deleteProduct(@PathVariable final long productId){
        productService.attemptDelete(productId);
        return new ModelAndView("redirect:/sellerProfile");
    }

    @RequestMapping("/product/{productId:[0-9]+}")
    public ModelAndView productPage(
            @PathVariable("productId") final long productId,
            @Valid @ModelAttribute("orderForm") final OrderForm form,
            @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="created", defaultValue = "false") final boolean created,
            @RequestParam(name="formFailure", defaultValue = "false") final boolean formFailure){

        final ModelAndView mav = new ModelAndView("productPage");
        final Optional<Product> product = productService.getById(productId);

        if(!product.isPresent()) throw new ProductNotFoundException();
        final Product productObj = product.get();

        mav.addObject("product", productObj);
        mav.addObject("category", Category.getById(productObj.getCategoryId()));
        mav.addObject("created", created);
        mav.addObject("interesting", productService.getInteresting(productObj, INTERESTING_SIZE));

        final Optional<Seller> seller = sellerService.findById(productObj.getSeller().getId());
        if(!seller.isPresent()) throw new UserNotFoundException();

        mav.addObject("user", seller.get().getUser());
        User user = userService.getLoggedUser();
        mav.addObject("loggedEmail", user == null ? null : user.getEmail());

        //TODO: nooo esto hay que cambiarlo
        List<List<Comment>> comments = commentService.getCommentsForProduct(productId);
        mav.addObject("comments", comments.get(page-1));
        mav.addObject("commentPages", comments);
        mav.addObject("currentPage", page);

        List<Ecotag> ecotags = ecotagService.getTagsFromProduct(productObj.getProductId());
        Area area = seller.get().getArea();
        mav.addObject("area", area);
        mav.addObject("formFailure", formFailure);
        mav.addObject("ecotags", ecotags);
        mav.addObject("categories", Category.values());
        mav.addObject("seller", seller.get());
        mav.addObject("availableId", ProductStatus.AVAILABLE.getId());
        mav.addObject("pausedId", ProductStatus.PAUSED.getId());
        mav.addObject("outofstockId", ProductStatus.OUTOFSTOCK.getId());
        mav.addObject("deletedId", ProductStatus.DELETED.getId());
        return mav;
    }


    @RequestMapping(value="/process/{productId}", method = {RequestMethod.POST})
    public ModelAndView process(@PathVariable final long productId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                final BindingResult errors){
        if(errors.hasErrors()){
            return productPage(productId, form, commentForm,1,false, true);
        }
        orderService.create(productId, form.getAmount(), form.getMessage());
        ModelAndView mav = new ModelAndView("redirect:/buyerProfile");
        return mav;
    }

    @RequestMapping(value = "/newComment/{productId}", method = {RequestMethod.POST})
    public ModelAndView comment(@PathVariable final long productId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                final BindingResult errors){
        if(errors.hasErrors())
            return productPage(productId, form, commentForm, 1, false, true);
        commentService.create(productId, commentForm.getMessage());
        return new ModelAndView("redirect:/product/{productId}");
    }

    @RequestMapping(value = "/reply/{productId}", method = {RequestMethod.POST})
    public ModelAndView reply(@PathVariable final long productId,
                                @Valid @ModelAttribute("orderForm") final OrderForm form,
                                @Valid @ModelAttribute("commentForm") final CommentForm commentForm,
                                final BindingResult errors){
        if(errors.hasErrors())
            return productPage(productId, form, commentForm, 1, false,true);
        commentService.replyComment(commentForm.getParentId(), commentForm.getMessage());
        return new ModelAndView("redirect:/product/{productId}");
    }

    @RequestMapping(value="/createProduct", method=RequestMethod.GET)
    public ModelAndView createProduct(@ModelAttribute("productForm") final ProductForm form) {
        final ModelAndView mav = new ModelAndView("createProducts");
        mav.addObject("tagList", Arrays.asList(Ecotag.values()));
        mav.addObject("categories", Category.values());
        return mav;
    }

    @RequestMapping(value="/pauseProduct/{productId:[0-9]+}", method=RequestMethod.GET)
    public ModelAndView pauseProduct(@PathVariable("productId") final long productId) {
        productService.attemptPause(productId);
        return new ModelAndView("redirect:/sellerProfile");
    }

    @RequestMapping(value="/republishProduct/{productId:[0-9]+}", method=RequestMethod.GET)
    public ModelAndView republishProduct(@PathVariable("productId") final long productId) {
        productService.attemptRepublish(productId);
        return new ModelAndView("redirect:/sellerProfile");
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
        return new ModelAndView("redirect:/createdProduct/" + product.getProductId());
    }

    @RequestMapping(value="/updateProduct/{productId:[0-9]+}", method=RequestMethod.GET)
    public ModelAndView updateProduct(
            @PathVariable("productId") final long productId,
            @ModelAttribute("updateProdForm") final UpdateProdForm form
    ){
        productService.checkForOwnership(productId);
        ModelAndView mav = new ModelAndView("/updateProduct");
        mav.addObject("product", productService.getById(productId).get());
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
        productService.updateProduct(productId, form.getNewStock(), form.getNewPrice());
        return new ModelAndView("redirect:/sellerProfile");
    }

}
