package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.OrderDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.exceptions.InsufficientStockException;
import ar.edu.itba.paw.models.exceptions.UnauthorizedRoleException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderDao orderDao;
    @Mock
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;
    @Mock
    private SellerService sellerService;

    @Test
    public void testCreateOrder(){
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(productService.checkForAvailableStock(any(), anyInt())).thenReturn(true);
        when(userService.getLoggedUser()).thenReturn(AUX_USER);
        when(sellerService.findById(anyLong())).thenReturn(Optional.of(AUX_SELLER));
        when(orderDao.create(anyString(), anyString(), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyInt(), anyInt(), any(), anyString())).thenReturn(AUX_ORDER);
        doNothing().when(emailService).itemsold(anyString(), any(), any(),
                anyInt(), anyInt(), anyString(), anyString(), anyString(), any());
        when(sellerService.getName(anyLong())).thenReturn(SELLER_NAME);
        when(sellerService.getSurname(anyLong())).thenReturn(SELLER_SURNAME);
        when(sellerService.getEmail(anyLong())).thenReturn(SELLER_EMAIL);



        Order order = null;
        try{
           order = orderService.create(AUX_PRODUCT_ID, AUX_ORDER_AMOUNT, AUX_ORDER_MSG);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Error creating order: " + e.getMessage());
        }
        Assert.assertNotNull(order);
        Assert.assertEquals(order.getProductName(), AUX_PRODUCT.getName());
    }

    @Test
    public void testCreateOrderUnlogged(){
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(productService.checkForAvailableStock(any(), anyInt())).thenReturn(true);
        when(userService.getLoggedUser()).thenReturn(null);

        Assert.assertThrows(UnauthorizedRoleException.class,
                () -> orderService.create(AUX_PRODUCT.getProductId(), AUX_ORDER_AMOUNT, AUX_ORDER_MSG));
    }

    @Test
    public void testCreateOrderAsSeller(){
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(productService.checkForAvailableStock(any(), anyInt())).thenReturn(true);
        when(userService.getLoggedUser()).thenReturn(AUX_USER_FOR_SELLER);
        when(userService.isLoggedSeller()).thenReturn(true);

        Assert.assertThrows(UnauthorizedRoleException.class,
                () -> orderService.create(AUX_PRODUCT.getProductId(), AUX_ORDER_AMOUNT, AUX_ORDER_MSG));
    }

    @Test
    public void testCreateOrderWithNoStockAvailable(){
        when(productService.getById(anyLong())).thenReturn(Optional.of(AUX_PRODUCT));
        when(productService.checkForAvailableStock(any(), anyInt())).thenReturn(false);

        Assert.assertThrows(InsufficientStockException.class,
                () -> orderService.create(AUX_PRODUCT.getProductId(), AUX_ORDER_AMOUNT, AUX_ORDER_MSG));
    }
}
