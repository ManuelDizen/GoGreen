package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserRoleDao;
import ar.edu.itba.paw.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRoleJdbcDao implements UserRoleDao {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;
    private final RowMapper<UserRole>  USER_ROLE_MAPPER = (resultSet, rowNum) ->
            new UserRole(resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("role_id"));

    @Autowired
    public UserRoleJdbcDao(final DataSource ds) {
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds)
                .withTableName("user_roles")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<UserRole> getById(long userId) {
        return template.query(
                "SELECT * FROM user_roles WHERE user_id = ?",
                new Object[]{userId}, USER_ROLE_MAPPER);
    }

    @Override
    public UserRole create(long userId, long roleId) {
        final Map<String, Object> values = new HashMap<>();
        values.put("user_id", userId);
        values.put("role_id", roleId);
        final Number userRoleId = insert.executeAndReturnKey(values);
        return new UserRole(userRoleId.longValue(), userId, roleId);
    }
}
