package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PasswordDao;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PasswordHibernateDao implements PasswordDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Token create(String passToken, User user, LocalDateTime dateTime) {
        Token token = new Token(passToken, user, dateTime);
        em.persist(token);
        return token;
    }

    @Override
    public Optional<Token> getByUserId(User user) {
        final TypedQuery<Token> query = em.
                createQuery("from Token as token where token.user = :user", Token.class);
        query.setParameter("user", user);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Token> getByToken(String token) {
        final TypedQuery<Token> query = em.
                createQuery("from Token as token where token.passToken = :token", Token.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }


}
