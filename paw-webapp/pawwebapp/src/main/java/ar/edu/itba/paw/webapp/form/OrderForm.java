package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderForm {
    @Size(max = 1023)
    private String message;

    // TODO: Custom validation @CheckForStock
    @Min(value = 1, message = "Por favor, introduzca una cantidad válida (mínimo 1 unidad)")
    private Integer amount;

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
