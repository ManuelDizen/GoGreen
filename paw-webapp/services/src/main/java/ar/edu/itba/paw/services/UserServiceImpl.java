package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserRegisterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final EmailService emailService;
    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder encoder,
                           RoleService roleService, EmailService emailService){
        this.userDao = userDao;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;

    }

    @Transactional
    @Override
    public User register(String firstName, String surname, String email, String password, Locale locale) {
        return userDao.create(firstName, surname, email, encoder.encode(password), locale);
    }

    @Transactional
    @Override
    public void registerUser(String firstName, String surname, String email, String password, Locale locale){
        User user = register(firstName, surname, email, password, locale);
        if(user == null) throw new UserRegisterException();
        Optional<Role> role = roleService.getByName("USER");
        if(!role.isPresent()) throw new RoleNotFoundException();
        user.addRole(role.get());
        emailService.registration(user, locale);

    }

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

    @Override
    public boolean isValidPassword(long userId, String oldPassword) {
        Optional<User> maybeUser = findById(userId);

        if(!maybeUser.isPresent())
            throw new UserNotFoundException();

        User user = maybeUser.get();

        return encoder.encode(oldPassword).equals(user.getPassword());
    }

    @Transactional
    @Override
    public void toggleNotifications(long userId){
        Optional<User> user = findById(userId);
        if(!user.isPresent()) throw new ForbiddenActionException();
        user.get().toggleNotifications();
    }

    @Transactional
    @Override
    public boolean isSeller(long userId) {
        return isRole(userId, "SELLER");
    }

    @Transactional
    @Override
    public boolean isBuyer(long userId) {
        return isRole(userId, "USER");
    }

    private boolean isRole(long userId, String role) {
        Optional<User> user = userDao.findById(userId);
        if(!user.isPresent()) throw new UserNotFoundException();
        Optional<Role> userRole = roleService.getByName(role);
        if(!userRole.isPresent()) throw new RoleNotFoundException();
        return user.get().getRoles().contains(userRole.get());
    }
}
