package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value="/email", method = RequestMethod.GET)
    @ResponseBody
    public String getLoggedEmail(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        // Remember username is given by email and not a proper username
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario");
        }
        return user.get().getEmail();
    }

    @RequestMapping(value="/firstName", method = RequestMethod.GET)
    public String getLoggedFirstName(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario");
        }
        return user.get().getFirstName();
    }

    @RequestMapping(value="/surname", method = RequestMethod.GET)
    public String getLoggedSurname(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario");
        }
        return user.get().getSurname();
    }
}
