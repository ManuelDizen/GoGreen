package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.OrderService;
import ar.edu.itba.paw.interfaces.services.SecurityService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value="profile")
    public ModelAndView profile(){
        User user = securityService.getLoggedUser();
        if(user == null){
            throw new IllegalStateException("Only logged users can access their profile");
        }
        if(sellerService.findByMail(user.getEmail()).isPresent()){
            // It's a seller
            return new ModelAndView("redirect:/sellerProfile");
        }
        return new ModelAndView("redirect:/userProfile");
    }

    @RequestMapping(value="/userProfile")
    public ModelAndView buyerProfile(){
        final ModelAndView mav = new ModelAndView("userProfile");
        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("no lo ovaf dkfds");
        mav.addObject("user", user.get());
        return mav;
    }

    @RequestMapping(value="/sellerProfile")
    public ModelAndView sellerProfile(){
        final ModelAndView mav = new ModelAndView("sellerProfile");

        Optional<User> user = userService.findByEmail(securityService.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No se encntró user");

        Optional<Seller> seller = sellerService.findByMail(user.get().getEmail());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        List<Order> orders = orderService.getBySellerEmail(user.get().getEmail());

        // TODO: Acá faltaría además buscar los productos que vende un seller,
        //  y las órdenes que tiene pendientes (para esto hay que agrandar la BDD)

        mav.addObject("seller", seller.get());
        mav.addObject("user", user.get());
        mav.addObject("orders", orders);
        return mav;
    }
}
