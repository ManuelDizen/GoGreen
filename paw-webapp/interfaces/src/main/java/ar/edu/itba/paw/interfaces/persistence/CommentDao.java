package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface CommentDao {

    Comment create(User user, Product product, String message);

    List<Comment> getCommentsForProduct(long productId);

}
