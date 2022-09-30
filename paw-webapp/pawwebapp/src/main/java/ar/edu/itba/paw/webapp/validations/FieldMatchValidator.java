package ar.edu.itba.paw.webapp.validations;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String first;
    private String matching;

    /* Extracted from https://memorynotfound.com/field-matching-bean-validation-annotation-example/ */
    public void initialize(FieldMatch constraintAnnotation) {
        this.first = constraintAnnotation.first();
        this.matching = constraintAnnotation.matching();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        Object firstVal = new BeanWrapperImpl(value).getPropertyValue(first);
        Object matchingVal = new BeanWrapperImpl(value).getPropertyValue(matching);

        boolean isValid = Objects.equals(firstVal, matchingVal);
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Las contraseñas deben coincidir.")
                    .addNode(matching).addConstraintViolation();
        }
        return isValid;
    }
}
