package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Article;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
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
public class ArticleHibernateDaoTest {

    @Autowired
    private ArticleHibernateDao articleHibernateDao;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp(){}

    @Test
    public void testCreate(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Article article = TestHelper.articleCreateHelperFunction(em, image, seller,
                ARTICLE_MESSAGE, AUX_DATETIME);
        assertNotNull(article);
        assertEquals(ARTICLE_MESSAGE, article.getMessage());
    }

    @Test
    public void testFindById(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Article article = TestHelper.articleCreateHelperFunction(em, image, seller,
                ARTICLE_MESSAGE, AUX_DATETIME);
        Optional<Article> maybeArticle = articleHibernateDao.getById(article.getId());
        assertTrue(maybeArticle.isPresent());
        article = maybeArticle.get();
        assertEquals(ARTICLE_MESSAGE, article.getMessage());
    }

    @Test
    public void testFindByIdWhenNotExists(){
        Optional<Article> maybeArticle = articleHibernateDao.getById(AUX_ARTICLE_ID);
        assertFalse(maybeArticle.isPresent());
    }
}
