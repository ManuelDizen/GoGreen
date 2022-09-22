package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    Optional<Category> getById(long id);

    List<Category> getAllCategories();
}
