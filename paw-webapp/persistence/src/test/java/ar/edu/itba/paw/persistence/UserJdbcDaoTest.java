package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class UserJdbcDaoTest {

    private static final String FIRSTNAME = "John";
    private static final String SURNAME = "Doe";
    private static final String EMAIL = "foo@bar.edu.ar";
    private static final String PASSWORD = "secret";
    private static final Locale LOCALE = new Locale("es");

    @Autowired
    private UserJdbcDao dao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;

    @Before
    public void setUp() {
        this.dao = new UserJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("users")
                .usingGeneratedKeyColumns("id");

        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"users");
    }

    @Test
    public void testCreate() {
        //1.precondiciones

        //2.exercise
        User newUser = dao.create(FIRSTNAME, SURNAME, EMAIL, PASSWORD, LOCALE);
        //3.assertions
        assertNotNull(newUser);
        assertEquals(EMAIL, newUser.getEmail());
        //assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    public void testFindById() {
        final Map<String, Object> values = new HashMap<>();
        values.put("firstName", FIRSTNAME);
        values.put("surname", SURNAME);
        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("locale", LOCALE);
        final Number userId = insert.executeAndReturnKey(values);
        final Optional<User> maybeUser = dao.findById(userId.longValue());
        assertTrue(maybeUser.isPresent());
        assertEquals(EMAIL, maybeUser.get().getEmail());
    }

    @Test
    public void testFindByEmail() {
        final Map<String, Object> values = new HashMap<>();
        values.put("firstName", FIRSTNAME);
        values.put("surname", SURNAME);
        values.put("email", EMAIL);
        values.put("password", PASSWORD);
        values.put("locale", LOCALE);
        insert.execute(values);
        final Optional<User> maybeUser = dao.findByEmail(EMAIL);
        assertTrue(maybeUser.isPresent());
        assertEquals(EMAIL, maybeUser.get().getEmail());
    }



}
