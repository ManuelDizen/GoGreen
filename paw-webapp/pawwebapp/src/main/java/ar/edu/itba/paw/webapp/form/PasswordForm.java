package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.NoValidToken;
import ar.edu.itba.paw.webapp.validations.UniqueUserMail;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PasswordForm {
    //TODO: Could set a validator to check if a current token has been created.
    //   If so, notify of error to user
    // Update: Ya arme este, me falta armar otro que valide que el mail exista
    @Email
    @Size(min = 8, max = 50)
    @NotNull
    @NoValidToken
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
