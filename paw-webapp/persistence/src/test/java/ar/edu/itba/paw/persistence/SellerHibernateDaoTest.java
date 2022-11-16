package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Area;
import ar.edu.itba.paw.models.Pagination;
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
public class SellerHibernateDaoTest {
    @Autowired
    private SellerHibernateDao sellerHibernateDao;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp(){}

    @Test
    public void testCreate(){
        Seller seller = TestHelper.sellerCreateHelperFunction(em, AUX_USER_FOR_SELLER, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        assertNotNull(seller);
        assertEquals(SELLER_ADDRESS, seller.getAddress());
    }

    @Test
    public void testFindById(){
        Seller seller = TestHelper.sellerCreateHelperFunction(em, AUX_USER_FOR_SELLER, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Optional<Seller> maybeSeller = sellerHibernateDao.findById(seller.getId());
        assertTrue(maybeSeller.isPresent());
        seller = maybeSeller.get();
        assertEquals(SELLER_PHONE, seller.getPhone());
        assertEquals(SELLER_ADDRESS, seller.getAddress());
        assertEquals(SELLER_AREA, seller.getArea());
    }

    @Test
    public void testAttemptFindByIdWhenNotExists(){
        Optional<Seller> maybeSeller = sellerHibernateDao.findById(SELLER_ID);
        assertFalse(maybeSeller.isPresent());
    }

    @Test
    public void testFindByMail(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Optional<Seller> maybeSeller = sellerHibernateDao.findByMail(seller.getUser().getEmail());
        assertTrue(maybeSeller.isPresent());
        seller = maybeSeller.get();
        assertEquals(SELLER_PHONE, seller.getPhone());
        assertEquals(SELLER_ADDRESS, seller.getAddress());
        assertEquals(SELLER_AREA, seller.getArea());
    }

    @Test
    public void testAttemptFindByEmailWhenNotExists(){
        Optional<Seller> maybeSeller = sellerHibernateDao.findByMail(AUX_U_FOR_SELLER_EMAIL);
        assertFalse(maybeSeller.isPresent());
    }

    @Test
    public void testFindByUserId(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);
        Seller seller = TestHelper.sellerCreateHelperFunction(em, user, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);
        Optional<Seller> maybeSeller = sellerHibernateDao.findByUserId(seller.getUser().getId());
        assertTrue(maybeSeller.isPresent());
        seller = maybeSeller.get();
        assertEquals(SELLER_PHONE, seller.getPhone());
        assertEquals(SELLER_ADDRESS, seller.getAddress());
        assertEquals(SELLER_AREA, seller.getArea());
    }

    @Test
    public void testAttemptFindByUserIdWhenNotExists(){
        Optional<Seller> maybeSeller = sellerHibernateDao.findByUserId(AUX_U_FOR_SELLER_ID);
        assertFalse(maybeSeller.isPresent());
    }

    /*@Test
    public void testFilterByArea(){
        User user1 = TestHelper.userCreateHelperFunction(em, AUX_U_FOR_SELLER_FIRSTNAME,
                AUX_U_FOR_SELLER_SURNAME, AUX_U_FOR_SELLER_EMAIL, AUX_U_FOR_SELLER_PASSWORD,
                AUX_U_FOR_SELLER_LOCALE);
        Seller seller1 = TestHelper.sellerCreateHelperFunction(em, user1, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);

        User user2 = TestHelper.userCreateHelperFunction(em, AUX_U_FOR_SELLER_FIRSTNAME_2,
                AUX_U_FOR_SELLER_SURNAME_2, AUX_U_FOR_SELLER_EMAIL_2, AUX_U_FOR_SELLER_PASSWORD_2,
                AUX_U_FOR_SELLER_LOCALE_2);
        Seller seller2 = TestHelper.sellerCreateHelperFunction(em, user2,
                SELLER_PHONE_2, SELLER_ADDRESS_2, SELLER_AREA_2);

        User user3 = TestHelper.userCreateHelperFunction(em, AUX_U_FOR_SELLER_FIRSTNAME_3,
                AUX_U_FOR_SELLER_SURNAME_3, AUX_U_FOR_SELLER_EMAIL_3, AUX_U_FOR_SELLER_PASSWORD_3,
                AUX_U_FOR_SELLER_LOCALE_3);
        Seller seller3 = TestHelper.sellerCreateHelperFunction(em, user3,
                SELLER_PHONE_3, SELLER_ADDRESS_3, SELLER_AREA_3);

        Pagination<Seller> sellersByArea = sellerHibernateDao.filter("", Area.DEVOTO,
                false, 1, 0); // UserId is irrelevant as favorite is false
        assertEquals(1, sellersByArea.getItems().size());
        assertEquals(seller1, sellersByArea.getItems().get(0));
    }

    @Test
    public void testFilterByName(){
        User user1 = TestHelper.userCreateHelperFunction(em, AUX_U_FOR_SELLER_FIRSTNAME,
                AUX_U_FOR_SELLER_SURNAME, AUX_U_FOR_SELLER_EMAIL, AUX_U_FOR_SELLER_PASSWORD,
                AUX_U_FOR_SELLER_LOCALE);
        Seller seller1 = TestHelper.sellerCreateHelperFunction(em, user1, SELLER_PHONE,
                SELLER_ADDRESS, SELLER_AREA);

        User user2 = TestHelper.userCreateHelperFunction(em, AUX_U_FOR_SELLER_FIRSTNAME_2,
                AUX_U_FOR_SELLER_SURNAME_2, AUX_U_FOR_SELLER_EMAIL_2, AUX_U_FOR_SELLER_PASSWORD_2,
                AUX_U_FOR_SELLER_LOCALE_2);
        Seller seller2 = TestHelper.sellerCreateHelperFunction(em, user2,
                SELLER_PHONE_2, SELLER_ADDRESS_2, SELLER_AREA_2);

        User user3 = TestHelper.userCreateHelperFunction(em, AUX_U_FOR_SELLER_FIRSTNAME_3,
                AUX_U_FOR_SELLER_SURNAME_3, AUX_U_FOR_SELLER_EMAIL_3, AUX_U_FOR_SELLER_PASSWORD_3,
                AUX_U_FOR_SELLER_LOCALE_3);
        Seller seller3 = TestHelper.sellerCreateHelperFunction(em, user3,
                SELLER_PHONE_3, SELLER_ADDRESS_3, SELLER_AREA_3);

        Pagination<Seller> sellersByName = sellerHibernateDao.filter("Leonardo", null,
                false, 1, 0); // UserId is irrelevant as favorite is false
        assertEquals(1, sellersByName.getItems().size());
        assertEquals(seller2, sellersByName.getItems().get(0));
    }*/
}
