package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao {

    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) ->
            new User(resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"));

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    //Esto le idnica a Spring que constructor debe usar cuando quiere crear
    //instancias de este DAO.
    @Autowired
    public UserJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public User create(final String email, final String password) {
        final Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("password", password);
        final Number userId = insert.executeAndReturnKey(values);
        return new User(userId.longValue(), "from-the-dao-" + email, password);
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        // Hacer esto esta MAL por SQL Injection
        // NO hay que concatenar variables en una query
        //template.query("SELECT * FROM users WHERE email = " + email, null);

        return template.query("SELECT * FROM users WHERE email = ?",
                new Object[]{ email }, USER_ROW_MAPPER
        ).stream().findFirst();

        /*------------------ Sin lambda ------------------
        return template.query("SELECT * FROM users WHERE email = ?",
                new Object[]{email}, new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        return new User(resultSet.getString("email"),
                                resultSet.getString("password"));
                    }
                }).stream().findFirst();

         */

        /*------------------ OTRA FORMA ------------------
        List<User> users = template.query("SELECT * FROM users WHERE email = ?",
                new Object[]{email}, new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                        return new User(resultSet.getString("email"), resultSet.getString("password"));
                    }
                });
        return users.isEmpty() ? Optional.<User>empty() : Optional.of(users.get(0));*/

    }

    @Override
    public Optional<User> findById(final long userId) {
        return template.query("SELECT * FROM users WHERE id = ?",
                new Object[]{ userId }, USER_ROW_MAPPER
        ).stream().findFirst();
    }
}
