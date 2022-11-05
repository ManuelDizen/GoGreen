package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRoleHibernateDao implements UserRoleDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Set<Role> getById(long userId) {

        Optional<User> user = em.createQuery("SELECT u from User u JOIN FETCH u.roles WHERE u.id = :userId",
                User.class).setParameter("userId", userId).getResultList().stream().findFirst();
        return user.orElseThrow(UserNotFoundException::new).getRoles();

        //TODO: Refactor
        /*final TypedQuery<UserRole> query = em.createQuery("FROM UserRole WHERE user.id = :userId",
                UserRole.class);
        query.setParameter("userId", userId);
        return query.getResultList();*/
    }

    @Override
    public UserRole create(User user, Role role) {
        final UserRole userRole = new UserRole(user, role);
        em.persist(userRole);
        return userRole;
    }
}
