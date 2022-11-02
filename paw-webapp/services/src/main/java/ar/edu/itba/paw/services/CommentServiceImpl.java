package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
import ar.edu.itba.paw.interfaces.services.CommentService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final EmailService emailService;
    private static final int PAGESIZE = 10;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, EmailService emailService) {
        this.commentDao = commentDao;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public Comment create(User user, Product product, String message) {
        Comment comment = commentDao.create(user, product, message);
        if(comment == null) throw new RuntimeException(); //TODO: Make custom exc
        emailService.newComment(user, product, message);
        return comment;
    }

    @Override
    public List<List<Comment>> getCommentsForProduct(long productId) {
        List<Comment> allComments = commentDao.getCommentsForProduct(productId);
        Collections.reverse(allComments);
        List<List<Comment>> pageList = new ArrayList<>();

        int aux = 1;
        while(aux <= allComments.size()/PAGESIZE) {
            pageList.add(allComments.subList((aux-1)*PAGESIZE, aux*PAGESIZE));
            aux++;
        }
        if(allComments.size() % PAGESIZE != 0)
            pageList.add(allComments.subList((aux-1)*PAGESIZE, allComments.size()));
        if(allComments.size() == 0) pageList.add(new ArrayList<>());
        return pageList;

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
        //TODO: Check that reply creation was successful to send email
        emailService.replyComment(comment.getUser(), comment.getProduct(), comment.getReply());
    }
}
