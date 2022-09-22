package ar.edu.itba.paw.webapp.form;



import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
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

    @Min(value=1, message="Por favor, introduzca un stock válido")
    @Max(value=10000, message="El máximo para publicar son 10000 unidades.")
    private Integer stock;

    @FileType(types = {"image/png", "image/jpeg"}, message="Por favor, use archivos de extensión .png o .jpeg.")
    @FileSize(bytes = (10*1024*1024), message="El archivo pasa los 10MB.")
    private MultipartFile image;

    @NotNull(message="Por favor, escoja al menos una categoría")
    private long[] ecotag;

    private long category;


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

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

}
