package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
@Service
public class SellerServiceImpl implements SellerService {
    private final SellerDao sd;
    private final UserDao ud;

    @Autowired

    public SellerServiceImpl(final SellerDao sd, final UserDao ud) {
        this.sd = sd;
        this.ud = ud;
    }

    @Override
    public Seller create(long userid, String phone, String address) {
        return sd.create(userid, phone, address);
    }

    @Override
    public Optional<Seller> findById(long id) {
        return sd.findById(id);
    }

    @Override
    public Optional<Seller> findByUserId(long userId) {
        return sd.findByUserId(userId);
    }

    @Override
    public Optional<Seller> findByMail(String mail) {
        return sd.findByMail(mail);
    }

    @Override
    public List<Seller> findByName(String name) {
        return sd.findByName(name);
    }

    @Override
    public Optional<Seller> findByPhone(String phone) {
        return sd.findByPhone(phone);
    }

    @Override
    public List<Seller> getAll() {
        return sd.getAll();
    }

    @Override
    public String getEmail(long userid) {
        Optional<User> maybeUser = ud.findById(userid);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getEmail();
        }
        return null;
    }

    @Override
    public String getName(long userid) {
        Optional<User> maybeUser = ud.findById(userid);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getFirstName();
        }
        return null;
    }

    @Override
    public String getSurname(long userid) {
        Optional<User> maybeUser = ud.findById(userid);
        if(maybeUser.isPresent()) {
            User user = maybeUser.get();
            return user.getSurname();
        }
        return null;
    }

    @Override
    public Locale getLocale(long userid) {
        Optional<User> maybeUser = ud.findById(userid);
        if(maybeUser.isPresent()){
            User user = maybeUser.get();
            return user.getLocale();
        }
        return null;
    }
}
