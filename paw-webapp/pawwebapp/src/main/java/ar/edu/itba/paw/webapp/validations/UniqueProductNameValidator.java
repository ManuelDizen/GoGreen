package ar.edu.itba.paw.webapp.validations;

import ar.edu.itba.paw.interfaces.services.ProductService;
import ar.edu.itba.paw.models.Product;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    @Autowired
    private ProductService productService;

    @Override
    public void initialize(UniqueProductName uniqueProductName) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Product> maybeProduct = productService.getByName(s);
        return !maybeProduct.isPresent();
    }
}
