package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;

public interface PasswordService {

    Token create(String passToken, User user);

    void passwordToken(String path, User user);

}
