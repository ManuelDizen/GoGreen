package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class OrderForm {
    @Size(max = 255)
    private String message;

    @Min(value = 1)
    @Max(value = 5)
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
