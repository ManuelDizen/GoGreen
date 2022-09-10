package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService{

    private UserRoleDao urd;

    @Autowired
    public UserRoleServiceImpl(UserRoleDao urd) {
        this.urd = urd;
    }

    @Override
    public List<UserRole> getById(long userId) {
        return urd.getById(userId);
    }

    @Override
    public UserRole create(long userId, long roleId) {
        return urd.create(userId, roleId);
    }
}
