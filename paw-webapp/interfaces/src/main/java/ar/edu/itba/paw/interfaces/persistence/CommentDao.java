package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface CommentDao {

    Comment create(User user, Product product, String message);

    Pagination<Comment> getCommentsForProduct(long productId, int page);

    Comment getById(long commentId);

    Comment replyComment(long parentId, String message);

}
