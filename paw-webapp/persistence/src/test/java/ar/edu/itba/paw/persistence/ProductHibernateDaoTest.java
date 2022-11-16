package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
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

import java.util.*;

import static ar.edu.itba.paw.persistence.TestDaosResources.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ProductHibernateDaoTest {
    @Autowired
    private ProductHibernateDao productHibernateDao;

    @PersistenceContext
    private EntityManager em;


    @Before
    public void setUp() {}

    @Test
    public void testCreate(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Product product = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT.getId(), PRODUCT_NAME, PRODUCT_DESC, PRODUCT_STOCK,
                PRODUCT_PRICE, image);
        assertNotNull(product);
        assertEquals(PRODUCT_NAME, product.getName());
    }

    @Test
    public void testFindById(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Product product = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT.getId(), PRODUCT_NAME, PRODUCT_DESC, PRODUCT_STOCK,
                PRODUCT_PRICE, image);
        Optional<Product> maybeProduct = productHibernateDao.getById(product.getProductId());
        assertTrue(maybeProduct.isPresent());
        product = maybeProduct.get();
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(PRODUCT_DESC, product.getDescription());
        assertEquals(PRODUCT_STOCK, product.getStock());
        assertEquals(PRODUCT_PRICE, product.getPrice());
    }

    @Test
    public void testFindByIdWhenNotExists(){
        Optional<Product> maybeProduct = productHibernateDao.getById(PRODUCT_ID);
        assertFalse(maybeProduct.isPresent());
    }

    @Test
    public void testFindByName(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Product product = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT.getId(), PRODUCT_NAME, PRODUCT_DESC, PRODUCT_STOCK,
                PRODUCT_PRICE, image);
        Optional<Product> maybeProduct = productHibernateDao.getByName(product.getName());
        assertTrue(maybeProduct.isPresent());
        product = maybeProduct.get();
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(PRODUCT_DESC, product.getDescription());
        assertEquals(PRODUCT_STOCK, product.getStock());
        assertEquals(PRODUCT_PRICE, product.getPrice());
    }

    @Test
    public void testFindByNameWhenNotExists(){
        Optional<Product> maybeProduct = productHibernateDao.getByName(PRODUCT_NAME);
        assertFalse(maybeProduct.isPresent());
    }

    /*@Test
    public void testFilterByName(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Product product1 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT.getId(), PRODUCT_NAME, PRODUCT_DESC, PRODUCT_STOCK,
                PRODUCT_PRICE, image);
        Product product2 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT_2.getId(), PRODUCT_NAME_2, PRODUCT_DESC_2, PRODUCT_STOCK_2,
                PRODUCT_PRICE_2, image);
        Product product3 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT_3.getId(), PRODUCT_NAME_3, PRODUCT_DESC_3, PRODUCT_STOCK_3,
                PRODUCT_PRICE_3, image);

        List<Product> productsByName = productHibernateDao.filter("meat", 0,
                new ArrayList<>(), -1, 0);
        assertEquals(1, productsByName.size());
        assertEquals(product1, productsByName.get(0));
    }

    @Test
    public void testFilterByCategory(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Product product1 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT.getId(), PRODUCT_NAME, PRODUCT_DESC, PRODUCT_STOCK,
                PRODUCT_PRICE, image);
        Product product2 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT_2.getId(), PRODUCT_NAME_2, PRODUCT_DESC_2, PRODUCT_STOCK_2,
                PRODUCT_PRICE_2, image);
        Product product3 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT_3.getId(), PRODUCT_NAME_3, PRODUCT_DESC_3, PRODUCT_STOCK_3,
                PRODUCT_PRICE_3, image);

        List<Product> productsByName = productHibernateDao.filter("",
                Category.CLOTHING.getId(), new ArrayList<>(), -1, 0);
        assertEquals(1, productsByName.size());
        assertEquals(product2, productsByName.get(0));
    }

    @Test
    public void testFilterByPrice(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Image image = TestHelper.imageCreateHelperFunction(em, BYTES_FOR_PROD_IMAGE);
        Product product1 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT.getId(), PRODUCT_NAME, PRODUCT_DESC, PRODUCT_STOCK,
                PRODUCT_PRICE, image);
        Product product2 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT_2.getId(), PRODUCT_NAME_2, PRODUCT_DESC_2, PRODUCT_STOCK_2,
                PRODUCT_PRICE_2, image);
        Product product3 = TestHelper.productCreateHelperFunction(em, seller,
                PRODUCT_CAT_3.getId(), PRODUCT_NAME_3, PRODUCT_DESC_3, PRODUCT_STOCK_3,
                PRODUCT_PRICE_3, image);

        List<Product> productsByName = productHibernateDao.filter("",
                0, new ArrayList<>(), 200, 0);
        assertEquals(1, productsByName.size());
        assertEquals(product3, productsByName.get(0));
    }*/
}
