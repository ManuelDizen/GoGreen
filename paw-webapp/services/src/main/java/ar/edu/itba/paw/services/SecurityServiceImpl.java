package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.SecurityService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.models.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final RoleService roleService;

    @Autowired
    public SecurityServiceImpl(final UserService userService, UserRoleService userRoleService, RoleService roleService){
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
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
                throw new UserNotFoundException();
            }
            return user.get().getEmail();
        }
        return null;
    }

    public String getLoggedFirstName(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return user.get().getFirstName();
    }

    public String getLoggedSurname(){
        Authentication auth = getAuthentication();
        Optional<User> user = userService.findByEmail(auth.getName());
        if(!user.isPresent()){
            throw new UserNotFoundException();
        }
        return user.get().getSurname();
    }

    @Override
    public List<Role> getLoggedUserRoles() {
        List<Role> roles = new ArrayList<>();
        User user= getLoggedUser();
        if(user == null) return roles;
        long userId = user.getId();
        for(UserRole ur : userRoleService.getById(userId)){
            Optional<Role> role = roleService.getById(ur.getRoleId());
            if(!role.isPresent()) throw new RoleNotFoundException();
            roles.add(role.get());
        }
        return roles;
    }

    @Override
    public Boolean loggedUserIsSeller() {
        List<Role> roles = getLoggedUserRoles();
        boolean isSeller = false;
        for(Role role:roles){
            if (role.getName().equals("SELLER")) {
                isSeller = true;
                break;
            }
        }
        return isSeller;
    }
}
