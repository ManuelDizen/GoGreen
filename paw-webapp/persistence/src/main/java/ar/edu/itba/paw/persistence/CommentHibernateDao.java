package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentHibernateDao implements CommentDao {

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
    public List<Comment> getCommentsForProduct(long productId) {
        final TypedQuery<Comment> query = em.createQuery("FROM Comment AS c WHERE productId = :productId", Comment.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public Comment getById(long commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public void replyComment(long parentId, String message) {
        Comment comment = em.find(Comment.class, parentId);
        comment.setReply(message);
        //em.persist(comment);

    }


}
