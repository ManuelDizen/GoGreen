package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.validations.FileSize;
import ar.edu.itba.paw.webapp.validations.FileType;
import ar.edu.itba.paw.webapp.validations.ValidImage;
import org.springframework.web.multipart.MultipartFile;

public class ProfilePicForm {
    @FileType(types = {"image/png", "image/jpeg"})
    @FileSize(bytes = (10*1024*1024))
    @ValidImage
    private MultipartFile image;


    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
