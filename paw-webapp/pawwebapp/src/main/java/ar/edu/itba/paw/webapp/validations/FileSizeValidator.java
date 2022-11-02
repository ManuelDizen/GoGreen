package ar.edu.itba.paw.webapp.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private long bytes;

    @Override
    public void initialize(FileSize fileSize) {
        this.bytes = fileSize.bytes();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile == null || multipartFile.getSize() == 0) return false;
        try {
            multipartFile.getBytes();
        } catch (IOException e) {
            return false;
        }
        return multipartFile.getSize() <= bytes;
    }
}
