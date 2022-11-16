package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.interfaces.services.PasswordService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Token;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class NoValidTokenValidator implements ConstraintValidator<NoValidToken, String> {

    @Autowired
    private PasswordService passwordService;
    @Autowired
    private UserService userService;

    @Override
    public void initialize(NoValidToken noValidToken) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userService.findByEmail(s);
        if(!user.isPresent()) return false;
        Optional<Token> maybeToken = passwordService.getByUser(user.get());
        return !(maybeToken.isPresent() && maybeToken.get().isValid());

    }
}
