package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.persistence.SellerDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SellerServiceTest {

    @InjectMocks
    private SellerServiceImpl sellerService;

    @Mock
    private SellerDao sellerDao;

    @Test
    public void testCreate(){
        when(sellerDao.create(any(), anyString(), anyString(), any())).thenReturn(AUX_SELLER);
        try{
            sellerService.create(AUX_USER_FOR_SELLER.getId(), SELLER_PHONE, SELLER_ADDRESS, SELLER_AREA);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Seller creation failed: " + e.getMessage());
        }
    }
}
