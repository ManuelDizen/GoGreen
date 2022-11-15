package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.models.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class GoGreenUserDetailsService implements UserDetailsService {
    private final UserService us;
    private final UserRoleService urs;
    private final RoleService rs;

    private final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    public GoGreenUserDetailsService(final UserService us, final UserRoleService urs,
                                     final RoleService rs) {
        this.us = us;
        this.urs = urs;
        this.rs = rs;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = us.findByEmail(email).orElseThrow(
                UserNotFoundException::new);
        Collection<GrantedAuthority> auths = new ArrayList<>();
        //List<UserRole> userToRoleList = urs.getById(user.getId());

        Set<Role> roles = urs.getById(user.getId());

        for(Role role : roles){
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        if(roles.isEmpty()){
            //By default, users are buyers unless stated otherwise
            // 10/9/22: Resulta necesario agregar en la bdd, cuando un usuario se registre, su rol
            // así no entra acá equivocadamente. Esto es importantisimo.
            Optional<Role> role = rs.getByName("BUYER");
            if(!role.isPresent()){
                throw new RoleNotFoundException();
            }
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.get().getName()));
            //urs.create(user, role.get());
            user.addRole(role.get());
        }
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), auths);
    }
}
