package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

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
                    resultSet.getFloat("price"),
                    resultSet.getLong("imageId")
            );

    private static final RowMapper<Seller> SELLER_ROW_MAPPER =
            (resultSet, rowNum) -> new Seller(
                    resultSet.getLong("id"),
                    resultSet.getLong("userid"),
                    resultSet.getString("phone"),
                    resultSet.getString("address")
            );


    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public ProductJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("products")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Product create(long sellerId, long categoryId, String name, String description,
                          int stock, float price, long imageId) {
        final Map<String, Object> values = new HashMap<>();
        values.put("sellerId", sellerId);
        values.put("categoryId", categoryId);
        values.put("name", name);
        values.put("description", description);
        values.put("stock", stock);
        values.put("price", price);
        values.put("imageId", imageId);
        final Number productId = insert.executeAndReturnKey(values);
        return new Product(productId.longValue(), sellerId, categoryId, name, description, stock, price,
                imageId);
    }

    @Override
    public List<Product> findBySeller(long sellerId) {
        return template.query("SELECT * FROM products WHERE sellerId = ? ORDER BY id DESC", new Object[]{sellerId},
                PRODUCT_ROW_MAPPER);
    }

    @Override
    public Optional<Product> getById(long productId) {
        List<Product> product =  template.query("SELECT * FROM products WHERE id = ? ORDER BY id DESC",
                new Object[]{productId}, PRODUCT_ROW_MAPPER);
        return product.stream().findFirst();
    }

    @Override
    public Optional<Product> getByName(String name) {
        List<Product> product = template.query("SELECT * FROM products WHERE name = ? ORDER BY id DESC",
                new Object[]{name}, PRODUCT_ROW_MAPPER);
        return product.stream().findFirst();
    }

    @Override
    public List<Product> getAll() {
        return template.query("SELECT * FROM products ORDER BY id DESC",
        PRODUCT_ROW_MAPPER);
    }

    @Override
    public List<Product> getAvailable() {
        return template.query("SELECT * FROM products WHERE stock <> 0 ORDER BY id DESC",
                PRODUCT_ROW_MAPPER);
    }

    @Override
    public void deleteProduct(long productId) {
        template.update("DELETE FROM products WHERE id = ?", new Object[]{productId});
    }

    @Override
    public void updateStock(long productId, int amount) {
        String query = "UPDATE products SET stock = ? WHERE id = ?";
        Object[] args = new Object[]{amount, productId};
        template.update(query, args);
    }

    @Override
    public Boolean addStock(String name, int amount) {
        String query = "UPDATE products SET stock = ? WHERE name = ?";
        Object[] args = new Object[]{amount, name};
        return template.update(query, args) == 1;
    }

    @Override
    public List<Product> filter(String name, long category, List<Long> tags, float maxPrice) {
        StringBuilder query = new StringBuilder("SELECT * FROM products WHERE ");
        List<Object> args = new ArrayList<>();
        if(name != null){
            query.append("LOWER(name) like ? ");
            args.add('%' + name.toLowerCase() + '%');
        }
        if(category != 0){
            query.append("AND categoryId = ? ");
            args.add(category);
        }
        if(tags.size() != 0){
            for(long tag : tags) {
                query.append("AND id IN (SELECT productId from tags_to_products WHERE tag = ?)");
                args.add(tag);
            }
        }
        if(maxPrice != -1.0){
           query.append("AND price <= ?");
           args.add(maxPrice);
        }
        query.append("AND stock <> 0 ORDER BY id DESC");
        return template.query(query.toString(), args.toArray(), PRODUCT_ROW_MAPPER);
    }

    @Override
    public List<Product> getRecent(int amount) {
        List<Product> products = template.query("SELECT * FROM products WHERE stock <> 0 ORDER BY id DESC",
                PRODUCT_ROW_MAPPER);
        if (products.size() < amount) return products;
        return products.subList(0, amount);
    }
}
