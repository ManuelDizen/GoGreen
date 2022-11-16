package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface CommentService {
    Comment create(long productId, String message);
    Pagination<Comment> getCommentsForProduct(long productId, int page);
    Comment getById(long id);
    void replyComment(long parentId, String message);

}
