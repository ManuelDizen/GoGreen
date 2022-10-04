package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.Order;
import ar.edu.itba.paw.models.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping(value = "/error400", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error400() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "400");
        return mav;
    }
    @RequestMapping(value = "/error401", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error401() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "401");
        return mav;
    }

    @RequestMapping(value = "/error404", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error404() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "404");
        return mav;
    }

    @RequestMapping(value = "/error500", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error500() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "500");
        return mav;
    }

    @RequestMapping(value = "/error503", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error503() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("error", "503");
        return mav;
    }

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

}
