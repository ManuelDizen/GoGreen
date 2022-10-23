package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ImageHibernateDao implements ImageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Image create(byte[] source) {
        final Image image = new Image(source);
        em.persist(image);
        return image;
    }

    @Override
    public Optional<Image> getById(long id) {
        return Optional.ofNullable(em.find(Image.class, id));
    }
}
