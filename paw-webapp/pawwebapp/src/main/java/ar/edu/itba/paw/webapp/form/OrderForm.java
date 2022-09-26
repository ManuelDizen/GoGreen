package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

public class OrderForm {
    @Size(max = 1023)
    private String message;

    @Min(value = 1, message = "Por favor, introduzca una cantidad válida (mínimo 1 unidad)")
    @Max(value = 5, message = "El límite de compra son 5 unidades.")
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
