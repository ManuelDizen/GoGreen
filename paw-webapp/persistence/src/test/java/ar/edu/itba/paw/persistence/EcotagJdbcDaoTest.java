package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Ecotag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class EcotagJdbcDaoTest {

    private static final long SELLERID = 1;
    private static final long CATEGORYID = 1;
    private static final String NAME = "P1";
    private static final String DESCRIPTION = "First product";
    private static final int STOCK = 10;
    private static final int PRICE = 100;
    private static final int IMAGEID = 0;

    @Autowired
    private EcotagJdbcDao dao;

    private ProductJdbcDao productDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;

    @Before
    public void setUp() {
        this.dao = new EcotagJdbcDao(ds);
        this.productDao = new ProductJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("tags_to_products")
                .usingGeneratedKeyColumns("id");

        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"tags_to_products");

    }

    @Test
    public void testAddEcotag() {
        Product product = productDao.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, IMAGEID);
        dao.add(Ecotag.ECOTAG_RECYCLE, product.getProductId());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "tags_to_products"));
    }

    @Test
    public void testGetEcotags() {
        Product product = productDao.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, IMAGEID);
        final Map<String, Object> values = new HashMap<>();
        values.put("tag", Ecotag.ECOTAG_RECYCLE.getId());
        values.put("productId", product.getProductId());
        insert.execute(values);
        values.put("tag", Ecotag.ECOTAG_FOREST.getId());
        insert.execute(values);
        List<Ecotag> getTags = dao.getTagsFromProduct(product.getProductId());
        assertEquals(2, getTags.size());
        assertEquals(1, getTags.get(0).getId());
    }

}
