package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.EcotagDao;
import ar.edu.itba.paw.models.Ecotag;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

//TODO: Is this DAO necessary? "add" method should never be used?

public class EcotagHibernateDao implements EcotagDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Ecotag tag, long productId) {
    }

    @Override
    public List<Ecotag> getTagsFromProduct(long productId) {
        /*final TypedQuery<Ecotag> query = em.createQuery("FROM tags_to_products WHERE productId = :productId",
                Ecotag.class);
        query.setParameter("productId", productId);
        return query.getResultList();*/
        return null;
    }
}
