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
    public ModelAndView productNotFoundError(ProductNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No user found")
    public ModelAndView userNotFoundError(UserNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No order found")
    public ModelAndView orderNotFoundError(OrderNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Role not found")
    public ModelAndView roleNotFoundError(RoleNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(UnauthorizedRoleException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Unauthorized to perform action")
    public ModelAndView unauthorizedRoleError(UnauthorizedRoleException e){
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

    @ExceptionHandler(ProductCreationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Error creating product")
    public ModelAndView productCreationError(ProductCreationException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error500");
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Not enough stock available")
    public ModelAndView insufficientStockError(InsufficientStockException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error400");
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

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Token Not Found")
    public ModelAndView tokenNotFoundError(TokenNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error400");
    }
    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Article Not Found")
    public ModelAndView articleNotFoundError(ArticleNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Comment Not Found")
    public ModelAndView commentNotFoundError(CommentNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="Image Not Found")
    public ModelAndView imageNotFoundError(ImageNotFoundException e){
        LOGGER.warn(e.getErrMsg());
        return new ModelAndView("forward:/error404");
    }

    @ExceptionHandler(ArticleCreationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Error creating article")
    public ModelAndView articleCreationError(ArticleCreationException e){
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
