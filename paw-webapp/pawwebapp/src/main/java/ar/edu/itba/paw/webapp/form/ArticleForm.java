package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

public class ArticleForm {

    @Size(min = 1, max = 1023)
    private String message;

    @FileType(types = {"image/png", "image/jpeg"})
    @FileSize(bytes = (10*1024*1024))
    private MultipartFile image;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
