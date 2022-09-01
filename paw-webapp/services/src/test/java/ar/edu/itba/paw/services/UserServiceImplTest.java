package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String EMAIL="foo@bar.com";
    private static final String PASSWORD="secret";

    @InjectMocks
    private UserServiceImpl us;

    @Mock
    private UserDao userDao;

//    @Before
//    public void setUp() {
//        us = new UserServiceImpl(userDao);
//    }

    @Test
    public void testCreate() {

        Mockito.when(userDao.create(eq(EMAIL), eq(PASSWORD))).thenReturn(new User(1, EMAIL, PASSWORD));

        final User newUser = us.register(EMAIL, PASSWORD);
        assertNotNull(newUser);
        assertEquals(EMAIL, newUser.getEmail());
    }

    @Test
    public void testFindByEmail() {
        Mockito.when(userDao.findByEmail(eq(EMAIL))).thenReturn(Optional.of(new User(1, EMAIL, PASSWORD)));

        final Optional<User> maybeUser = us.findByEmail(EMAIL);

        assertTrue(maybeUser.isPresent());
        assertEquals(EMAIL, maybeUser.get().getEmail());

    }

}