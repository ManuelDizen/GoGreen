package ar.edu.itba.paw.webapp.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidPhoneValidator implements ConstraintValidator<ValidPhone, String> {

    /* EXtracted from https://www.baeldung.com/java-regex-validate-phone-numbers
    final Pattern pattern = Pattern.compile(
            "^\\s?((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?\\s?");
     */

    // Esta tiene codigo de area opcional, y 4 bloques posibles (minimo 2) de entre 1 y 4 numeros
    // lo que permtie que +54 1 1 1 1 sea un numero valido
    final Pattern pattern = Pattern.compile(
            "^\\s?(\\+[0-9]{1,3}[ \\-]*)?" +
                    "([0-9]{1,4}[ \\-]*){2,4}\\s?"
    );

    // La diferencia aca es que puede aparecer algo como +54 1 1 pero los siguientes dos
    // bloques de numeros deben tener entre 3 y 4 caracteres (y son obligatorios).

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
