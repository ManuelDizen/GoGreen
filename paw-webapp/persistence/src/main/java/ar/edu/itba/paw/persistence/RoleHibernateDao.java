package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleHibernateDao implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Role> getById(long id) {
        final TypedQuery<Role> query = em.createQuery("FROM Role as r WHERE r.id = :id", Role.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Role> getByName(String name) {
        final TypedQuery<Role> query = em.createQuery("FROM Role as r WHERE r.name = :name", Role.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst();
    }
}
