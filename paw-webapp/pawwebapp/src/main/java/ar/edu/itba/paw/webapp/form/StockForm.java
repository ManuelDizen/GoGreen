package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class StockForm {

    @Min(value=1,message="Por favor, introduzca una cantidad válida.")
    @Max(value = 10000, message="El máximo stock permitido es de 10000 unidades.")
    private Integer newStock;

    public Integer getNewStock() {
        return newStock;
    }

    public void setNewStock(Integer newStock) {
        this.newStock = newStock;
    }
}
