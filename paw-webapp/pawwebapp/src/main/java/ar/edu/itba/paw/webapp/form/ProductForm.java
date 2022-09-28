package ar.edu.itba.paw.webapp.form;



import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;

import ar.edu.itba.paw.webapp.validations.UniqueProductName;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

public class ProductForm {

    @Size(min = 5, max = 50)
    @NotNull
    @UniqueProductName
    private String name;

    @Size(max = 300)
    private String description;

    //@Min(value=1, message= "Por favor, introduzca un precio válido")
    //@Max(value=999999, message="El precio debe ser menor a un millón")
    @Size(min=1, max=6, message = "Precio no debe exceder un millón")
    @NotNull
    private String price;

    //@Min(value=1, message="Por favor, introduzca un stock válido")
    //@Max(value=10000, message="El máximo para publicar son 10000 unidades.")
    @Size(min=1, max=4, message = "Máximo 10000 unidades")
    @NotNull
    private String stock;

    @FileType(types = {"image/png", "image/jpeg"}, message="Por favor, use archivos de extensión .png o .jpeg.")
    @FileSize(bytes = (10*1024*1024))
    private MultipartFile image;


    private long[] ecotag;

    @Min(value=1, message="Por favor, escoja al menos una categoría")
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


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
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
