package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ProfilePicForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController {

    private static final int ORDERS_PER_PAGE = 8;
    private static final int PRODUCTS_PER_PAGE = 6;
    private final UserService userService;
    private final SellerService sellerService;
    private final OrderService orderService;
    private final ProductService productService;
    private final ArticleService articleService;
    private final FavoriteService favoriteService;

    @Autowired
    public UserController(final UserService userService, final SellerService sellerService,
                          final OrderService orderService, final ProductService productService,
                          final ArticleService articleService, final FavoriteService favoriteService) {
        this.userService = userService;
        this.sellerService = sellerService;
        this.orderService = orderService;
        this.productService = productService;
        this.articleService = articleService;
        this.favoriteService = favoriteService;
    }

    @RequestMapping(value="profile")
    public ModelAndView profile(){
        User user = userService.getLoggedUser();
        if(user == null){
            throw new UnauthorizedRoleException();
        }
        if(sellerService.findByMail(user.getEmail()).isPresent()){
            return new ModelAndView("redirect:/sellerProfile");
        }
        return new ModelAndView("redirect:/userProfile");
    }


    @RequestMapping(value="/userProfile")
    public ModelAndView buyerProfile(
            @ModelAttribute("profilePicForm") final ProfilePicForm form,
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="fromSale", defaultValue="false") final boolean fromSale){

        final ModelAndView mav = new ModelAndView("userProfile");
        User user = userService.getLoggedUser();
        if(user == null) throw new UserNotFoundException();
        mav.addObject("user", user);

        //TODO: Cambiar por paginación
        List<Order> orders = orderService.getByBuyerEmail(user.getEmail());
        //TODO: este método debería estar en algun "utils"
        List<List<Order>> orderPages = productService.divideIntoPages(orders, ORDERS_PER_PAGE);

        mav.addObject("currentPage", page);
        mav.addObject("pages", orderPages);
        mav.addObject("orders", orderPages.get(page-1));
        mav.addObject("fromSale", fromSale);
        //TODO: Che esto esta muy mal, hay que cambiarlo urgente por un
        // "getUsersForLogged" y "getSellersForLogged"
        mav.addObject("users", userService.getAll());
        mav.addObject("sellers", sellerService.getAll());
        return mav;
    }

    @RequestMapping(value="/buyerProfile")
    public ModelAndView buyerProfileFromSale(@ModelAttribute("profilePicForm") final ProfilePicForm form) {
        //TODO: No me gusta esto, habría que cambiarlo
        return buyerProfile(form, 1, true);
    }

    @RequestMapping(value="/sellerProfile")
    public ModelAndView sellerProfile(@ModelAttribute("profilePicForm") final ProfilePicForm form,
                                      @RequestParam(name="pageP", defaultValue="1") final int pageP,
                                      @RequestParam(name="pageO", defaultValue="1") final int pageO){
        final ModelAndView mav = new ModelAndView("sellerProfile");

        User user = userService.getLoggedUser();
        if(user==null) throw new UserNotFoundException();

        Optional<Seller> seller = sellerService.findByMail(user.getEmail());
        if(!seller.isPresent()) throw new UserNotFoundException();

        List<Order> orders = orderService.getBySellerEmail(user.getEmail());
        List<List<Order>> orderPages = productService.divideIntoPages(orders, ORDERS_PER_PAGE);
        List<Product> products = productService.findBySeller(seller.get().getId(), false);
        List<List<Product>> productPages = productService.divideIntoPages(products, PRODUCTS_PER_PAGE);

        mav.addObject("seller", seller.get());
        mav.addObject("user", user);
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
        if(!seller.isPresent()){
            System.out.println("!!! No encontre seller con sellerId " + sellerId + "!!!\n\n");
            throw new UserNotFoundException();
        }
        mav.addObject("seller", seller.get());
        mav.addObject("user", seller.get().getUser());
        mav.addObject("loggedEmail", userService.getLoggedEmail());
        mav.addObject("areas", Area.values());
        mav.addObject("categories", Category.values());

        List<Product> products = productService.findBySeller(sellerId, true);
        //TODO: Move to service
        //mav.addObject("recentProducts", products.size() >= 3? products.subList(0,3):products);

        List<Article> news = articleService.getBySellerId(sellerId);
        //TODO: Move to service
        news = news.size() > 2? news.subList(0, 2):news;
        mav.addObject("news", news);

        int n_orders = orderService.getTotalOrdersForSeller(seller.get().getUser().getEmail());
        mav.addObject("n_orders", n_orders);
        mav.addObject("isFavorite", favoriteService.isFavorite(seller.get()));

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
        //TODO: This method should go to an utils service, which should definitely not be seen by the controller
        List<List<Article>> newsPages = productService.divideIntoPages(news, 10);
        final ModelAndView mav = new ModelAndView("userNewsFeed");
        List<Seller> favs = favoriteService.getFavoriteSellersByUserId();
        mav.addObject("currentPage", page);
        mav.addObject("pages", newsPages);
        mav.addObject("favs", favs);
        mav.addObject("news", newsPages.get(page-1));
        mav.addObject("user", userService.getLoggedUser());
        return mav;
    }

    @RequestMapping(value="/toggleNotifications")
    public ModelAndView toggleNotifications(HttpServletRequest request){
        userService.toggleNotifications();
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/exploreSellers")
    public ModelAndView exploreSellers(
            @RequestParam(name="name", defaultValue="") final String name,
            @RequestParam(name="areaId", defaultValue="-1") final long areaId,
            @RequestParam(name="favorite", defaultValue="false") final boolean favorite,
            @RequestParam(name="page", defaultValue = "1") final int page,
            @RequestParam(name="sort", defaultValue = "0") final int sort,
            @RequestParam(name="direction", defaultValue = "1") final int direction
                                        ){
        //TODO: Paginate queries not to load all sellers in memory simultaneously
        //Pagination<Seller> sellers = sellerService.filter(name, areaId, favorite, page, sort, direction);
        List<Seller> sellers = sellerService.getAll();
        final ModelAndView mav = new ModelAndView("exploreSellers");
        mav.addObject("isEmpty", sellers.isEmpty()); //TODO: Change the "getAll()" call
        mav.addObject("sellers", sellers);
        mav.addObject("name", name);
        mav.addObject("categories", Category.values());
        mav.addObject("areas", Area.values());
        mav.addObject("chosenArea", areaId);
        mav.addObject("favorite", favorite);

        String favoritePath = "";
        if(favorite) {
            //TODO: Filter by favorites (en realidad se debería hacer directo en el servicio inicial)
            //productService.onlyFavorites(filteredProducts, userService.getLoggedUser().getId());
            favoritePath = "favorite=on&";
        }
        mav.addObject("favoritePath", favoritePath);
        mav.addObject("direction", direction);
        String sortName = Objects.requireNonNull(Sort.getById(sort)).getName();
        mav.addObject("sortName", sortName);
        mav.addObject("sorting", Sort.values());
        mav.addObject("favIds", favoriteService.getFavIdsByUser(userService.getLoggedUser()));

        return mav;
    }

    @RequestMapping(value="/updateProfilePic", method= RequestMethod.POST)
    public ModelAndView updateProfilePic(HttpServletRequest request,
                                         @Valid @ModelAttribute("profilePicForm") final ProfilePicForm form,
                                         final BindingResult errors){
        if (errors.hasErrors()){
            if(userService.isLoggedUser())
                return buyerProfile(form,1,false);
            else if(userService.isLoggedSeller())
                return sellerProfile(form,1,1);
            else throw new ForbiddenActionException();
        }
        byte[] image;
        try {
            image = form.getImage().getBytes();
        } catch (IOException e) {throw new RuntimeException(e);}
        userService.setProfilePic(image);
        String referer = request.getHeader("Referer");
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value="/deleteProfilePic")
    public ModelAndView deleteProfilePic(HttpServletRequest request){
        userService.deleteProfilePic();
        String referer = request.getHeader("Referer");
        if(referer.contains("updateProfilePic")){
            if(userService.isLoggedUser())
                referer = "/userProfile";
            else if(userService.isLoggedSeller())
                referer = "/sellerProfile";
        }
        return new ModelAndView("redirect:" + referer);
    }
}
