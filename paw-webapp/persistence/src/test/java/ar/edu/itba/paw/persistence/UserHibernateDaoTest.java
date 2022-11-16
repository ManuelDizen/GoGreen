package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;

import org.junit.Assert;
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
public class UserHibernateDaoTest {

    @Autowired
    private UserHibernateDao userHibernateDao;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setUp(){}

    @Test
    public void testCreate(){
        User newUser = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME,
                USER_EMAIL, USER_PASSWORD, USER_LOCALE);
        assertNotNull(newUser);
        assertEquals(USER_EMAIL, newUser.getEmail());
    }

    @Test
    public void testFindByEmail(){
        TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);

        Optional<User> maybeUser = userHibernateDao.findByEmail(USER_EMAIL);
        assertTrue(maybeUser.isPresent());
        User user = maybeUser.get();
        Assert.assertEquals(USER_EMAIL, user.getEmail());
        Assert.assertEquals(USER_FIRSTNAME, user.getFirstName());
        Assert.assertEquals(USER_SURNAME, user.getSurname());
    }

    @Test
    public void testFindById(){
        User user = TestHelper.userCreateHelperFunction(em, USER_FIRSTNAME, USER_SURNAME, USER_EMAIL,
                USER_PASSWORD, USER_LOCALE);

        Optional<User> maybeUser = userHibernateDao.findById(user.getId());
        assertTrue(maybeUser.isPresent());
        user = maybeUser.get();
        Assert.assertEquals(USER_EMAIL, user.getEmail());
        Assert.assertEquals(USER_FIRSTNAME, user.getFirstName());
        Assert.assertEquals(USER_SURNAME, user.getSurname());
    }

    @Test
    public void testAttemptFindByIdWhenNotExists(){
        Optional<User> maybeUser = userHibernateDao.findById(USER_ID);
        assertFalse(maybeUser.isPresent());
    }

    @Test
    public void testAttemptFindByEmailWhenNotExists(){
        Optional<User> maybeUser = userHibernateDao.findByEmail(USER_EMAIL);
        assertFalse(maybeUser.isPresent());
    }


    /*private static final String FIRSTNAME = "John";
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

*/

}
