package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    private final ArticleService articleService;

    @Autowired
    public UserController(final UserService userService, final SellerService sellerService,
                          final SecurityService securityService, final OrderService orderService,
                          final ProductService productService, final ArticleService articleService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.securityService = securityService;
        this.orderService = orderService;
        this.productService = productService;
        this.articleService = articleService;
    }

    @RequestMapping(value="profile")
    public ModelAndView profile(){
        User user = securityService.getLoggedUser();
        if(user == null){
            throw new UnauthorizedRoleException();
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
        if(!user.isPresent()) throw new UserNotFoundException();
        mav.addObject("user", user.get());

        List<Order> orders = orderService.getByBuyerEmail(user.get().getEmail());

        List<List<Order>> orderPages = orderService.divideIntoPages(orders);

        mav.addObject("currentPage", page);
        mav.addObject("pages", orderPages);
        mav.addObject("orders", orderPages.get(page-1));
        mav.addObject("fromSale", fromSale);
        mav.addObject("users", userService.getAll());
        mav.addObject("sellers", sellerService.getAll());
        return mav;
    }

    @RequestMapping(value="/sellerProfile")
    public ModelAndView sellerProfile(@RequestParam(name="pageP", defaultValue="1") final int pageP,
                                      @RequestParam(name="pageO", defaultValue="1") final int pageO){
        final ModelAndView mav = new ModelAndView("sellerProfile");

        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new UserNotFoundException();

        Optional<Seller> seller = sellerService.findByMail(user.get().getEmail());
        if(!seller.isPresent()) throw new UserNotFoundException();

        List<Order> orders = orderService.getBySellerEmail(user.get().getEmail());
        List<List<Order>> orderPages = orderService.divideIntoPages(orders);
        List<Product> products = productService.findBySeller(seller.get().getId());
        List<List<Product>> productPages = productService.divideIntoPages(products, 3);

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

    @RequestMapping(value="/sellerPage/{sellerId:[0-9]+}")
    public ModelAndView sellerPage(@PathVariable("sellerId") long sellerId){


        ModelAndView mav = new ModelAndView("sellerPage");
        Optional<Seller> seller = sellerService.findById(sellerId);
        if(!seller.isPresent()) throw new UserNotFoundException();
        mav.addObject("seller", seller.get());
        mav.addObject("user", seller.get().getUser());
        User user = securityService.getLoggedUser();
        String loggedEmail = user == null? null : user.getEmail();
        mav.addObject("loggedEmail", loggedEmail);
        mav.addObject("areas", Area.values());
        mav.addObject("categories", Category.values());

        List<Product> products = productService.findBySeller(sellerId);
        mav.addObject("recentProducts", products.size() >= 3? products.subList(0,2):products);

        List<Article> news = articleService.getBySellerId(sellerId);
        mav.addObject("news", news);

        List<Order> orders = orderService.getBySellerEmail(seller.get().getUser().getEmail());
        mav.addObject("orders", orders);

        return mav;
    }

}
