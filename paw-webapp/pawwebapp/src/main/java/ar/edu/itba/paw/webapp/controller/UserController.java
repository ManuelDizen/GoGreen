package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final SellerService sellerService;
    private final SecurityService securityService;
    private final OrderService orderService;
    private final ProductService productService;
    private final ArticleService articleService;
    private final FavoriteService favoriteService;

    @Autowired
    public UserController(final UserService userService, final SellerService sellerService,
                          final SecurityService securityService, final OrderService orderService,
                          final ProductService productService, final ArticleService articleService,
                          final FavoriteService favoriteService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.securityService = securityService;
        this.orderService = orderService;
        this.productService = productService;
        this.articleService = articleService;
        this.favoriteService = favoriteService;
    }

    @RequestMapping(value="profile")
    public ModelAndView profile(){
        User user = securityService.getLoggedUser();
        if(user == null){
            throw new UnauthorizedRoleException();
        }
        if(sellerService.findByMail(user.getEmail()).isPresent()){
            // It's a seller
            return new ModelAndView("redirect:/sellerProfile");
        }
        return new ModelAndView("redirect:/userProfile");
    }


    @RequestMapping(value="/userProfile")
    public ModelAndView buyerProfile(
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="fromSale", defaultValue="false") final boolean fromSale){
        final ModelAndView mav = new ModelAndView("userProfile");
        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new UserNotFoundException();
        mav.addObject("user", user.get());

        List<Order> orders = orderService.getByBuyerEmail(user.get().getEmail());

        List<List<Order>> orderPages = productService.divideIntoPages(orders, 8);

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
        List<List<Order>> orderPages = productService.divideIntoPages(orders, 8);
        List<Product> products = productService.findBySeller(seller.get().getId());
        List<List<Product>> productPages = productService.divideIntoPages(products, 6);

        mav.addObject("seller", seller.get());
        mav.addObject("user", user.get());
        mav.addObject("currentPageP", pageP);
        mav.addObject("currentPageO", pageO);
        mav.addObject("productPages", productPages);
        mav.addObject("orderPages", orderPages);
        mav.addObject("orders", orderPages.get(pageO-1));
        mav.addObject("products", productPages.get(pageP-1));

        //TODO: See how to optimize this 4 states while keeping it parametrized
        mav.addObject("availableId", ProductStatus.AVAILABLE.getId());
        mav.addObject("pausedId", ProductStatus.PAUSED.getId());
        mav.addObject("outofstockId", ProductStatus.OUTOFSTOCK.getId());
        mav.addObject("deletedId", ProductStatus.DELETED.getId());
        return mav;
    }

    @RequestMapping(value="/sellerPage/{sellerId:[0-9]+}")
    public ModelAndView sellerPage(@PathVariable("sellerId") long sellerId,
                                   @RequestParam(name="page", defaultValue = "1") final int page){
        ModelAndView mav = new ModelAndView("sellerPage");
        Optional<Seller> seller = sellerService.findById(sellerId);
        if(!seller.isPresent()) throw new UserNotFoundException();
        mav.addObject("seller", seller.get());
        mav.addObject("user", seller.get().getUser());
        User user = securityService.getLoggedUser();
        //TODO: Move to service
        String loggedEmail = user == null? null : user.getEmail();
        mav.addObject("loggedEmail", loggedEmail);
        mav.addObject("areas", Area.values());
        mav.addObject("categories", Category.values());

        List<Product> products = productService.findBySeller(sellerId);
        //TODO: Move to service
        //mav.addObject("recentProducts", products.size() >= 3? products.subList(0,3):products);

        List<Article> news = articleService.getBySellerId(sellerId);
        //TODO: Move to service
        news = news.size() > 2? news.subList(0, 2):news;
        mav.addObject("news", news);

        List<Order> orders = orderService.getBySellerEmail(seller.get().getUser().getEmail());
        mav.addObject("orders", orders);
        mav.addObject("isFavorite", favoriteService.isFavorite(
                securityService.getLoggedUser(), seller.get()));

        List<List<Product>> productPages = productService.divideIntoPages(products, 8);
        mav.addObject("currentPage", page);
        mav.addObject("pages", productPages);
        mav.addObject("recentProducts", productPages.get(page-1));

        return mav;
    }

    @RequestMapping(value="/setFav/{sellerId:[0-9]+}/{toggle}")
    public ModelAndView setFavorite(@PathVariable("sellerId") long sellerId,
                                    @PathVariable("toggle") boolean toggle,
                                    HttpServletRequest request){
        favoriteService.toggleFavorite(sellerId, toggle);
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value="/newsFeed")
    public ModelAndView newsFeed(@RequestParam(name="page", defaultValue = "1") final int page){
        List<Article> news = articleService.getForLoggedUser();
        List<List<Article>> newsPages = productService.divideIntoPages(news, 10);
        final ModelAndView mav = new ModelAndView("userNewsFeed");
        User user = securityService.getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        List<Seller> favs = favoriteService.getFavoriteSellersByUserId(user.getId());
        mav.addObject("currentPage", page);
        mav.addObject("pages", newsPages);
        mav.addObject("favs", favs);
        mav.addObject("news", newsPages.get(page-1));
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value="/toggleNotifications")
    public ModelAndView toggleNotifications(HttpServletRequest request){
        User user = securityService.getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        userService.toggleNotifications(user.getId());
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

}
