package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;

import java.util.Locale;

public interface EmailService {

    void purchase(String buyerEmail, String buyer, Product product, int amount, Integer price,
                  String sellerName, String sellerPhone, String sellerMail, Locale locale);
    void itemsold(String sellerEmail, String seller, Product product, int amount, Integer price,
                  String buyerName, String buyerEmail, String buyerMessage, Locale locale);

    void registration(User user, Locale locale);

    void noMoreStock(Product product, String sellerEmail, String sellerName,
                            String sellerSurname, Locale locale);

    void orderCancelled(Order order, Locale buyerLocale, Locale sellerLocale);

}
