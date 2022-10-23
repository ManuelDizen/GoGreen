package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class SellerServiceTest {

    private static final long USERID = 1;
    private static final long SELLERID = 1;
    private static final String PHONE = "11111111";
    private static final String ADDRESS = "Address";
    
    private static final long AREAID = 1;

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private SellerDao sellerDao;


    /*@Test
    public void testCreate() {

        Mockito.when(sellerDao.create(eq(USERID), eq(PHONE), eq(ADDRESS), eq(AREAID))).thenReturn(new Seller(SELLERID, USERID, PHONE, ADDRESS, AREAID));

        final Seller seller = sellerService.create(USERID, PHONE, ADDRESS, AREAID);
        assertNotNull(seller);
        //assertEquals(SELLERID, seller.getId());
        assertEquals(PHONE, seller.getPhone());
        assertEquals(ADDRESS, seller.getAddress());
        assertEquals(AREAID, seller.getAreaId());

    }*/


}
