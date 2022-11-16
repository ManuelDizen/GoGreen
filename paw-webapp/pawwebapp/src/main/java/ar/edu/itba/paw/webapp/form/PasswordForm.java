package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.EmailExists;
import ar.edu.itba.paw.webapp.validations.NoValidToken;
import ar.edu.itba.paw.webapp.validations.UniqueUserMail;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordForm {
    @Email
    @Size(min = 8, max = 50)
    @NotNull
    @EmailExists
    @NoValidToken
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
