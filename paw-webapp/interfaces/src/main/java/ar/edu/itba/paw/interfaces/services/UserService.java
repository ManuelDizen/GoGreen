package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User register(String firstName, String surname, String email, String password, Locale locale);
    void registerUser(String firstName, String surname, String email, String password, Locale locale);

    //void updateImage(long userId, long imageId);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);

    List<User> getAll();
    void toggleNotifications(long userId);

    void changePassword(long userId, String newPassword);

    boolean isValidPassword(long userId, String oldPassword);

    boolean isSeller(long userId);

    boolean isBuyer(long userId);
}
