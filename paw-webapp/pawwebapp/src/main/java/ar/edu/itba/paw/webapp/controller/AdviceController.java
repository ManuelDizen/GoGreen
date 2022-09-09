package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
    public ModelAndView productNotFound() {
        final ModelAndView mav = new ModelAndView("forward:/error404");
        return mav;
    }

}
