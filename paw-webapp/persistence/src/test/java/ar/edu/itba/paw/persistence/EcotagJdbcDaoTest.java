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
import static org.junit.Assert.assertTrue;

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
    private EcotagHibernateDao dao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insert;
    private SimpleJdbcInsert insert2;
/*
    @Before
    public void setUp() {
        this.dao = new EcotagJdbcDao(ds);
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("tags_to_products")
                .usingGeneratedKeyColumns("id");
        this.insert2 = new SimpleJdbcInsert(ds).withTableName("products")
                .usingGeneratedKeyColumns("id");

        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"tags_to_products");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,	"products");

    }

    @Test
    public void testAddEcotag() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        final Number productId = insert2.executeAndReturnKey(values);
        dao.add(Ecotag.ECOTAG_RECYCLE, productId.longValue());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "tags_to_products"));
    }

    @Test
    public void testGetEcotags() {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", SELLERID);
        values.put("categoryId", CATEGORYID);
        values.put("name", NAME);
        values.put("description", DESCRIPTION);
        values.put("stock", STOCK);
        values.put("price", PRICE);
        values.put("imageId", IMAGEID);;
        final long productId = insert2.executeAndReturnKey(values).longValue();
        final Map<String, Object> values2 = new HashMap<>();
        values2.put("tag", Ecotag.ECOTAG_RECYCLE.getId());
        values2.put("productId", productId);
        insert.execute(values2);
        values2.put("tag", Ecotag.ECOTAG_ENERGY.getId());
        insert.execute(values2);
        List<Ecotag> getTags = dao.getTagsFromProduct(productId);
        assertEquals(2, getTags.size());
        assertEquals(1, getTags.get(0).getId());
        assertEquals(3, getTags.get(1).getId());
    }
*/
}
