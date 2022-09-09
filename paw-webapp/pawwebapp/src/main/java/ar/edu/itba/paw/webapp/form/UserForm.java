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
    private String name;

    @Size(max = 100)
    @NotNull
    @NotEmpty
    private String surname;

    @Email
    @Size(min = 8, max = 50)
    @UniqueUserMail
    private String email;

    @Password
    @NotNull
    @NotEmpty
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
