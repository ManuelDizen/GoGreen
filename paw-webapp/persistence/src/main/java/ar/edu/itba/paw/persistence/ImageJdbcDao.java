package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.models.Image;
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
public class ImageJdbcDao implements ImageDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RowMapper<Image> IMAGE_ROW_MAPPER = (rs, row_num) ->
            new Image(rs.getLong("id"), rs.getBytes("source"));

    @Autowired
    public ImageJdbcDao(final DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
        this.jdbcInsert = new SimpleJdbcInsert(ds).withTableName("images")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public Image create(byte[] source) {
        Map<String, Object> data = new HashMap<>();
        data.put("source", source);
        long imageId = jdbcInsert.executeAndReturnKey(data).longValue();
        return new Image(imageId, source);
    }

    @Override
    public Optional<Image> getById(long id) {
        List<Image> query = jdbcTemplate.query("SELECT * FROM images WHERE id = ?",
                new Object[] {id}, IMAGE_ROW_MAPPER);
        return query.stream().findFirst();
    }
}
