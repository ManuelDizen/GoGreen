package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder encoder, RoleService roleService, UserRoleService userRoleService, EmailService emailService){
        this.userDao = userDao;
        this.encoder = encoder;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public User register(String firstName, String surname, String email, String password, Locale locale) {
        return userDao.create(firstName, surname, email, encoder.encode(password), locale);
    }

    @Override
    public Boolean registerUser(String firstName, String surname, String email, String password, Locale locale){
        User user = register(firstName, surname, email,
                password, locale);
        if(user == null) return false;
        Optional<Role> role = roleService.getByName("USER");
        if(!role.isPresent()) throw new RoleNotFoundException();
        userRoleService.create(user, role.get());
        emailService.registration(user, locale);
        return true;
    }

//    @Override
//    public void updateImage(long userId, long imageId) {
//        userDao.updateImage(userId, imageId);
//    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Optional<User> findById(long userId) {
        return userDao.findById(userId);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}
