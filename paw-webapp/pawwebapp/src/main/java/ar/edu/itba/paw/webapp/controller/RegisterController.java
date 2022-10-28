package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        Boolean success = userService.registerUser(form.getFirstName(), form.getSurname(), form.getEmail(),
                form.getPassword(), LocaleContextHolder.getLocale());
        if(!success) throw new UserRegisterException();
        authWithAuthManager(request, form.getEmail(), form.getPassword());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value="/registerseller", method=RequestMethod.GET)
    public ModelAndView registerSeller(
            @ModelAttribute("sellerForm") final SellerForm form
    ){
        final ModelAndView mav = new ModelAndView("registerseller");
        mav.addObject("areas", Area.values()); //solo esta línea
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
        Boolean success = sellerService.registerSeller(form.getFirstName(), form.getSurname(),
                form.getEmail(), form.getPassword(), LocaleContextHolder.getLocale(), form.getPhone(),
                form.getAddress(), form.getArea());
        if(!success) throw new SellerRegisterException();
        authWithAuthManager(request, form.getEmail(), form.getPassword());

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public ModelAndView forgotMyPassword(@ModelAttribute("passwordForm") final PasswordForm passwordForm) {
        return new ModelAndView("forgotPassword");
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ModelAndView updateMyPassword(@Valid @ModelAttribute("passwordForm") final PasswordForm passwordForm, final BindingResult errors, HttpServletRequest request) {

        Optional<User> maybeUser = userService.findByEmail(passwordForm.getEmail());
        if(!maybeUser.isPresent())
            throw new UserNotFoundException();
        User user = maybeUser.get();
        String path = request.getRequestURL().toString().replace(request.getServletPath(), "") + "/newPassword?token=";
        passwordService.passwordToken(path, user);
        return new ModelAndView("redirect:/login");
    }
    @RequestMapping(value = "/newPassword")
    public ModelAndView newPassword(@RequestParam(name = "token") final String token, @ModelAttribute("updatePasswordForm") final UpdatePasswordForm updatePasswordForm) {

        ModelAndView mav = new ModelAndView("/resetPassword");
        mav.addObject("token", token);
        return mav;

    }

    @RequestMapping(value = "/confirmPassword", method = RequestMethod.POST)
    public ModelAndView confirmPassword(@Valid @ModelAttribute("updatePasswordForm") final UpdatePasswordForm updatePasswordForm, final BindingResult errors) {


        if(errors.hasErrors()) {
            newPassword(updatePasswordForm.getToken(), updatePasswordForm);
        }

        Optional<User> maybeUser = passwordService.getByToken(updatePasswordForm.getToken());

        if(!maybeUser.isPresent())
            throw new UserNotFoundException();

        System.out.println("llegué!! ");

        userService.changePassword(maybeUser.get().getId(), updatePasswordForm.getPassword());

        ModelAndView mav = new ModelAndView("redirect:/login");
        return mav;

    }


    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
