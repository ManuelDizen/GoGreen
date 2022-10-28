package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PasswordDao;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Repository
public class PasswordHibernateDao implements PasswordDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Token create(String passToken, User user) {
        LocalDateTime dateTime = LocalDateTime.now();
        Token token = new Token(passToken, user, dateTime);
        em.persist(token);
        return token;
    }
}
