package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.models.UserRole;
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
        //TODO: Define if a user can have more than one role (up to now its impossible)
        //  Still, this method will return "a collection" that will have only one element
        final TypedQuery<UserRole> query = em.createQuery("FROM UserRole WHERE userId = :userId", UserRole.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public UserRole create(long userId, long roleId) {
        final UserRole userRole = new UserRole(userId, roleId);
        em.persist(userRole);
        return userRole;
    }
}
