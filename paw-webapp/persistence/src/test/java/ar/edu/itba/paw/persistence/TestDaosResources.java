package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;

import java.util.Locale;

public class TestDaosResources {

    static final int USER_ID = 1;
    static final String USER_FIRSTNAME = "John";
    static final String USER_SURNAME = "Doe";
    static final String USER_EMAIL = "johndoe@email.com";
    static final String USER_PASSWORD = "H3LL0";
    static final Locale USER_LOCALE = Locale.US;

    // ------------ FIRST MOCK SELLER -----------------
    static final Long AUX_U_FOR_SELLER_ID = 2L;
    static final String AUX_U_FOR_SELLER_FIRSTNAME = "John";
    static final String AUX_U_FOR_SELLER_SURNAME = "Doe";
    static final String AUX_U_FOR_SELLER_EMAIL = "johndoe@email.com";
    static final String AUX_U_FOR_SELLER_PASSWORD = "H3LL0";
    static final Locale AUX_U_FOR_SELLER_LOCALE = Locale.US;

    static final User AUX_USER_FOR_SELLER = new User(AUX_U_FOR_SELLER_ID, AUX_U_FOR_SELLER_FIRSTNAME,
            AUX_U_FOR_SELLER_SURNAME, AUX_U_FOR_SELLER_EMAIL, AUX_U_FOR_SELLER_PASSWORD,
            AUX_U_FOR_SELLER_LOCALE);
    static final int SELLER_ID = 1;
    static final String SELLER_PHONE = "11 2233 4455";
    static final String SELLER_ADDRESS = "Av. del Libertador 123";
    static final Area SELLER_AREA = Area.DEVOTO;

    // ------------ SECOND MOCK SELLER -----------------

    //static final Long AUX_U_FOR_SELLER_ID_2 = 3L;
    static final String AUX_U_FOR_SELLER_FIRSTNAME_2 = "Leonardo";
    static final String AUX_U_FOR_SELLER_SURNAME_2 = "Di Caprio";
    static final String AUX_U_FOR_SELLER_EMAIL_2 = "leodicaprio@email.com";
    static final String AUX_U_FOR_SELLER_PASSWORD_2 = "H3LL0";
    static final Locale AUX_U_FOR_SELLER_LOCALE_2 = Locale.UK;

    /*static final User AUX_USER_FOR_SELLER_2 = new User(AUX_U_FOR_SELLER_ID_2, AUX_U_FOR_SELLER_FIRSTNAME_2,
            AUX_U_FOR_SELLER_SURNAME_2, AUX_U_FOR_SELLER_EMAIL_2, AUX_U_FOR_SELLER_PASSWORD_2,
            AUX_U_FOR_SELLER_LOCALE_2);
    static final int SELLER_ID_2 = 2;*/
    static final String SELLER_PHONE_2 = "11 2323 5656";
    static final String SELLER_ADDRESS_2 = "Av. Callao 123";
    static final Area SELLER_AREA_2 = Area.RECOLETA;

    // ------------ THIRD MOCK SELLER -----------------

    static final Long AUX_U_FOR_SELLER_ID_3 = 3L;
    static final String AUX_U_FOR_SELLER_FIRSTNAME_3 = "Mark";
    static final String AUX_U_FOR_SELLER_SURNAME_3 = "Ruffalo";
    static final String AUX_U_FOR_SELLER_EMAIL_3 = "markruffalo@email.com";
    static final String AUX_U_FOR_SELLER_PASSWORD_3 = "G00DB13";
    static final Locale AUX_U_FOR_SELLER_LOCALE_3 = Locale.FRANCE;

    /*static final User AUX_USER_FOR_SELLER_3 = new User(AUX_U_FOR_SELLER_ID_3, AUX_U_FOR_SELLER_FIRSTNAME_3,
            AUX_U_FOR_SELLER_SURNAME_3, AUX_U_FOR_SELLER_EMAIL_3, AUX_U_FOR_SELLER_PASSWORD_3,
            AUX_U_FOR_SELLER_LOCALE_3);
    static final int SELLER_ID_3 = 3;*/
    static final String SELLER_PHONE_3 = "11 2222 3333";
    static final String SELLER_ADDRESS_3 = "Iguazú 123";
    static final Area SELLER_AREA_3 = Area.PARQUE_PATRICIOS;


    // ------------ PRODUCT TEST DATA -----------------
    static final Seller AUX_SELLER_FOR_PRODUCT = new Seller(AUX_USER_FOR_SELLER, SELLER_PHONE,
            SELLER_ADDRESS, SELLER_AREA);
    static final int PRODUCT_ID = 1;
    static final Category PRODUCT_CAT = Category.FOOD;
    static final String PRODUCT_NAME = "100% plant based meat";
    static final String PRODUCT_DESC = "A steak prepared entirely out of plant based ingredients";
    static final int PRODUCT_STOCK = 100;
    static final Integer PRODUCT_PRICE = 500;
    static final byte[] BYTES_FOR_PROD_IMAGE = new byte[]{0,1};
    static final Image PRODUCT_IMAGE = new Image(BYTES_FOR_PROD_IMAGE);

    static final Category PRODUCT_CAT_2 = Category.CLOTHING;
    static final String PRODUCT_NAME_2 = "Cruelty free ecoleather jacket";
    static final String PRODUCT_DESC_2 = "A jacket composed entirely out of recycled plastic fabrics";
    static final int PRODUCT_STOCK_2 = 50;
    static final Integer PRODUCT_PRICE_2 = 3000;

    static final Category PRODUCT_CAT_3 = Category.SKINCARE;
    static final String PRODUCT_NAME_3 = "Exfoliating cream";
    static final String PRODUCT_DESC_3 = "Everyday cream to moisturize the skin";
    static final int PRODUCT_STOCK_3 = 200;
    static final Integer PRODUCT_PRICE_3 = 200;


}
