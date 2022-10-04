package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.print.attribute.standard.MediaSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private static final long PRODUCTID = 1;
    private static final long SELLERID = 1;
    private static final long CATEGORYID = 1;
    private static final String NAME = "P1";
    private static final String DESCRIPTION = "First product";
    private static final int STOCK = 10;
    private static final int PRICE = 100;
    private static final int IMAGEID = 0;

    private static final long PRODUCTID2 = 2;
    private static final String NAME2 = "Q2";

    private static final long CATEGORYID2 = 2;
    private static final int PRICE2 = 20;


    @InjectMocks
    private ProductServiceImpl ps;

    @Mock
    private ProductDao productDao;

    private List<Product> productForSorting() {
        Mockito.when(productDao.create(eq(SELLERID), eq(CATEGORYID), eq(NAME), eq(DESCRIPTION), eq(STOCK), eq(PRICE), anyLong())).thenReturn(new Product(PRODUCTID, SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, IMAGEID));
        Mockito.when(productDao.create(eq(SELLERID), eq(CATEGORYID2), eq(NAME2), eq(DESCRIPTION), eq(STOCK), eq(PRICE2), anyLong())).thenReturn(new Product(PRODUCTID2, SELLERID, CATEGORYID, NAME2, DESCRIPTION, STOCK, PRICE2, IMAGEID));

        List<Product> productList = new ArrayList<>();
        productList.add(ps.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, null));
        productList.add(ps.create(SELLERID, CATEGORYID, NAME2, DESCRIPTION, STOCK, PRICE2, null));

        return productList;
    }


    @Test
    public void testCreate() {

        Mockito.when(productDao.create(eq(SELLERID), eq(CATEGORYID), eq(NAME), eq(DESCRIPTION), eq(STOCK), eq(PRICE), anyLong())).thenReturn(new Product(PRODUCTID, SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, IMAGEID));

        final Product newProduct = ps.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, null);
        assertNotNull(newProduct);
        assertEquals(SELLERID, newProduct.getSellerId());
        assertEquals(NAME, newProduct.getName());

    }


    @Test
    public void testSortByChronologic() {

        List<Product> productList = productForSorting();

        ps.sortProducts(productList, 0, 0);
        assertEquals(NAME, productList.get(0).getName());
        ps.sortProducts(productList, 0, 1);
        assertEquals(NAME2, productList.get(0).getName());

    }

    @Test
    public void testSortByAlphabetic() {

        List<Product> productList = productForSorting();

        ps.sortProducts(productList, 1, 0);
        assertEquals(NAME, productList.get(0).getName());
        ps.sortProducts(productList, 1, 1);
        assertEquals(NAME2, productList.get(0).getName());

    }


    @Test
    public void testSortByPrice() {

        List<Product> productList = productForSorting();

        ps.sortProducts(productList, 2, 0);
        assertEquals(NAME2, productList.get(0).getName());
        ps.sortProducts(productList, 2, 1);
        assertEquals(NAME, productList.get(0).getName());

    }


    @Test
    public void testCheckForStock() {
        Mockito.when(productDao.create(eq(SELLERID), eq(CATEGORYID), eq(NAME), eq(DESCRIPTION), eq(STOCK), eq(PRICE), anyLong())).thenReturn(new Product(PRODUCTID, SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, IMAGEID));

        final Product newProduct = ps.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE, null);
        Boolean bool = ps.checkForAvailableStock(newProduct, 20);
        assertEquals(bool, false);
        bool = ps.checkForAvailableStock(newProduct, 5);
        assertEquals(bool, true);
    }




}
