package ar.edu.itba.paw.interfaces.services;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;

import java.util.Set;

public interface UserRoleService {
    Set<Role> getById(long userId);
    UserRole create(User user, Role role);
}
