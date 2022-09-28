package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.*;

public class StockForm {

    @NotNull
    @Size(max=4)
    @Pattern(regexp = "[0-9]+")
    private String newStock;

    public String getNewStock() {
        return newStock;
    }

    public void setNewStock(String newStock) {
        this.newStock = newStock;
    }
}
