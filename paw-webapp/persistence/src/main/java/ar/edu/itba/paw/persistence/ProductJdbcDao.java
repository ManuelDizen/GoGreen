package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductJdbcDao implements ProductDao {

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER =
            (resultSet, rowNum) -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getLong("sellerId"),
                    resultSet.getLong("categoryId"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("stock"),
                    resultSet.getFloat("price")
            );

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    //Esto le idnica a Spring que constructor debe usar cuando quiere crear
    //instancias de este DAO.
    @Autowired
    public ProductJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("products")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Product create(long sellerId, long categoryId, String name, String description, int stock, float price) {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", sellerId);
        values.put("categoryId", categoryId);
        values.put("name", name);
        values.put("description", description);
        values.put("stock", stock);
        values.put("price", price);
        final Number productId = insert.executeAndReturnKey(values);
        return new Product(productId.longValue(), sellerId, categoryId, name, description, stock, price);
    }

    @Override
    public Optional<List<Product>> findBySeller(long sellerId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Product>> getByMaxPrice(float price) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Product>> getByCategory(long categoryId) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return template.query("SELECT * FROM products",
        PRODUCT_ROW_MAPPER);
    }
}
