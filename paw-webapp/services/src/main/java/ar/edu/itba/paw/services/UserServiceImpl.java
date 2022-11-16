package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EmailService emailService;
    private final ImageService imageService;
    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder encoder,
                           final RoleService roleService, final EmailService emailService,
                           final ImageService imageService){
        this.userDao = userDao;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.imageService = imageService;
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
    public void changePassword(long userId, String newPassword) {
        Optional<User> maybeUser = userDao.findById(userId);
        if(!maybeUser.isPresent())
            throw new UserNotFoundException();
        User user = maybeUser.get();
        user.setPassword(encoder.encode(newPassword));
    }

    @Transactional
    @Override
    public void toggleNotifications(){
        User user = getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        user.toggleNotifications();
    }

    @Transactional
    @Override
    public boolean isSeller(long userId) {
        return isRole(userId, "SELLER");
    }

    private boolean isRole(long userId, String role) {
        Optional<User> user = userDao.findById(userId);
        if(!user.isPresent()) throw new UserNotFoundException();
        Optional<Role> userRole = roleService.getByName(role);
        if(!userRole.isPresent()) throw new RoleNotFoundException();
        return user.get().getRoles().contains(userRole.get());
    }

    @Transactional
    @Override
    public void setProfilePic(byte[] image){
        User user = getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        Image img = user.getImage();
        if(img == null){
            if(image != null && image.length > 0){
                img = imageService.create(image);
            }
            user.setImage(img);
        }
        else{
            img.setSource(image);
        }
    }

    @Transactional
    @Override
    public void deleteProfilePic(){
        User user = getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        user.deleteImage();
    }

    @Override
    public User getLoggedUser(){
        Authentication auth = getAuthentication();
        if(auth != null){
            Optional<User> user = findByEmail(auth.getName());
            return user.orElse(null);
        }
        return null;
    }

    @Override
    public String getLoggedEmail(){
        Authentication auth = getAuthentication();
        if(auth != null) {
            Optional<User> user = findByEmail(auth.getName());
            return user.map(User::getEmail).orElse(null);
        }
        return null;
    }
    @Override
    public boolean isLoggedUser(){
        User user = getLoggedUser();
        return user != null && user.getRoles().contains("SELLER");
    }

    @Override
    public boolean isLoggedSeller(){
        User user = getLoggedUser();
        return user != null && user.getRoles().contains("USER");
    }
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
