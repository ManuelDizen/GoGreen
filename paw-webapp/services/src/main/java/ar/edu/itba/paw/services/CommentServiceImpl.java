package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Pagination;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.CommentNotFoundException;
import ar.edu.itba.paw.models.exceptions.ForbiddenActionException;
import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final EmailService emailService;
    private final UserService userService;
    private final ProductService productService;
    private static final int PAGESIZE = 10;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, EmailService emailService,
                              UserService userService, ProductService productService) {
        this.commentDao = commentDao;
        this.emailService = emailService;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    @Override
    public Comment create(long productId, String message) {
        User user = userService.getLoggedUser();
        if(user == null) throw new ForbiddenActionException();
        Optional<Product> maybeProduct = productService.getById(productId);
        if(!maybeProduct.isPresent()) throw new ProductNotFoundException();
        final Product product = maybeProduct.get();

        Comment comment = commentDao.create(user, product, message);
        if(comment == null) throw new CommentNotFoundException();
        emailService.newComment(user, product, message);
        return comment;
    }

    @Override
    public Pagination<Comment> getCommentsForProduct(long productId, int page) {
        return commentDao.getCommentsForProduct(productId, page);
    }

    @Transactional
    @Override
    public Comment getById(long id){
        return commentDao.getById(id);
    }

    @Transactional
    @Override
    public void replyComment(long parentId, String message) {
        Comment comment = commentDao.replyComment(parentId, message);
        if(comment != null)
            emailService.replyComment(comment.getUser(), comment.getProduct(), comment.getReply());
    }
}
