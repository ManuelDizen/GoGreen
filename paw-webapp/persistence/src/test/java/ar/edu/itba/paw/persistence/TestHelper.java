package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import org.hibernate.AssertionFailure;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Locale;

public class TestHelper {
    private TestHelper(){throw new AssertionError();}
    static User userCreateHelperFunction(EntityManager em, String name, String surname, String email,
                                         String password, Locale locale){
        User user = new User(name, surname, email, password, locale);
        em.persist(user);
        return user;
    }

    static Seller sellerCreateHelperFunction(EntityManager em, User user, String phone,
                                             String address, Area area){
        Seller seller = new Seller(user, phone, address, area);
        em.persist(seller);
        return seller;
    }

    static Image imageCreateHelperFunction(EntityManager em, byte[] bytes){
        Image image = new Image(bytes);
        em.persist(image);
        return image;
    }

    static Product productCreateHelperFunction(EntityManager em, Seller seller, long categoryId,
                                       String name, String description, int stock, int price,
                                               Image image){
        Product product = new Product(seller,categoryId,name,description,stock,price,image);
        em.persist(product);
        return product;
    }

    static Article articleCreateHelperFunction(EntityManager em, Image image, Seller seller,
                                               String message, LocalDateTime datetime){
        Article article = new Article(image, message, seller, datetime);
        em.persist(article);
        return article;
    }

    static Comment commentCreateHelperFunction(EntityManager em, User user, Product product,
                                               String message, LocalDateTime datetime){
        Comment comment = new Comment(message, user, product, datetime);
        em.persist(comment);
        return comment;
    }
}
