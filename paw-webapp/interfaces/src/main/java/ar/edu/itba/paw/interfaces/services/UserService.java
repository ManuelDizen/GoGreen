package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {

    User register(String firstName, String surname, String email, String password, Locale locale);
    void registerUser(String firstName, String surname, String email, String password, Locale locale);
    Optional<User> findByEmail(String email);
    Optional<User> findById(long userId);
    void toggleNotifications();
    void changePassword(long userId, String newPassword);
    boolean isSeller(long userId);
    void setProfilePic(byte[] image);
    void deleteProfilePic();
    User getLoggedUser();
    String getLoggedEmail();
    boolean isLoggedUser();
    boolean isLoggedSeller();
}
