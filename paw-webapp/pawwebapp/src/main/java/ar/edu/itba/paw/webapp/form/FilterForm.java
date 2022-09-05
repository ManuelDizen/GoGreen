package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

public class FilterForm {
    @Size(max = 50)
    private String name;

    @Max(value=100000, message="Price must be equal or less to $100.000")
    private Integer price;

    @Size(max = 50)
    private String category;

    //private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
