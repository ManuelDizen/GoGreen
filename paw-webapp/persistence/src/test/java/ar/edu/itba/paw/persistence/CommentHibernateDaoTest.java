package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Comment;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static ar.edu.itba.paw.persistence.TestDaosResources.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CommentHibernateDaoTest {

    @Autowired
    private CommentHibernateDao commentHibernateDao;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp(){}

    @Test
    public void testCreate(){
        Comment comment = TestHelper.commentCreateHelperFunction(em, AUX_USER_FOR_SELLER, AUX_PRODUCT,
                COMMENT_MSG, AUX_DATETIME);
        assertNotNull(comment);
        assertEquals(COMMENT_MSG, comment.getMessage());
    }

    @Test
    public void testFindById(){
        Comment comment = TestHelper.commentCreateHelperFunction(em, AUX_USER_FOR_SELLER, AUX_PRODUCT,
                COMMENT_MSG, AUX_DATETIME);
        Comment maybeComment = commentHibernateDao.getById(comment.getId());
        assertNotNull(maybeComment);
        assertEquals(COMMENT_MSG, comment.getMessage());
    }

    @Test
    public void testFindByIdWhenNotExists(){
        Comment comment = commentHibernateDao.getById(AUX_COMMENT_ID);
        assertNull(comment);
    }
}
