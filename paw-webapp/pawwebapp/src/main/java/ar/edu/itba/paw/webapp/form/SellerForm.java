package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.FieldMatch;
import ar.edu.itba.paw.webapp.validations.Password;
import ar.edu.itba.paw.webapp.validations.UniqueUserMail;
import ar.edu.itba.paw.webapp.validations.ValidPhone;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(
        first = "password",
        matching = "confirmationPassword"
)
public class SellerForm {
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

    @Password
    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    private String confirmationPassword;

    @NotNull
    @NotEmpty
    @Size(max = 50)
    private String address;

    @NotNull
    @NotEmpty
    @ValidPhone
    private String phone;

    @NotNull
    @Min(value=1)
    private long area;

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

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //Agregué Area con getter y setter
    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }
}
