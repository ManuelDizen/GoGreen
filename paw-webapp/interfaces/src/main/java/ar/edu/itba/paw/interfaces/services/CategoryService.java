package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Optional<Category> getById(long id);

    public List<Category> getAllCategories();

}
