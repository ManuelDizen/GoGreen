package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(final String name, final String surname, final String email, final String password,
                       final Locale locale) {
        final User user = new User(name, surname, email, password, locale);
        em.persist(user);
        return user;
    }


    @Override
    public Optional<User> findByEmail(final String email) {
        final TypedQuery<User> query =
                em.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
        //Hablo como JPA y siempre en terminos de mis models
    }

    @Override
    public Optional<User> findById(final long userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }

    @Override
    public List<User> getAll() {
        final TypedQuery<User> query = em.createQuery("FROM User", User.class);
        return query.getResultList();
    }
}
