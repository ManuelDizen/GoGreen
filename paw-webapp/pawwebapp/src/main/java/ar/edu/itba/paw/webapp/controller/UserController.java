package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
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
    private AuthenticationController authController;


    @RequestMapping(value="/userProfile")
    public ModelAndView profile(){
        Optional<User> user = userService.findByEmail(authController.getLoggedEmail());
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario loggeado");
        }
        final ModelAndView mav = new ModelAndView("userProfile");
        mav.addObject("user", user.get());
        return mav;
    }
}
