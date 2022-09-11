package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {

    User register(String firstName, String surname, String email, String username, String password);

    Optional<User> findByEmail(String email);

    Optional<User> findById(long userId);
}
