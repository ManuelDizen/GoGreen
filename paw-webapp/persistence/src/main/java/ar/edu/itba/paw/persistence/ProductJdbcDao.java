package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
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
                    resultSet.getFloat("price")
            );

    private static final RowMapper<Seller> SELLER_ROW_MAPPER =
            (resultSet, rowNum) -> new Seller(
                    resultSet.getLong("id"),
                    resultSet.getString("mail"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getString("name")
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
    public Optional<Product> getById(long productId) {
        List<Product> product =  template.query("SELECT * from products where id = ?",
                new Object[]{productId}, PRODUCT_ROW_MAPPER);
        return product.stream().findFirst();
    }

    @Override
    public List<Product> getAll() {
        return template.query("SELECT * FROM products",
        PRODUCT_ROW_MAPPER);
    }

    @Override
    public List<Product> filter(String name, String category, float maxPrice) {
        StringBuilder query = new StringBuilder("SELECT * from products where ");
        List<Object> args = new ArrayList<>();
        if(name != null){
            query.append("LOWER(name) like ? ");
            args.add('%' + name.toLowerCase() + '%');
        }
        if(category != null){
            query.append("AND categoryId IN (SELECT id from category where LOWER(name) like ?) ");
            args.add('%' + category.toLowerCase() + '%');
        }
        if(maxPrice != -1.0){
           query.append("AND price <= ?");
           args.add(maxPrice);
        }
        return template.query(query.toString(), args.toArray(), PRODUCT_ROW_MAPPER);
    }
}