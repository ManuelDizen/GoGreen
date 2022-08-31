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
import java.util.List;

@Repository
public class ProductJdbcDao implements ProductDao {

    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    //Esto le idnica a Spring que constructor debe usar cuando quiere crear
    //instancias de este DAO.
    @Autowired
    public ProductJdbcDao(final DataSource ds){
        this.template = new JdbcTemplate(ds);
        this.insert = new SimpleJdbcInsert(ds);

    }


    @Override
    public Product create(String name, String description, int stock, int price) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return null;
    }
}
