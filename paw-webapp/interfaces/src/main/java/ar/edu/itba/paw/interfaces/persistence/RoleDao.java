package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Role;

import java.util.Optional;

public interface RoleDao {
    Optional<Role> getById(long id);
    Optional<Role> getByName(String name);
}
