package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.webapp.form.SellerForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping(value= "/register", method= RequestMethod.GET)
    public ModelAndView register(){
        final ModelAndView mav = new ModelAndView("register");
        return mav;
    }

    @RequestMapping(value="/registerbuyer", method = RequestMethod.GET)
    public ModelAndView registerBuyer(
            @ModelAttribute("userForm") final UserForm form
    ){
        final ModelAndView mav = new ModelAndView("registerbuyer");
        return mav;
    }

    @RequestMapping(value = "/registerbuyer", method = RequestMethod.POST)
    public ModelAndView registerBuyerPost(
            @Valid @ModelAttribute("userForm") final UserForm form,
            final BindingResult errors){
        if(errors.hasErrors())
            return registerBuyer(form);
        try{
            //sellerService.create(form.getEmail(), form.getPhone(), form.getAddress(), form.getName());
        }
        catch(DuplicateKeyException e){
            errors.addError(new FieldError("form", "email", "El email está en uso"));
            return registerBuyer(form);
        }

        return new ModelAndView("");
    }

    @RequestMapping(value="/registerseller", method=RequestMethod.GET)
    public ModelAndView registerSeller(
            @ModelAttribute("sellerForm") final SellerForm form
    ){
        final ModelAndView mav = new ModelAndView("registerseller");
        return mav;
    }

    @RequestMapping(value="/registerseller", method=RequestMethod.POST)
    public ModelAndView registerSellerPost(
            @Valid @ModelAttribute("sellerForm") final SellerForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            return registerSeller(form);
        }
        return new ModelAndView("");
    }
}
