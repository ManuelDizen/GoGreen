package ar.edu.itba.paw.interfaces.services;
import ar.edu.itba.paw.models.Role;
import java.util.Optional;

public interface RoleService {
    Optional<Role> getById(long id);
    Optional<Role> getByName(String name);
}
