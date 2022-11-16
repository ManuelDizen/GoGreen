package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public Set<Role> getById(long userId) {
        return userRoleDao.getById(userId);
    }

    @Transactional
    @Override
    public UserRole create(User user, Role role) {
        return userRoleDao.create(user, role);
    }
}
