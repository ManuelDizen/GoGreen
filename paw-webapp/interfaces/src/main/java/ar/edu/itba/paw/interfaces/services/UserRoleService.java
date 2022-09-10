package ar.edu.itba.paw.interfaces.services;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getById(long userId);
    UserRole create(long userId, long roleId);
}
