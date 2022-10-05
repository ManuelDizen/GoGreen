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

    @NotNull
    @Size(max=6)
    @Pattern(regexp = "[0-9]+")
    private String price;

    @NotNull
    @Size(max=4)
    @Pattern(regexp = "[0-9]+")
    private String stock;

    @FileType(types = {"image/png", "image/jpeg"})
    @FileSize(bytes = (10*1024*1024))
    private MultipartFile image;


    private long[] ecotag;

    @Min(value=1)
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
