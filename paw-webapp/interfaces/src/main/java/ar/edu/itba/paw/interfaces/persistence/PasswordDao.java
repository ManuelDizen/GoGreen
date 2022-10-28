package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;

public interface PasswordDao {

    Token create(String passToken, User user);

}
