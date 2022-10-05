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
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;

    private SimpleJdbcInsert insert2;

    @Before
    public void setUp() {
        this.dao = new SellerJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("sellers")
                .usingGeneratedKeyColumns("id");
        this.insert2 = new SimpleJdbcInsert(ds).withTableName("users")
                .usingGeneratedKeyColumns("id");

        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"sellers");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"users");

    }

    @Test
    public void testCreate() {
        final Map<String, Object> values = new HashMap<>();
        values.put("firstName", FIRSTNAME);
        values.put("surname", SURNAME);
        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("locale", LOCALE);
        final long userId = insert2.executeAndReturnKey(values).longValue();
        values.put("userId", userId);
        values.put("phone", PHONE);
        values.put("address", ADDRESS);
        values.put("areaId", AREAID);
        Seller newSeller = dao.create(userId, PHONE, ADDRESS, AREAID);
        assertEquals(userId, newSeller.getUserId());
        assertEquals(PHONE, newSeller.getPhone());
        assertEquals(ADDRESS, newSeller.getAddress());
    }

    @Test
    public void testFindById() {
        final Map<String, Object> values = new HashMap<>();
        values.put("firstName", FIRSTNAME);
        values.put("surname", SURNAME);
        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("locale", LOCALE);
        final long userId = insert2.executeAndReturnKey(values).longValue();
        final Map<String, Object> values2 = new HashMap<>();;
        values2.put("userId", userId);
        values2.put("phone", PHONE);
        values2.put("address", ADDRESS);
        values2.put("areaId", AREAID);
        final Number sellerId = insert.executeAndReturnKey(values2);
        final Optional<Seller> maybeSeller = dao.findById(sellerId.longValue());
        assertTrue(maybeSeller.isPresent());
        assertEquals(maybeSeller.get().getPhone(), PHONE);
        assertEquals(maybeSeller.get().getAddress(), ADDRESS);
    }

    @Test
    public void testFindByUserId() {
        final Map<String, Object> values = new HashMap<>();
        values.put("firstName", FIRSTNAME);
        values.put("surname", SURNAME);
        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("locale", LOCALE);
        final long userId = insert2.executeAndReturnKey(values).longValue();
        final Map<String, Object> values2 = new HashMap<>();;
        values2.put("userId", userId);
        values2.put("phone", PHONE);
        values2.put("address", ADDRESS);
        values2.put("areaId", AREAID);
        insert.execute(values2);
        final Optional<Seller> maybeSeller = dao.findByUserId(userId);
        assertTrue(maybeSeller.isPresent());
        assertEquals(maybeSeller.get().getPhone(), PHONE);
        assertEquals(maybeSeller.get().getAddress(), ADDRESS);
    }

    @Test
    public void testFindByMail() {
        final Map<String, Object> values = new HashMap<>();
        values.put("firstName", FIRSTNAME);
        values.put("surname", SURNAME);
        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("locale", LOCALE);
        final long userId = insert2.executeAndReturnKey(values).longValue();
        final Map<String, Object> values2 = new HashMap<>();;
        values2.put("userId", userId);
        values2.put("phone", PHONE);
        values2.put("address", ADDRESS);
        values2.put("areaId", AREAID);
        insert.execute(values2);
        final Optional<Seller> maybeSeller = dao.findByMail(EMAIL);
        assertTrue(maybeSeller.isPresent());
        assertEquals(maybeSeller.get().getPhone(), PHONE);
        assertEquals(maybeSeller.get().getAddress(), ADDRESS);
    }




}
