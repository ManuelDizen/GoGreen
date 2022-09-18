package ar.edu.itba.paw.webapp.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {FileSizeValidator.class})
public @interface FileSize {

    String message() default "Maximum file size: 10MB";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    long bytes();

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        long bytes();
    }
}