package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class SellerJdbcDaoTest {

    private long userId;
    private String userMail;
    private static final String PHONE = "11111111";
    private static final String ADDRESS = "Address";
    private static final String FIRSTNAME = "John";
    private static final String SURNAME = "Doe";
    private static final String EMAIL = "foo@bar.edu.ar";
    private static final String PASSWORD = "secret";
    private static final Locale LOCALE = new Locale("es");

    private static final long AREAID = 1;

    @Autowired
    private SellerJdbcDao dao;

    @Autowired
    private UserJdbcDao userDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;

    private Map<String, Object> createSeller() {
        final Map<String, Object> values = new HashMap<>();
        User newUser = userDao.create(FIRSTNAME, SURNAME, EMAIL, PASSWORD, LOCALE);
        this.userId = newUser.getId();
        this.userMail = newUser.getEmail();
        values.put("userId", newUser.getId());
        values.put("phone", PHONE);
        values.put("address", ADDRESS);
        values.put("areaId", AREAID);
        return values;
    }

    @Before
    public void setUp() {
        this.dao = new SellerJdbcDao(ds);
        this.userDao = new UserJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("sellers")
                .usingGeneratedKeyColumns("id");

        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"sellers");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"users");

    }

    @Test
    public void testCreate() {
        User newUser = userDao.create(FIRSTNAME, SURNAME, EMAIL, PASSWORD, LOCALE);
        Seller newSeller = dao.create(newUser.getId(), PHONE, ADDRESS, AREAID);
        assertEquals(newUser.getId(), newSeller.getUserId());
        assertEquals(PHONE, newSeller.getPhone());
        assertEquals(ADDRESS, newSeller.getAddress());
    }

    @Test
    public void testFindById() {
        final Map<String, Object> values = createSeller();
        final Number sellerId = insert.executeAndReturnKey(values);
        final Optional<Seller> maybeSeller = dao.findById(sellerId.longValue());
        assertTrue(maybeSeller.isPresent());
        assertEquals(maybeSeller.get().getPhone(), PHONE);
        assertEquals(maybeSeller.get().getAddress(), ADDRESS);
    }

    @Test
    public void testFindByUserId() {
        final Map<String, Object> values = createSeller();
        insert.execute(values);
        final Optional<Seller> maybeSeller = dao.findByUserId(userId);
        assertTrue(maybeSeller.isPresent());
        assertEquals(maybeSeller.get().getPhone(), PHONE);
        assertEquals(maybeSeller.get().getAddress(), ADDRESS);
    }

    @Test
    public void testFindByMail() {
        final Map<String, Object> values = createSeller();
        insert.execute(values);
        final Optional<Seller> maybeSeller = dao.findByMail(userMail);
        assertTrue(maybeSeller.isPresent());
        assertEquals(maybeSeller.get().getPhone(), PHONE);
        assertEquals(maybeSeller.get().getAddress(), ADDRESS);
    }




}
