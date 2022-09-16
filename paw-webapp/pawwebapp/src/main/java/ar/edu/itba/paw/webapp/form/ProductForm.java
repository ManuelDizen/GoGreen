package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.Ecotag;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductForm {

    @Size(max = 50)
    @NotNull
    private String name;

    @Size(max = 300)
    private String description;

    @Min(value=1, message= "Por favor, introduzca un precio válido")
    @NotNull
    private Integer price;

    private Integer stock;

    private MultipartFile image;

    @NotNull
    private long[] ecotag;

    // Falta category!!! Que habría que pasar a un enum dado que es estático.


    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public long[] getEcotag() {
        return ecotag;
    }

    public void setEcotag(long[] ecotag) {
        this.ecotag = ecotag;
    }
}
