package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ArticleDao;
import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Seller;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleHibernateDao implements ArticleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Article create(Seller seller, String message, Image image, LocalDateTime dateTime) {
        // Should I delete completely?
        final Article article = new Article(image, message, seller, dateTime);
        em.persist(article);
        return article;
    }

    @Override
    public void delete(Long id) {
        em.remove(em.find(Article.class, id));
    }

    @Override
    public Optional<Article> getById(Long id) {
        return Optional.ofNullable(em.find(Article.class, id));
    }

    @Override
    public List<Article> getBySellerId(Long sellerId) {
        final TypedQuery<Article> query = em.createQuery("FROM Article WHERE seller.id = :sellerId " +
                "ORDER BY datetime DESC", Article.class);
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }
}
