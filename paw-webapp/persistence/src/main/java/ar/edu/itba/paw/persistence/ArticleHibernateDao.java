package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ArticleDao;
import ar.edu.itba.paw.models.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleHibernateDao implements ArticleDao {

    private static final int NEWS_PAGE_SIZE = 5;
    @PersistenceContext
    private EntityManager em;

    @Override
    public Article create(Seller seller, String message, Image image, LocalDateTime dateTime) {
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
        final TypedQuery<Article> query = em.createQuery("FROM Article WHERE sellerid = :sellerId " +
                "ORDER BY datetime DESC", Article.class);
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }

    @Override
    public Pagination<Article> getBySellerId(Long sellerId, int page) {

        String str = "FROM news WHERE sellerid = :sellerId";

        Query query = em.createNativeQuery("SELECT * " + str + " ORDER BY datetime DESC LIMIT :limit OFFSET :offset", Article.class);

        query.setParameter("sellerId", sellerId);
        query.setParameter("limit", NEWS_PAGE_SIZE);
        query.setParameter("offset", (page-1)*NEWS_PAGE_SIZE);

        List<Article> articles = query.getResultList();

        if(articles.isEmpty())
            return new Pagination<>(new ArrayList<>(), (long) page,
                    0);

        Query countQuery = em.createNativeQuery("SELECT COUNT(*) " + str);
        countQuery.setParameter("sellerId", sellerId);
        int count = ((BigInteger)countQuery.getSingleResult()).intValue();

        return new Pagination<>(articles, (long) page, (count + NEWS_PAGE_SIZE - 1)/NEWS_PAGE_SIZE);
    }

    @Override
    public Pagination<Article> getForUser(long userId, int page) {

        String str = "FROM news WHERE sellerid IN (SELECT seller_id FROM favorites WHERE user_id = :userId)";

        Query query = em.createNativeQuery("SELECT * " + str + " ORDER BY datetime DESC LIMIT :limit OFFSET :offset", Article.class);

        query.setParameter("userId", userId);
        query.setParameter("limit", NEWS_PAGE_SIZE);
        query.setParameter("offset", (page-1)*NEWS_PAGE_SIZE);

        List<Article> articles = query.getResultList();

        if(articles.isEmpty())
            return new Pagination<>(new ArrayList<>(), (long) page,
                    0);

        Query countQuery = em.createNativeQuery("SELECT COUNT(*) " + str);
        countQuery.setParameter("userId", userId);
        int count = ((BigInteger)countQuery.getSingleResult()).intValue();

        return new Pagination<>(articles, (long) page, (count + NEWS_PAGE_SIZE - 1)/NEWS_PAGE_SIZE);
    }
}
