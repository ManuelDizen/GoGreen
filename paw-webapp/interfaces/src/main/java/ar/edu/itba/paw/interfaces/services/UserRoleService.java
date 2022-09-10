package ar.edu.itba.paw.interfaces.services;
import ar.edu.itba.paw.models.Role;
import java.util.List;

public interface UserRoleService {
    List<Role> getById(long userId);
    Role create(long userId, long roleId);
}
