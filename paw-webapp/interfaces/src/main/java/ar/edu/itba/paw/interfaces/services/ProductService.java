package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(String name, String description, int stock, int price);

    List<Product> getProductList();

}
