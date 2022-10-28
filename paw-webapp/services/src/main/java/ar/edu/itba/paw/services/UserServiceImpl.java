package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    @Override
    public Boolean registerUser(String firstName, String surname, String email, String password, Locale locale){
        User user = register(firstName, surname, email, password, locale);
        if(user == null) return false;
        Optional<Role> role = roleService.getByName("USER");
        if(!role.isPresent()) throw new RoleNotFoundException();
        user.addRole(role.get());
        //userRoleService.create(user, role.get());
        emailService.registration(user, locale);
        return true;
    }

//    @Override
//    public void updateImage(long userId, long imageId) {
//        userDao.updateImage(userId, imageId);
//    }addRole

    @Transactional
    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Transactional
    @Override
    public Optional<User> findById(long userId) {
        return userDao.findById(userId);
    }

    @Transactional
    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    @Override
    public void changePassword(long userId, String newPassword) {
        Optional<User> maybeUser = userDao.findById(userId);
        if(!maybeUser.isPresent())
            throw new UserNotFoundException();

        User user = maybeUser.get();
        user.setPassword(encoder.encode(newPassword));
    }


}
