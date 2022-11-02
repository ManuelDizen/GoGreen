package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface PasswordService {

    Token create(String passToken, User user);

    Optional<Token> getByUserId(User user);
    void passwordToken(String path, User user);

    Optional<User> getByToken(String token);

}
