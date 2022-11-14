package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.PasswordDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.PasswordService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.exceptions.TokenNotFoundException;
import ar.edu.itba.paw.services.exceptions.UserNotFoundException;
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
    public void passwordToken(String path, String email) {
        Optional<User> maybeUser = userService.findByEmail(email);
        if(!maybeUser.isPresent()){throw new UserNotFoundException();} //This line should never be reached
        User user = maybeUser.get();
        if(!hasUsableToken(user)) {
            Token token = create(UUID.randomUUID().toString(), user);
            emailService.updatePassword(
                    user, path + token.getPassToken(), user.getLocale());
        }
    }

    public Optional<Token> getByUser(User user) {
        //TODO: Este método esta mal. Un usuario puede solicitar mas de un cambio de contraseña,
        //  y ahi se va a romper porque va a encontrar dos por userId.
        //  Opción A: Borrar tupla de token cuando se usa
        //  Opción B: modificar este método y que traiga el último
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

    @Transactional
    public void burnToken(User user){
        Optional<Token> token = getByUser(user);
        if(!token.isPresent()) throw new TokenNotFoundException();
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
