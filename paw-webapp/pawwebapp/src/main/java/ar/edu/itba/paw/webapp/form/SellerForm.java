package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.Password;
import ar.edu.itba.paw.webapp.validations.UniqueUserMail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SellerForm {
    @Size(max = 50)
    @NotNull
    @NotEmpty(message="Por favor, indicar su nombre")
    private String firstName;

    @Size(max = 100)
    @NotNull
    @NotEmpty(message = "Por favor, indicar su apellido")
    private String surname;

    @Email
    @Size(min = 8, max = 50, message = "El mail debe tener como mínimo 8 caracteres y máximo 50.")
    @UniqueUserMail(message = "Este email ya se encuenta registrado. Por favor, intente con otro.")
    @NotNull(message = "Por favor, indicar su mail")
    private String email;

    @Password
    @NotNull
    @NotEmpty(message = "Por favor, indicar la contraseña")
    private String password;

    @NotNull
    @NotEmpty(message="Por favor, indicar su nombre de usuario")
    @Size(min = 8, max = 50, message="El nombre de usuario debe tener entre 8 y 50 caracteres")
    private String username;

    @NotNull
    @NotEmpty(message="Por favor, indicar su dirección")
    @Size(max = 100, message="La dirección ingresada es inusualmente larga. Por favor, indique una dirección válida.")
    private String address;

    @NotNull
    @NotEmpty(message="Por favor, indicar su teléfono")
    @Size(min = 8, max = 11, message="Indique su teléfono sin espacios ni código de area. Ejemplo: 1100001111, o 11112222")
    private String phone;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
