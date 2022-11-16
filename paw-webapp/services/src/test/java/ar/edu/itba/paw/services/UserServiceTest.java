package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Optional;

import static ar.edu.itba.paw.services.TestServicesResources.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    //TODO!!!!
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDao userDao;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private EmailService emailService;

    @Test
    public void testRegisterUser(){
        when(userDao.create(anyString(), anyString(), anyString(), anyString(), any())).thenReturn(AUX_USER);
        when(roleService.getByName(anyString())).thenReturn(Optional.of(AUX_USER_ROLE));
        when(encoder.encode(anyString())).thenReturn(AUX_USER_PASSWORD);
        doNothing().when(emailService).registration(any(), any());

        try{
            userService.registerUser(AUX_USER_NAME, AUX_USER_SURNAME, AUX_USER_EMAIL,
                    AUX_USER_PASSWORD, Locale.US);
        }
        catch(Exception e){
            System.out.println(e.getClass());
            Assert.fail("Error creating user: " + e.getMessage());
        }
    }
}
