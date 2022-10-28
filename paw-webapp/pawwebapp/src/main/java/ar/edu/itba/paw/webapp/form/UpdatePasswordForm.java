package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Password;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UpdatePasswordForm {

    @Password
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmationPassword;

    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
