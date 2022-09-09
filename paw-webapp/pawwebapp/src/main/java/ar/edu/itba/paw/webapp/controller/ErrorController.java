package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error404() {
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }

    @RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error500() {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("name", "Error 500");
        return mav;
    }

    @RequestMapping(value = "/503", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView error503() {
        ModelAndView mav = new ModelAndView("error");
        return mav;
    }

}
