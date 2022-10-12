package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.EcotagDao;
import ar.edu.itba.paw.models.Ecotag;
import ar.edu.itba.paw.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcotagJdbcDao implements EcotagDao {

    private static final RowMapper<Ecotag> ECOTAG_ROW_MAPPER =
            (resultSet, rowNum) -> Ecotag.getById(resultSet.getLong("tag")
            );
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public EcotagJdbcDao(final DataSource ds) {
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("tags_to_products")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public void add(Ecotag tag, long productId) {
        final Map<String, Object> values = new HashMap<>();
        values.put("tag", tag.getId());
        values.put("productId", productId);
        insert.execute(values);
    }

    @Override
    public List<Ecotag> getTagsFromProduct(long productId) {
        return template.query("SELECT tag from tags_to_products WHERE productId = ?",
                new Object[]{productId}, ECOTAG_ROW_MAPPER);
    }

}
