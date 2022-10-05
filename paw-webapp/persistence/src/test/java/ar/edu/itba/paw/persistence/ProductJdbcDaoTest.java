package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class ProductJdbcDaoTest {


    private static final long SELLERID = 1;
    private static final long CATEGORYID = 1;
    private static final String NAME = "P1";
    private static final String DESCRIPTION = "First product";
    private static final int STOCK = 10;
    private static final int PRICE = 100;
    private static final int IMAGEID = 0;

    private static final long PRODUCTID2 = 2;
    private static final String NAME2 = "Q2";

    private static final long CATEGORYID2 = 2;
    private static final int PRICE2 = 20;

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
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);

        final Number productId = insert.executeAndReturnKey(values);
        final Optional<Product> maybeProduct = dao.getById(productId.longValue());
        assertTrue(maybeProduct.isPresent());
        assertEquals(NAME, maybeProduct.get().getName());
    }

    @Test
    public void testFindByName() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        insert.execute(values);
        final Optional<Product> maybeProduct = dao.getByName(NAME);
        assertTrue(maybeProduct.isPresent());
        assertEquals(NAME, maybeProduct.get().getName());
    }


    @Test
    public void testFindBySeller() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        insert.execute(values);
        List<Product> bySeller = dao.findBySeller(SELLERID);
        for(Product prod : bySeller) {
            assertEquals(SELLERID, prod.getSellerId());
        };
    }

    @Test
    public void testDelete() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        final Number productId = insert.executeAndReturnKey(values);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
        dao.deleteProduct(productId.longValue());
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
    }


    @Test
    public void testGetRecent() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);
        insert.execute(values);
        List<Product> recentProducts = dao.getRecent(1);
        assertEquals(NAME, recentProducts.get(0).getName());
    }

    @Test
    public void testFilterByName() {

        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        final Number number = insert.executeAndReturnKey(values);

        final Map<String, Object> values2 = new HashMap<>();
        values2.put("sellerId", SELLERID);
        values2.put("categoryId", CATEGORYID2);
        values2.put("name", NAME2);
        values2.put("description", DESCRIPTION);
        values2.put("stock", STOCK);
        values2.put("price", PRICE2);
        values2.put("imageId", IMAGEID);
        insert.execute(values2);

        List<Product> filteredList = dao.filter(NAME, 0, new ArrayList<>(), -1, 0);
        assertEquals(1, filteredList.size());
        assertEquals(number.longValue(), filteredList.get(0).getProductId());

    }
    @Test
    public void testFilterByPrice() {

        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        insert.execute(values);

        final Map<String, Object> values2 = new HashMap<>();
        values2.put("sellerId", SELLERID);
        values2.put("categoryId", CATEGORYID2);
        values2.put("name", NAME2);
        values2.put("description", DESCRIPTION);
        values2.put("stock", STOCK);
        values2.put("price", PRICE2);
        values2.put("imageId", IMAGEID);
        final Number number = insert.executeAndReturnKey(values2);

        List<Product> filteredList = dao.filter("", 0, new ArrayList<>(), 30, 0);
        assertEquals(1, filteredList.size());
        assertEquals(number.longValue(), filteredList.get(0).getProductId());

    }

    @Test
    public void testFilterByCategory() {

        final Map<String, Object> values =new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        insert.execute(values);

        final Map<String, Object> values2 = new HashMap<>();
        values2.put("sellerId", SELLERID);
        values2.put("categoryId", CATEGORYID2);
        values2.put("name", NAME2);
        values2.put("description", DESCRIPTION);
        values2.put("stock", STOCK);
        values2.put("price", PRICE2);
        values2.put("imageId", IMAGEID);
        final Number number = insert.executeAndReturnKey(values2);

        List<Product> filteredList = dao.filter("", CATEGORYID2, new ArrayList<>(), -1, 0);
        assertEquals(1, filteredList.size());
        assertEquals(number.longValue(), filteredList.get(0).getProductId());

    }

    @Test
    public void testFilterGetNothing() {

        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        insert.execute(values);

        final Map<String, Object> values2 = new HashMap<>();
        values2.put("sellerId", SELLERID);
        values2.put("categoryId", CATEGORYID2);
        values2.put("name", NAME2);
        values2.put("description", DESCRIPTION);
        values2.put("stock", STOCK);
        values2.put("price", PRICE2);
        values2.put("imageId", IMAGEID);
        insert.execute(values2);

        assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "products"));
        List<Product> filteredList = dao.filter("", CATEGORYID, new ArrayList<>(), 30, 0);
        assertEquals(0, filteredList.size());


    }

    @Test
    public void testUpdateStock() {

        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        final Number number = insert.executeAndReturnKey(values);


        dao.updateStock(number.longValue(), 20);
        Product product = dao.getById(number.longValue()).get();
        assertEquals(20, product.getStock());
    }


}
