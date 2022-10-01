package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    @Override
    public List<UserRole> getById(long userId) {
        return userRoleDao.getById(userId);
    }

    @Override
    public UserRole create(long userId, long roleId) {
        return userRoleDao.create(userId, roleId);
    }
}
