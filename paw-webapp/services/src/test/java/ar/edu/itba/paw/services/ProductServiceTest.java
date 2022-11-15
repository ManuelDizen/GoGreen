package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ProductDao;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Product;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;
    @Mock
    private ImageService imageService;
    @Mock
    private UserService userService;
    @Mock
    private SellerService sellerService;

    @Test
    public void testCreateProduct(){
        when(imageService.getById(0)).thenReturn(Optional.of(AUX_IMAGE));
        when(productDao.create(any(), anyLong(), anyString(), anyString(), anyInt(),
                anyInt(), any())).thenReturn(AUX_PRODUCT);

        Product product = null;
        try{
            product = productService.create(AUX_SELLER, CATEGORY_ID, PRODUCT_NAME, PRODUCT_DESC,
                    PRODUCT_STOCK, PRODUCT_PRICE, IMAGE_BYTES);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Error creating product: " + e.getMessage());
        }
        Assert.assertNotNull(product);
        Assert.assertEquals(AUX_SELLER.getId(), product.getSeller().getId());
        Assert.assertEquals(PRODUCT_NAME, product.getName());
    }

    @Test
    public void testDeleteProduct(){
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(sellerService.findById(anyLong())).thenReturn(Optional.of(AUX_SELLER));
        when(userService.findById(anyLong())).thenReturn(Optional.of(AUX_USER_FOR_SELLER));

        try{
            productService.attemptDelete(AUX_PRODUCT.getProductId());
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Deleting product failed: " + e.getMessage());
        }
    }

    @Test
    public void testPauseProduct(){
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(sellerService.findById(anyLong())).thenReturn(Optional.of(AUX_SELLER));
        when(userService.findById(anyLong())).thenReturn(Optional.of(AUX_USER_FOR_SELLER));

        try{
            productService.attemptPause(AUX_PRODUCT.getProductId());
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Deleting product failed: " + e.getMessage());
        }
    }
    @Test
    public void testRepublishProduct(){
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(sellerService.findById(anyLong())).thenReturn(Optional.of(AUX_SELLER));
        when(userService.findById(anyLong())).thenReturn(Optional.of(AUX_USER_FOR_SELLER));

        try{
            productService.attemptRepublish(AUX_PRODUCT.getProductId());
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Deleting product failed: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteIfNotLogged(){
        when(userService.getLoggedUser()).thenReturn(null);
        Assert.assertThrows(UnauthorizedRoleException.class,
                () -> productService.attemptDelete(AUX_PRODUCT.getProductId()));
    }

    @Test
    public void testDeleteIfNotOwner(){
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(sellerService.findById(anyLong())).thenReturn(Optional.empty());
        Assert.assertThrows(UnauthorizedRoleException.class,
                () -> productService.attemptDelete(AUX_PRODUCT.getProductId()));
    }
}
