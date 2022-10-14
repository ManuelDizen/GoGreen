package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.EcotagDao;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//TODO: Is this DAO necessary? "add" method should never be used?
@Repository
public class EcotagHibernateDao implements EcotagDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Ecotag tag, long productId) {
    }

    @Override
    public List<Ecotag> getTagsFromProduct(long productId) {
        final TypedQuery<Product> query = em.createQuery("FROM Product WHERE id = :productId", Product.class);
        query.setParameter("productId", productId);
        Optional<Product> product = query.getResultList().stream().findFirst();
        if(product.isPresent()) return product.get().getTagList();
        return null;
    }
}
