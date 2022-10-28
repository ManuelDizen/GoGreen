package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.FavoriteDao;
import ar.edu.itba.paw.models.Favorite;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class FavoriteHibernateDao implements FavoriteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addFavorite(User user, Seller seller) {
        final Favorite favorite = new Favorite(user, seller);
        em.persist(favorite);
    }

    @Override
    public void removeFavorite(User user, Seller seller) {
        final TypedQuery<Favorite> query = em.createQuery("FROM Favorite AS f WHERE " +
                "f.user = :user AND " +
                "f.seller = :seller",
                Favorite.class);
        query.setParameter("user", user);
        query.setParameter("seller", seller);
        em.remove(query.getResultList().stream().findFirst().orElse(null));
    }

    @Override
    public List<Favorite> getByUserId(long userId) {
        final TypedQuery<Favorite> query = em.createQuery("FROM Favorite AS f WHERE " +
                "f.user.id = :userId", Favorite.class);
        return query.getResultList();
    }
}
