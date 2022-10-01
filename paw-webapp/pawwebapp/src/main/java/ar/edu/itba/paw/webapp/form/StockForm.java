package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.*;

public class StockForm {

    @Min(value=1)
    @Max(value=10000)
    private Integer newStock;

    public Integer getNewStock() {
        return newStock;
    }

    public void setNewStock(Integer newStock) {
        this.newStock = newStock;
    }
}
