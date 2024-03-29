package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Comment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private UserService userService;
    @Mock
    private ProductService productService;
    @Mock
    private CommentDao commentDao;
    @Mock
    private EmailService emailService;

    @Test
    public void testCreateComment() {
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(commentDao.create(any(), any(), anyString())).thenReturn(AUX_COMMENT);
        doNothing().when(emailService).newComment(any(), any(), anyString());

        Comment comment = null;
        try{
            comment = commentService.create(AUX_PRODUCT.getProductId(), AUX_COMMENT_MSG);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Error creating comment: " + e.getMessage());
        }
        Assert.assertNotNull(comment);
        Assert.assertEquals(AUX_COMMENT_MSG, comment.getMessage());
    }
}
