package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.RoleDao;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(final RoleDao roleDao){
        this.roleDao = roleDao;
    }

    @Override
    public Optional<Role> getById(long id) {
        return roleDao.getById(id);
    }

    @Override
    public Optional<Role> getByName(String name) {
        return roleDao.getByName(name);
    }
}
