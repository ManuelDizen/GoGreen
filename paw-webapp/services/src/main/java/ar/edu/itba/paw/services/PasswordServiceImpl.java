package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.PasswordDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PasswordService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordDao passwordDao;

    private final EmailService emailService;

    private final UserService userService;

    @Autowired
    public PasswordServiceImpl(final PasswordDao passwordDao, final EmailService emailService, UserService userService) {
        this.passwordDao = passwordDao;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    public Token create(String passToken, User user) {
        LocalDateTime dateTime = LocalDateTime.now();
        return passwordDao.create(passToken, user, dateTime);
    }

    @Transactional
    @Override
    public void passwordToken(String path, User user) {
        if(!hasUsableToken(user)) {
            Token token = create(UUID.randomUUID().toString(), user);
            emailService.updatePassword(
                    user, path + token.getPassToken(), user.getLocale());
        }
    }

    public Optional<Token> getByUser(User user) {
        return passwordDao.getByUserId(user);
    }

    private boolean hasUsableToken(User user) {
        Optional<Token> maybeToken =  passwordDao.getByUserId(user);
        if(!maybeToken.isPresent()) {
            return false;
        }
        Token userToken = maybeToken.get();
        return userToken.isValid();
    }

    public Optional<User> getByToken(String token) {
        Optional<Token> maybeToken =  passwordDao.getByToken(token);
        if(!maybeToken.isPresent())
            throw new UserNotFoundException();

        Token userToken = maybeToken.get();
        return userService.findById(userToken.getUser().getId());
    }

    public void burnToken(User user){
        Optional<Token> token = getByUser(user);
        if(!token.isPresent()) return; //TODO: Create custom exception
        token.get().use();
    }

    @Override
    public void updatePassword(String token, String password){
        Optional<User> maybeUser = getByToken(token);
        if(!maybeUser.isPresent())
            throw new UserNotFoundException();
        userService.changePassword(maybeUser.get().getId(), password);
        burnToken(maybeUser.get());
    }

}
