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

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private static final long SELLERID = 1;
    private static final long CATEGORYID = 1;
    private static final String NAME = "P1";
    private static final String DESCRIPTION = "First product";
    private static final int STOCK = 10;
    private static final float PRICE = 100;

    @InjectMocks
    private ProductServiceImpl ps;

    @Mock
    private ProductDao productDao;


    @Test
    public void testCreate() {

        Mockito.when(productDao.create(eq(SELLERID), eq(CATEGORYID), eq(NAME), eq(DESCRIPTION), eq(STOCK), eq(PRICE))).thenReturn(new Product(1, SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE));

        final Product newProduct = ps.create(SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE);
        assertNotNull(newProduct);
        assertEquals(SELLERID, newProduct.getSellerId());
        assertEquals(NAME, newProduct.getName());

    }

    @Test
    public void testFindById() {
        Mockito.when(productDao.getById(1)).thenReturn(Optional.of(new Product(1, SELLERID, CATEGORYID, NAME, DESCRIPTION, STOCK, PRICE)));

        Optional<Product> product = ps.getById(1);
        assertTrue(product.isPresent());
        assertEquals(SELLERID, product.get().getSellerId());
    }



}
