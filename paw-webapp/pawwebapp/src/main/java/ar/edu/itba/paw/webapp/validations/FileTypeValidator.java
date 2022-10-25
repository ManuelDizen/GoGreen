package ar.edu.itba.paw.webapp.validations;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<FileType, MultipartFile> {

    private List<String> types;

    @Override
    public void initialize(FileType fileType) {
        this.types = Arrays.asList(fileType.types());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile,
                           ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile == null)
            return false;
        if(multipartFile.isEmpty())
            return true;
        String fileType = multipartFile.getContentType();
        for(String type : types){
            if(type.equals(fileType)) return true;
        }
        return false;
    }
}
