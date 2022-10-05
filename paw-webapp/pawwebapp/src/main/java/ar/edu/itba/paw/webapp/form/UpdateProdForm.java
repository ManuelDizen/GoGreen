package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UpdateProdForm {

    @Min(value=1)
    @Max(value = 10000)
    private Integer newStock;

    @Min(value=1)
    @Max(value = 1000000)
    private Integer newPrice;

    public Integer getNewStock() {
        return newStock;
    }

    public void setNewStock(Integer newStock) {
        this.newStock = newStock;
    }


    public Integer getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Integer newPrice) {
        this.newPrice = newPrice;
    }
}
