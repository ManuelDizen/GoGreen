package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRoleHibernateDao implements UserRoleDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<UserRole> getById(long userId) {
        //TODO: Refactor
        final TypedQuery<UserRole> query = em.createQuery("FROM UserRole WHERE user.id = :userId", UserRole.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public UserRole create(User user, Role role) {
        final UserRole userRole = new UserRole(user, role);
        em.persist(userRole);
        return userRole;
    }
}
