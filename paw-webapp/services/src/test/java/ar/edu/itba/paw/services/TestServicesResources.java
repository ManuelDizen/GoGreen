package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;

import java.time.LocalDateTime;
import java.util.Locale;

public class TestServicesResources {

    TestServicesResources(){throw new AssertionError();}
    static final String SELLER_NAME = "John";
    static final String SELLER_SURNAME = "Doe";
    static final String SELLER_EMAIL = "johndoe@mail.com";
    static final String SELLER_PASSWORD = "H3LL0";
    static final String SELLER_PHONE = "11 1111 1111";
    static final String SELLER_ADDRESS = "Av. del Libertador 1234";
    static final Area SELLER_AREA = Area.BELGRANO;
    static final long AUX_USER_FOR_SELLER_ID = 1;

    static final User AUX_USER_FOR_SELLER = new User(AUX_USER_FOR_SELLER_ID, SELLER_NAME, SELLER_SURNAME,
            SELLER_EMAIL, SELLER_PASSWORD, Locale.ENGLISH);
    static final long AUX_SELLER_ID = 2;

    static final Seller AUX_SELLER = new Seller(AUX_SELLER_ID, AUX_USER_FOR_SELLER, SELLER_PHONE,
                                                SELLER_ADDRESS, SELLER_AREA);
    static final long CATEGORY_ID = Category.FOOD.getId();
    static final String PRODUCT_NAME = "Soy milk";
    static final String PRODUCT_DESC = "Soy milk produced 100% out of plant ingredients";
    static final int PRODUCT_STOCK = 100;
    static final int PRODUCT_PRICE = 500;
    static final byte[] IMAGE_BYTES = new byte[] {0, 1};
    static final long IMAGE_ID = 1;
    static final Image AUX_IMAGE = new Image(IMAGE_ID, IMAGE_BYTES);
    static final long AUX_PRODUCT_ID = 1;

    static final Product AUX_PRODUCT = new Product(AUX_PRODUCT_ID, AUX_SELLER, CATEGORY_ID, PRODUCT_NAME,
            PRODUCT_DESC, PRODUCT_STOCK, PRODUCT_PRICE, AUX_IMAGE);

    static final String AUX_ARTICLE_MESSAGE = "New launch this week: 100% vegan leather jackets!";

    static final LocalDateTime DATE_TIME = LocalDateTime.now();

    static final String AUX_USER_NAME = "John";
    static final String AUX_USER_SURNAME = "Smith";
    static final String AUX_USER_EMAIL = "johnsmith@email.com";
    static final String AUX_USER_PASSWORD = "g00dby3";
    static final long AUX_USER_ID = 3;
    static final User AUX_USER = new User(AUX_USER_ID, AUX_USER_NAME, AUX_USER_SURNAME, AUX_USER_EMAIL,
            AUX_USER_PASSWORD, Locale.ENGLISH);
    static final String AUX_COMMENT_MSG = "This is an excellent product. 10/10!";
    static final long AUX_COMMENT_ID = 1;
    static final Comment AUX_COMMENT = new Comment(AUX_COMMENT_ID, AUX_COMMENT_MSG, AUX_USER,
            AUX_PRODUCT, DATE_TIME);

    static final String AUX_ORDER_MSG = "Please, I would like them to be 3 yellow and 2 blue";
    static final int AUX_ORDER_AMOUNT = 5;
    static final long AUX_ORDER_ID = 1;
    static final Order AUX_ORDER = new Order(AUX_ORDER_ID, PRODUCT_NAME, AUX_USER_NAME, AUX_USER_SURNAME,
            AUX_USER_EMAIL, SELLER_NAME, SELLER_SURNAME, SELLER_EMAIL, AUX_ORDER_AMOUNT, PRODUCT_PRICE,
            DATE_TIME, AUX_ORDER_MSG, AUX_SELLER);
    static final Role AUX_USER_ROLE = new Role("USER");
    static final long AUX_ARTICLE_ID = 4;
    static final Article AUX_ARTICLE = new Article(AUX_ARTICLE_ID, AUX_IMAGE, AUX_ARTICLE_MESSAGE,
            AUX_SELLER, DATE_TIME);

}
