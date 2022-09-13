package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private AuthenticationController authController;


    @RequestMapping(value="/userProfile")
    public ModelAndView profile(){
        /*Optional<User> user = userService.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario loggeado");
        }*/
        final ModelAndView mav = new ModelAndView("userProfile");
        Optional<User> user = userService.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("no lo ovaf dkfds");
        mav.addObject("user", user.get());
        return mav;
    }

    @RequestMapping(value="/sellerProfile")
    public ModelAndView sellerProfile(){
        final ModelAndView mav = new ModelAndView("sellerProfile");
        Optional<User> user = userService.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()) throw new IllegalStateException("No se encntró user");
        Optional<Seller> seller = sellerService.findById(user.get().getId());
        if(!seller.isPresent()) throw new IllegalStateException("No se encontró seller");

        // TODO: Acá faltaría además buscar los productos que vende un seller,
        //  y las órdenes que tiene pendientes (para esto hay que agrandar la BDD)

        mav.addObject("seller", seller.get());
        mav.addObject("user", user.get());
        return mav;
    }
}
