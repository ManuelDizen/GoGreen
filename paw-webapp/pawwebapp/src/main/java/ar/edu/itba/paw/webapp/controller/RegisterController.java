package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.PasswordService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Area;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.exceptions.SellerRegisterException;
import ar.edu.itba.paw.models.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.exceptions.UserRegisterException;
import ar.edu.itba.paw.webapp.form.PasswordForm;
import ar.edu.itba.paw.webapp.form.SellerForm;
import ar.edu.itba.paw.webapp.form.UpdatePasswordForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.Optional;

@Controller
public class RegisterController {

    private final SellerService sellerService;

    private final UserService userService;

    private final PasswordService passwordService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public RegisterController(final SellerService sellerService, final UserService userService, final PasswordService passwordService,
                              final AuthenticationManager authenticationManager) {
        this.sellerService = sellerService;
        this.userService = userService;
        this.passwordService = passwordService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value= "/register", method= RequestMethod.GET)
    public ModelAndView register(){
        return new ModelAndView("register");
    }

    @RequestMapping(value="/registerbuyer", method = RequestMethod.GET)
    public ModelAndView registerBuyer(
            @ModelAttribute("userForm") final UserForm form
    ){
        return new ModelAndView("registerbuyer");
    }

    @RequestMapping(value = "/registerbuyerprocess", method = RequestMethod.POST)
    public ModelAndView registerBuyerPost(
            @Valid @ModelAttribute("userForm") final UserForm form, final BindingResult errors, HttpServletRequest request){
        if(errors.hasErrors()) {
            return registerBuyer(form);
        }
        userService.registerUser(form.getFirstName(), form.getSurname(), form.getEmail(),
                form.getPassword(), LocaleContextHolder.getLocale());
        authWithAuthManager(request, form.getEmail(), form.getPassword());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value="/registerseller", method=RequestMethod.GET)
    public ModelAndView registerSeller(
            @ModelAttribute("sellerForm") final SellerForm form
    ){
        final ModelAndView mav = new ModelAndView("registerseller");
        mav.addObject("areas", Area.values());
        return mav;
    }

    @RequestMapping(value="/registersellerprocess", method=RequestMethod.POST)
    public ModelAndView registerSellerPost(
            @Valid @ModelAttribute("sellerForm") final SellerForm form,
            final BindingResult errors,
            HttpServletRequest request
    ){
        if(errors.hasErrors()){
            return registerSeller(form);
        }
        sellerService.registerSeller(form.getFirstName(), form.getSurname(),
                form.getEmail(), form.getPassword(), LocaleContextHolder.getLocale(), form.getPhone(),
                form.getAddress(), Area.getById(form.getArea()));
        authWithAuthManager(request, form.getEmail(), form.getPassword());

        //TODO: Check how to link if coming from product page
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotMyPassword(@RequestParam(name = "notFound", defaultValue = "false") final boolean notFound,
                                         @ModelAttribute("passwordForm") final PasswordForm passwordForm) {

        ModelAndView mav = new ModelAndView("forgotPassword");
        mav.addObject("notFound", notFound);
        return mav;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ModelAndView updateMyPassword(@Valid @ModelAttribute("passwordForm") final PasswordForm passwordForm,
                                         final BindingResult errors,
                                         HttpServletRequest request) {
        if(errors.hasErrors()) return forgotMyPassword(false, passwordForm);
        String path = request.getRequestURL().toString().replace(request.getServletPath(), "") +
                "/newPassword?token=";
        passwordService.passwordToken(path, passwordForm.getEmail());
        return new ModelAndView("congratulations");
    }
    @RequestMapping(value = "/newPassword")
    public ModelAndView newPassword(@RequestParam(name = "token") final String token,
                    @ModelAttribute("updatePasswordForm") final UpdatePasswordForm updatePasswordForm) {
        ModelAndView mav = new ModelAndView("/resetPassword");
        mav.addObject("token", token);
        return mav;
    }

    @RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
    public ModelAndView confirmPassword(
            @Valid @ModelAttribute("updatePasswordForm") final UpdatePasswordForm updatePasswordForm,
            final BindingResult errors) {
        if(errors.hasErrors()) {
            return newPassword(updatePasswordForm.getToken(), updatePasswordForm);
        }
        passwordService.updatePassword(updatePasswordForm.getToken(), updatePasswordForm.getPassword());
        return new ModelAndView("redirect:/login");
    }

    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
