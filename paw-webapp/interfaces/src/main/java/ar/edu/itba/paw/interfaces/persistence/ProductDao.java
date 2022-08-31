package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Product;

import java.util.List;

public interface ProductDao {

    Product create(String name, String description, int stock, int price);

    List<Product> getAll();

}
