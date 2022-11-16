package ar.edu.itba.paw.interfaces.persistence;


import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserDao {

    User create(String firstName, String surname, String email, String password, Locale locale);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long userId);
}
