package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CategoryDao;
import ar.edu.itba.paw.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryJdbcDao implements CategoryDao {

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER =
            (resultSet, rowNum) -> new Category(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getLong("imageId")
            );
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public CategoryJdbcDao(final DataSource ds) {
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("tags_to_products")
                .usingGeneratedKeyColumns("id");

    }


    @Override
    public Optional<Category> getById(long id) {
        return template.query("SELECT * from category where id = ?",
                new Object[]{id}, CATEGORY_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Category> getAllCategories() {
        return template.query("SELECT * from category", CATEGORY_ROW_MAPPER);
    }
}
