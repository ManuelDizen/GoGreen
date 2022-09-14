package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

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

}
