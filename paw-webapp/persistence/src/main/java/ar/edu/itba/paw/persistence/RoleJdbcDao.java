package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.RoleDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class RoleJdbcDao implements RoleDao {

    private final JdbcTemplate template;
    private static final RowMapper<Role> ROLE_ROW_MAPPER = (result_set, row_num) ->
            new Role(result_set.getLong("id"),
                result_set.getString("name"));

    @Autowired
    public RoleJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
    }


    @Override
    public Optional<Role> getById(long id) {
        List<Role> roles = template.query("SELECT * FROM roles WHERE id = ?",
                new Object[]{id}, ROLE_ROW_MAPPER);
        return roles.stream().findFirst();

    }

    @Override
    public Optional<Role> getByName(String name) {
        List<Role> roles = template.query("SELECT * FROM roles WHERE name = ?",
                new Object[]{name}, ROLE_ROW_MAPPER);
        return roles.stream().findFirst();
    }
}
