package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    @Before
    public void setUp() {
        this.dao = new UserJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        //JdbcTestUtils.deleteFromTables(jdbcTemplate,	"users");
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

}
