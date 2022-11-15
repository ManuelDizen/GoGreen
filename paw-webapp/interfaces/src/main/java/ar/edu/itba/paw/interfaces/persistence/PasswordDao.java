package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordDao {

    Token create(String passToken, User user, LocalDateTime dateTime);

    Optional<Token> getByUserId(User user);
    Optional<Token> getByToken(String token);

}
