package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GoGreenUserDetailsService implements UserDetailsService {
    private final UserService us;
    private final UserRoleService urs;
    private final RoleService rs;

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
                () -> new RuntimeException("No se encontró al usuario " + email));
        Collection<GrantedAuthority> auths = new ArrayList<>();
        List<UserRole> userToRoleList = urs.getById(user.getId());
        for(UserRole ur : userToRoleList){
            Optional<Role> role = rs.getById(ur.getRoleId());
            if(!role.isPresent())
                throw new IllegalStateException("No hay role con ese ID");
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.get().getName()));
            //Not necessary to add ToUpper() as roles are already in uppercase on the DB

            //TODO: Acá todavía falta otorgarle los permisos permitidos a los usuarios
            // dados por los roles
        }
        if(userToRoleList.isEmpty()){
            //By default, users are buyers unless stated otherwise
            // 10/9/22: Resulta necesario agregar en la bdd, cuando un usuario se registre, su rol
            // así no entra acá equivocadamente. Esto es importantisimo.
            Optional<Role> role = rs.getByName("BUYER");
            if(!role.isPresent()){
                throw new IllegalStateException("No se encontró el rol");
            }
            auths.add(new SimpleGrantedAuthority("ROLE_" + role.get().getName()));
            // TODO: Acá agregar todos los permisos que tiene también
            urs.create(user.getId(), role.get().getId());
        }
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), auths);
    }
}
