package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface PasswordDao {

    Token create(String passToken, User user);

    Optional<Token> getByUserId(long userId);
    Optional<Token> getByToken(String token);

}
