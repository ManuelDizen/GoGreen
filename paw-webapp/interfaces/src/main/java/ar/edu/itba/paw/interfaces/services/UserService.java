package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User register(String firstName, String surname, String email, String password, Locale locale);
    Boolean registerUser(String firstName, String surname, String email, String password, Locale locale);

    void updateImage(long userId, long imageId);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);
}
