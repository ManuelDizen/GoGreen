package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.FAQDao;
import ar.edu.itba.paw.models.FAQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class FAQJdbcDao implements FAQDao {

    private static final RowMapper<FAQ> FAQ_ROW_MAPPER =
            (resultSet, rowNum) -> new FAQ(
                    resultSet.getLong("id"),
                    resultSet.getString("question"),
                    resultSet.getString("answer")
            );

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public FAQJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds).withTableName("faqs")
                .usingGeneratedKeyColumns("id");

    }

    @Override
    public List<FAQ> getFAQs() {
        return template.query("SELECT * FROM faqs",
                FAQ_ROW_MAPPER);
    }


}
