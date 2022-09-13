package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.SellerDao;
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
import java.util.Optional;

@Repository
public class SellerJdbcDao implements SellerDao {

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
    public SellerJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("sellers")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public Seller create(long userid, String phone, String address) {
        final Map<String, Object> values = new HashMap<>();
        values.put("userid", userid);
        values.put("phone", phone);
        values.put("address", address);
        final Number sellerId = insert.executeAndReturnKey(values);
        return new Seller(sellerId.longValue(), userid, phone, address);
    }

    @Override
    public Optional<Seller> findById(long id) {
        return template.query("SELECT * from sellers where userId = ?",
                new Object[]{id}, SELLER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public Optional<Seller> findByMail(String mail) {
        return template.query("SELECT * from sellers where userId = " +
                        "(SELECT id from users where email = ?)",
                new Object[]{mail}, SELLER_ROW_MAPPER).stream().findFirst();
    }

    @Override
    public List<Seller> findByName(String name) {
        return template.query("SELECT * from sellers where userId = " +
                        "(SELECT id FROM users where firstName = ?)",
                new Object[]{name}, SELLER_ROW_MAPPER);
    }

    @Override
    public Optional<Seller> findByPhone(String phone) {
        return template.query("SELECT * from sellers where phone = ?",
                new Object[]{phone}, SELLER_ROW_MAPPER).stream().findFirst();

    }

    @Override
    public List<Seller> getAll() {
        return template.query("SELECT * FROM sellers",
                SELLER_ROW_MAPPER);
    }

}
