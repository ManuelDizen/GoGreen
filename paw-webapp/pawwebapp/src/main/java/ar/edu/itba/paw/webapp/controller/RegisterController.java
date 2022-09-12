package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.SellerForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

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

    @RequestMapping(value = "/registerbuyerprocess", method = RequestMethod.POST)
    public ModelAndView registerBuyerPost(
            @Valid @ModelAttribute("userForm") final UserForm form,
            final BindingResult errors){
        if(errors.hasErrors()) {
            return registerBuyer(form);
        }
        User user = userService.register(form.getFirstName(), form.getSurname(), form.getEmail(), form.getUsername(), form.getPassword());
        // Seteo rol para comprador
        Optional<Role> role = roleService.getByName("USER");
        if(!role.isPresent())
            throw new IllegalStateException("No se enconetró el rol.");
        userRoleService.create(user.getId(), role.get().getId());
        return new ModelAndView("redirect:/userProfile");
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
        return new ModelAndView("index");
    }
}
