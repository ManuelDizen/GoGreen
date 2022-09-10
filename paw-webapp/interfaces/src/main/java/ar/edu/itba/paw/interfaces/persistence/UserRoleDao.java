package ar.edu.itba.paw.interfaces.persistence;
import ar.edu.itba.paw.models.Role;
import java.util.List;

public interface UserRoleDao {
    List<Role> getById(long userId);
    Role create(long userId, long roleId);
}
