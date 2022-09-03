package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Product;

public interface EmailService {

    void purchase(String buyerEmail, String buyer, Product product, int amount);

}
