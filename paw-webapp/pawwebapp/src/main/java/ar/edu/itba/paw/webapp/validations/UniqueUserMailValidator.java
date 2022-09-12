package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUserMailValidator implements ConstraintValidator<UniqueUserMail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueUserMail uniqueUserMail) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userService.findByEmail(s);
        return !user.isPresent();
    }
}
