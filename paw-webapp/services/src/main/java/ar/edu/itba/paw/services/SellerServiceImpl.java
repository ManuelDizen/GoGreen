package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
@Service
public class SellerServiceImpl implements SellerService {
    private final SellerDao sellerDao;
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;
    @Autowired

    public SellerServiceImpl(final SellerDao sellerDao, final UserService userService, RoleService roleService, UserRoleService userRoleService, EmailService emailService) {
        this.sellerDao = sellerDao;
        this.userService = userService;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public Seller create(long userid, String phone, String address, Area area) {
        Optional<User> maybeUser = userService.findById(userid);
        if(!maybeUser.isPresent()) throw new UserNotFoundException();
        User user = maybeUser.get();
        return sellerDao.create(user, phone, address, area);
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
    public List<Seller> getAll() {
        return sellerDao.getAll();
    }

    @Override
    public String getEmail(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(!maybeUser.isPresent()) throw new UserNotFoundException();
        User user = maybeUser.get();
        return user.getEmail();
    }

    @Override
    public String getName(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(!maybeUser.isPresent()) throw new UserNotFoundException();
        User user = maybeUser.get();
        return user.getFirstName();
    }

    @Override
    public String getSurname(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(!maybeUser.isPresent()) throw new UserNotFoundException();
        User user = maybeUser.get();
        return user.getSurname();
    }

    @Override
    public Locale getLocale(long userid) {
        Optional<User> maybeUser = userService.findById(userid);
        if(!maybeUser.isPresent()) throw new UserNotFoundException();
        User user = maybeUser.get();
        return user.getLocale();
    }

    @Transactional
    @Override
    public void registerSeller(String firstName, String surname,
                String email, String password, Locale locale, String phone,
                        String address, Area area){
        User user = userService.register(firstName, surname, email, password, locale);
        if(user == null) throw new UserRegisterException();
        Seller seller = create(user.getId(), phone, address, area);
        if(seller == null) throw new SellerRegisterException();
        Optional<Role> role = roleService.getByName("SELLER");
        if(!role.isPresent()) throw new RoleNotFoundException();
        user.addRole(role.get());
        emailService.registration(user, user.getLocale());
    }

    public Pagination<Seller> filter(String name, long areaId, boolean favorite, int page,
                              long userId){
        Area area = Area.getById(areaId);
        Pagination<Seller> toReturn = sellerDao.filter(name, area, favorite, page, userId);
        // sortSellers(toReturn, sortType);
        return toReturn;
    }

    @Override
    public String getProfileUrl(){
        User user = userService.getLoggedUser();
        if(user == null){
            throw new UnauthorizedRoleException();
        }
        if(findByMail(user.getEmail()).isPresent()){
            return "/sellerProfile";
        }
        return "/userProfile";
    }
}
