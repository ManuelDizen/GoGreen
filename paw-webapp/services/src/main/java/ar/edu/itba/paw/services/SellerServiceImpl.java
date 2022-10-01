package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
@Service
public class SellerServiceImpl implements SellerService {
    private final SellerDao sellerDao;
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final EmailService emailService;
    @Autowired

    public SellerServiceImpl(final SellerDao sellerDao, final UserService userService, RoleService roleService, UserRoleService userRoleService, EmailService emailService) {
        this.sellerDao = sellerDao;
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService = userRoleService;
        this.emailService = emailService;
    }

    @Override
    public Seller create(long userid, String phone, String address) {
        return sellerDao.create(userid, phone, address);
    }

    @Override
    public Optional<Seller> findById(long id) {
        return sellerDao.findById(id);
    }

    @Override
    public Optional<Seller> findByUserId(long userId) {
        return sellerDao.findByUserId(userId);
    }

    @Override
    public Optional<Seller> findByMail(String mail) {
        return sellerDao.findByMail(mail);
    }

    @Override
    public List<Seller> findByName(String name) {
        return sellerDao.findByName(name);
    }

    @Override
    public Optional<Seller> findByPhone(String phone) {
        return sellerDao.findByPhone(phone);
    }

    @Override
    public List<Seller> getAll() {
        return sellerDao.getAll();
    }

    @Override
    public String getEmail(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getEmail();
        }
        return null;
    }

    @Override
    public String getName(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getFirstName();
        }
        return null;
    }

    @Override
    public String getSurname(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getSurname();
        }
        return null;
    }

    @Override
    public Locale getLocale(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(maybeUser.isPresent()){
            User user = maybeUser.get();
            return user.getLocale();
        }
        return null;
    }

    @Override
    public Boolean registerSeller(String firstName, String surname,
                String email, String password, Locale locale, String phone,
                        String address){
        User user = userService.register(firstName, surname, email, password, locale);
        if(user == null) return false;
        Seller seller = create(user.getId(), phone, address);
        if(seller == null) return false;
        Optional<Role> role = roleService.getByName("SELLER");
        if(!role.isPresent()) return false;
        userRoleService.create(user.getId(), role.get().getId());
        emailService.registration(user, user.getLocale());
        return true;
    }
}
