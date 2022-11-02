package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdviceController.class);

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No product found")
    public ModelAndView productNotFound(ProductNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No user found")
    public ModelAndView userNotFound(UserNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No order found")
    public ModelAndView orderNotFound(OrderNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Role not found")
    public ModelAndView orderNotFound(RoleNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(UnauthorizedRoleException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Unauthorized to perform action")
    public ModelAndView unauthorizedRole(UnauthorizedRoleException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error401");
    }

    @ExceptionHandler(EmailErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView emailError(EmailErrorException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(OrderCreationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView orderCreationError(OrderCreationException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(OrderDeleteException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView orderDeleteError(OrderDeleteException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(ProductDeleteException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView productDeleteError(ProductDeleteException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(ProductUpdateException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView productUpdateError(ProductUpdateException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(SellerRegisterException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView sellerRegisterError(SellerRegisterException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(UserRegisterException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error")
    public ModelAndView userRegisterError(UserRegisterException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Article Not Found")
    public ModelAndView articleNotFoundError(ArticleNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(ArticleCreationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Error creating article")
    public ModelAndView articleNotFoundError(ArticleCreationException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(ForbiddenActionException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Unauthorized")
    public ModelAndView forbiddenActionError(ForbiddenActionException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error401");
    }

}
