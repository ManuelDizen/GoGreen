package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.SecurityService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;

    @Autowired
    public SecurityServiceImpl(final UserService userService){
        this.userService = userService;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getLoggedUser(){
        Authentication auth = getAuthentication();
        if(auth != null){
            Optional<User> user = userService.findByEmail(auth.getName());
            return user.orElse(null);
        }
        return null;
    }

    public String getLoggedEmail(){
        Authentication auth = getAuthentication();
        if(auth != null) {
            Optional<User> user = userService.findByEmail(auth.getName());
            // Remember username is given by email and not a proper username
            if (!user.isPresent()) {
                throw new IllegalStateException("No se encontró usuario");
            }
            return user.get().getEmail();
        }
        return null;
    }

    public String getLoggedFirstName(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario");
        }
        return user.get().getFirstName();
    }

    public String getLoggedSurname(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        if(!user.isPresent()){
            throw new IllegalStateException("No se encontró usuario");
        }
        return user.get().getSurname();
    }
}
