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

    static final User AUX_USER_FOR_SELLER = new User(SELLER_NAME, SELLER_SURNAME, SELLER_EMAIL,
            SELLER_PASSWORD, Locale.ENGLISH);

    static final Seller AUX_SELLER = new Seller(AUX_USER_FOR_SELLER, SELLER_PHONE,
                                                SELLER_ADDRESS, SELLER_AREA);

    static final long CATEGORY_ID = Category.FOOD.getId();
    static final String PRODUCT_NAME = "Soy milk";
    static final String PRODUCT_DESC = "Soy milk produced 100% out of plant ingredients";
    static final int PRODUCT_STOCK = 100;
    static final int PRODUCT_PRICE = 500;
    static final byte[] IMAGE_BYTES = new byte[] {0, 1};
    static final long IMAGE_ID = 1;
    static final Image AUX_IMAGE = new Image(IMAGE_BYTES);
    static final Product AUX_PRODUCT = new Product(AUX_SELLER, CATEGORY_ID, PRODUCT_NAME, PRODUCT_DESC,
            PRODUCT_STOCK, PRODUCT_PRICE, AUX_IMAGE);

    static final String ARTICLE_MESSAGE = "New launch this week: 100% vegan leather jackets!";
    static final LocalDateTime DATE_TIME = LocalDateTime.now();

    static final String AUX_USER_NAME = "John";
    static final String AUX_USER_SURNAME = "Smith";
    static final String AUX_USER_EMAIL = "johnsmith@email.com";
    static final String AUX_USER_PASSWORD = "g00dby3";
    static final User AUX_USER = new User(AUX_USER_NAME, AUX_USER_SURNAME, AUX_USER_EMAIL,
            AUX_USER_PASSWORD, Locale.ENGLISH);
    static final String AUX_COMMENT_MSG = "This is an excellent product. 10/10!";
    static final Comment AUX_COMMENT = new Comment(AUX_COMMENT_MSG, AUX_USER, AUX_PRODUCT,
            DATE_TIME);

}
