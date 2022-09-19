package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderJdbcDao implements OrderDao {

    private static final RowMapper<Order> ORDER_ROW_MAPPER = (resultSet, rowNum) -> new Order(
                            resultSet.getLong("id"),
                            resultSet.getString("productName"),
                            resultSet.getString("sellerName"),
                            resultSet.getString("sellerSurname"),
                            resultSet.getString("sellerEmail"),
                            resultSet.getString("buyerName"),
                            resultSet.getString("buyerSurname"),
                            resultSet.getString("buyerEmail"),
                            resultSet.getInt("amount"),
                            resultSet.getFloat("price"),
                            resultSet.getObject("datetime", LocalDateTime.class),
                            resultSet.getString("message")
                            );

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public OrderJdbcDao(DataSource ds) {
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("orders")
                .usingGeneratedKeyColumns("id");

    }


    @Override
    public Order create(String productName, String buyerName, String buyerSurname,
                        String buyerEmail, String sellerName, String sellerSurname, String sellerEmail,
                        Integer amount, float price, LocalDateTime dateTime, String message) {
        final Map<String, Object> values = new HashMap<>();
        values.put("productName", productName);
        values.put("buyerName", buyerName);
        values.put("buyerSurname", buyerSurname);
        values.put("buyerEmail", buyerEmail);
        values.put("sellerName", sellerName);
        values.put("sellerSurname", sellerSurname);
        values.put("sellerEmail", sellerEmail);
        values.put("amount", amount);
        values.put("price", price);
        values.put("datetime", dateTime);
        values.put("message", message);
        final Number orderId = insert.executeAndReturnKey(values);
        return new Order(orderId.longValue(), productName, buyerName, buyerSurname, buyerEmail,
                sellerName, sellerSurname, sellerEmail, amount, price, dateTime, message);
    }

    @Override
    public Optional<Order> getById(long orderId) {
        return template.query("SELECT * FROM orders WHERE id = ?", new Object[]{orderId},
                ORDER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Order> getBySellerEmail(String sellerEmail) {
        return template.query("SELECT * FROM orders WHERE sellerEmail = ?", new Object[]{sellerEmail},
                ORDER_ROW_MAPPER);

    }

    @Override
    public List<Order> getByBuyerEmail(String buyerEmail) {
        return template.query("SELECT * FROM orders WHERE buyerEmail = ?", new Object[]{buyerEmail},
                ORDER_ROW_MAPPER);
    }
}
