package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
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
public class CommentHibernateDao implements CommentDao {

    private static final int COMMENT_PAGE_SIZE = 5;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment create(User user, Product product, String message) {
        LocalDateTime dateTime = LocalDateTime.now();
        final Comment comment = new Comment(message, user, product, dateTime);
        em.persist(comment);
        return comment;
    }

    @Override
    public Pagination<Comment> getCommentsForProduct(long productId, int page) {
        Query query = em.createNativeQuery("SELECT id FROM comments WHERE productId = :productId ORDER BY datetime DESC LIMIT :limit OFFSET :offset");

        query.setParameter("productId", productId);
        query.setParameter("limit", COMMENT_PAGE_SIZE);
        query.setParameter("offset", (page-1)*COMMENT_PAGE_SIZE);

        List<Long> comments = new ArrayList<>();

        for(Object o : query.getResultList()) {
            comments.add(((BigInteger) o).longValue());
        }

        if(comments.isEmpty())
            return new Pagination<>(new ArrayList<>(), (long) page,
                    0);

        Query countQuery = em.createNativeQuery("SELECT COUNT(*) FROM comments WHERE productid = :productId");
        countQuery.setParameter("productId", productId);
        int count = ((BigInteger)countQuery.getSingleResult()).intValue();
        System.out.println("count!!! " + count);
        final TypedQuery<Comment> jpaQuery = em.createQuery("SELECT DISTINCT c FROM Comment c WHERE c.id IN :comments", Comment.class);
        jpaQuery.setParameter("comments", comments);
        return new Pagination<>(jpaQuery.getResultList(), (long) page, (count + COMMENT_PAGE_SIZE - 1)/COMMENT_PAGE_SIZE);
    }

    @Override
    public Comment getById(long commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public Comment replyComment(long parentId, String message) {
        Comment comment = em.find(Comment.class, parentId);
        comment.setReply(message);
        return comment;

    }


}
