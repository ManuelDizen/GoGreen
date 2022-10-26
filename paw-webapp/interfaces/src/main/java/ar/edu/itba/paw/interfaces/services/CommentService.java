package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface CommentService {

    Comment create(User user, Product product, String message);

    List<Comment> getCommentsForProduct(long productId);

    void replyComment(long parentId, String message);

}
