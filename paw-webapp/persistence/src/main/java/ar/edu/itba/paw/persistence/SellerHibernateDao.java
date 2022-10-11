package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.models.Seller;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class SellerHibernateDao implements SellerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Seller create(long userId, String phone, String address, long areaId) {
        final Seller seller = new Seller(userId, phone, address, areaId);
        em.persist(seller);
        return seller;
    }

    @Override
    public Optional<Seller> findById(long id) {
        return Optional.ofNullable(em.find(Seller.class, id));
    }

    @Override
    public Optional<Seller> findByUserId(long userId) {
        final TypedQuery<Seller> query = em.createQuery("FROM Seller WHERE userid = :userid", Seller.class);
        query.setParameter("userid", userId);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Optional<Seller> findByMail(String mail) {
        final TypedQuery<Seller> query = em.createQuery("FROM Seller where userid = " +
                        "(SELECT id from User where email = :email)", Seller.class);
        query.setParameter("email", mail);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public List<Seller> getAll() {
        return em.createQuery("FROM Seller", Seller.class).getResultList();
    }
}
