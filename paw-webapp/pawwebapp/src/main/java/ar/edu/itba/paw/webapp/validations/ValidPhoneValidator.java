package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {

    /* EXtracted from https://www.baeldung.com/java-regex-validate-phone-numbers
    final Pattern pattern = Pattern.compile(
            "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?");
     */
    final Pattern pattern = Pattern.compile(
            "^\\s?(\\+[0-9]{1,3}[ \\-]*)?" +
                    "([0-9]{1,4}[ \\-]*){2,4}\\s?"
    );

    final Pattern patternSuggestion = Pattern.compile(
            "^\\s?(\\+[0-9]{1,3}[ \\-]*)?" +
            "(([0-9]{1,3}[ \\-]*){1,2})?"+
            "(([0-9]{3,4}[ \\-]*){2})\\s?"
    );


    @Override
    public void initialize(ValidPhone validPhone) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(s).matches();
    }
}
