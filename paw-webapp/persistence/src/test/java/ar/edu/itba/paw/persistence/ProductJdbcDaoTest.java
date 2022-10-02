package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Product;
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

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class ProductJdbcDaoTest {


    private static final long SELLERID = 1;
    private static final long CATEGORYID = 1;
    private static final String NAME = "P1";
    private static final String DESCRIPTION = "First product";
    private static final int STOCK = 10;
    private static final float PRICE = 100;
    private static final int IMAGEID = 0;

    @Autowired
    private ProductJdbcDao dao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;

    @Before
    public void setUp() {
        this.dao = new ProductJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("products")
                .usingGeneratedKeyColumns("id");

        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"products");

    }

    private Map<String, Object> createProduct() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);
        return values;
    }

    @Test
    public void testCreate() {
        //1.precondiciones

        //2.exercise
        Product newProduct = dao.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, IMAGEID);
        //3.assertions
        assertNotNull(newProduct);
        assertEquals(NAME, newProduct.getName());
    }

    @Test
    public void testFindById() {
        final Map<String, Object> values = createProduct();
        final Number productId = insert.executeAndReturnKey(values);
        final Optional<Product> maybeProduct = dao.getById(productId.longValue());
        assertTrue(maybeProduct.isPresent());
    }

    @Test
    public void testFindByName() {
        final Map<String, Object> values = createProduct();
        insert.execute(values);
        final Optional<Product> maybeProduct = dao.getByName(NAME);
        assertTrue(maybeProduct.isPresent());
        assertEquals(NAME, maybeProduct.get().getName());
    }


    @Test
    public void testFindBySeller() {
        final Map<String, Object> values = createProduct();
        insert.execute(values);
        List<Product> bySeller = dao.findBySeller(SELLERID);
        for(Product prod : bySeller) {
            assertEquals(SELLERID, prod.getSellerId());
        };
    }

    @Test
    public void testDelete() {
        final Map<String, Object> values = createProduct();
        final Number productId = insert.executeAndReturnKey(values);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
        dao.deleteProduct(productId.longValue());
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
    }


    //TODO: tests para add y updateStock
    @Test
    public void testAddStock() {
        final Map<String, Object> values = createProduct();
        final Number productId = insert.executeAndReturnKey(values);

    }

    @Test
    public void testGetRecent() {
        final Map<String, Object> values = createProduct();
        insert.execute(values);
        List<Product> recentProducts = dao.getRecent(1);
        assertEquals(NAME, recentProducts.get(0).getName());
    }

}
