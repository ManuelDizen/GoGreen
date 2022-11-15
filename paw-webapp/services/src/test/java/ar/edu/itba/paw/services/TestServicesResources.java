package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.*;
import com.sun.crypto.provider.DESCipher;

import java.util.Locale;

public class TestServicesResources {
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
    static final Image PRODUCT_IMAGE = new Image(IMAGE_BYTES);
    static final Product AUX_PRODUCT = new Product(AUX_SELLER, CATEGORY_ID, PRODUCT_NAME, PRODUCT_DESC,
            PRODUCT_STOCK, PRODUCT_PRICE, PRODUCT_IMAGE);

}
