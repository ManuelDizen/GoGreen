package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Password;

import ar.edu.itba.paw.webapp.validations.UniqueUserMail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserForm {

    @Size(max = 50)
    @NotNull
    @NotEmpty
    private String firstName;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    private String surname;

    @Email
    @Size(min = 8, max = 50)
    @UniqueUserMail
    @NotNull
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 50)
    private String username;

    @Password
    @NotNull
    @NotEmpty
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
