package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.RoleService;
import ar.edu.itba.paw.interfaces.services.SellerService;
import ar.edu.itba.paw.interfaces.services.UserRoleService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.Seller;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.SellerForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private AuthenticationManager authenticationManager;

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
            final BindingResult errors,
            HttpServletRequest request){
        if(errors.hasErrors()) {
            return registerBuyer(form);
        }
        User user = userService.register(form.getFirstName(), form.getSurname(), form.getEmail(),
                form.getUsername(), form.getPassword());
        Optional<Role> role = roleService.getByName("USER");
        if(!role.isPresent())
            throw new IllegalStateException("No se encontró el rol.");
        userRoleService.create(user.getId(), role.get().getId());

        authWithAuthManager(request, user.getEmail(), user.getPassword());

        return new ModelAndView("redirect:/userProfile");
    }

    @RequestMapping(value="/registerseller", method=RequestMethod.GET)
    public ModelAndView registerSeller(
            @ModelAttribute("sellerForm") final SellerForm form
    ){
        final ModelAndView mav = new ModelAndView("registerseller");
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
        User user = userService.register(form.getFirstName(), form.getSurname(), form.getEmail(),
                form.getUsername(), form.getPassword());
        if(user == null){
            throw new IllegalArgumentException("Usuario no pudo ser creado");
        }
        Seller seller = sellerService.create(user.getId(), form.getPhone(), form.getAddress());
        if(seller == null){
            throw new IllegalArgumentException("Seller no pudo ser creado");
        }
        Optional<Role> role = roleService.getByName("SELLER");
        if(!role.isPresent()){
            throw new IllegalArgumentException("Role no fue encontrado");
        }
        userRoleService.create(user.getId(), role.get().getId());

        // Para setear al usuario recién creado como activo
        // (Una convención, podríamos preguntar si es apropiado)
        // (Fuente: https://www.baeldung.com/spring-security-auto-login-user-after-registration)
        authWithAuthManager(request, user.getEmail(), user.getPassword());

        return new ModelAndView("redirect:/sellerProfile");
    }

    public void authWithAuthManager(HttpServletRequest request, String username, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
