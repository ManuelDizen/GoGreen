package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Area;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.hibernate.AssertionFailure;

import javax.persistence.EntityManager;
import java.util.Locale;

public class TestHelper {
    private TestHelper(){throw new AssertionError();}
    static User userCreateHelperFunction(EntityManager em, String name, String surname, String email,
                                         String password, Locale locale){
        User user = new User(name, surname, email, password, locale);
        em.persist(user);
        return user;
    }

    static Seller sellerCreateHelperFunction(EntityManager em, User user, String phone,
                                             String address, Area area){
        Seller seller = new Seller(user, phone, address, area);
        em.persist(seller);
        return seller;
    }
}
