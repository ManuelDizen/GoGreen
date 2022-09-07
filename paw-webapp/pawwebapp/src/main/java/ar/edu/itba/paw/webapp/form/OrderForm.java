package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderForm {
    @Size(max = 20)
    private String name;
    @Size(max = 50)
    @Email
    private String mail;
    @Size(max = 20)
    @Pattern(regexp="[0-9]{8,10}", message="Por favor, introducir su celular SIN código de area")
    private String phone;
    @Size(max = 1000)
    private String message;
    @Min(value = 1, message = "Por favor, introduzca una cantidad válida (mínimo 1 unidad)")
    private Integer amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
