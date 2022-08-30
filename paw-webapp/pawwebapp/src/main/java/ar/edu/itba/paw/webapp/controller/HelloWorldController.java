package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HelloWorldController {

    private final UserService us;

    //----------------------------------------------
    //Inyeccion de dependencias por el contructor
    //----------------------------------------------

    // @Qualifier("userServiceImpl") es para indicarle cual implementacion quiero que use y evitar
    // conflictos en la inyeccion de dependencias

    //    public HelloWorldController(@Qualifier("userServiceImpl") final UserService us){
    //        this.us = us;
    //    }

    // La otra forma es agregarle @Primary a la clase que quiero que use,
    // sin embargo el @Qualifier tiene mayor prioridad

    @Autowired  //Para indicarle que este es el constructor que quiero que use
    public HelloWorldController( final UserService us){
        this.us = us;
    }

    //    @RequestMapping(value = "/", method = RequestMethod.GET, headers = ..., consumes = ..., produces = ...)
    @RequestMapping("/")
    public ModelAndView helloWorld() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", "manu");
        Optional<User> user = us.findByEmail("paw@itba.edu.ar");
        if(!user.isPresent()) {
            user = Optional.ofNullable(us.register("paw@itba.edu.ar", "secret"));
        }
        mav.addObject("username", "PAW");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping("/explore")
    public ModelAndView explore(){
        final ModelAndView mav = new ModelAndView("explore");
        return mav;
    }

    @RequestMapping("/FAQ")
    public ModelAndView faq(){
        final ModelAndView mav = new ModelAndView("FAQ");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        final ModelAndView mav = new ModelAndView("login");
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(
            @RequestParam(value = "email", required = false, defaultValue = "itba@edu.ar") final String email,
            @RequestParam("password") final String password
    ) {
        final User user = us.register(email, password);
        return new ModelAndView("redirect:/profile" + user.getId());
        // Con esto puedo ingresar a:
        // http://localhost:8080/register?email=foo@bar.com&password=secret
        // (aunque el mail no es requerido pq le pusimos un valor default)
        // y esto me redirige a http://localhost:8080/profile/1
    }

    //Puedo agregar una validacion como expresion regular para userId
    @RequestMapping("/profile/{userId:[0-9]+}")
    public ModelAndView profile(@PathVariable("userId") final long userId) {
        final ModelAndView mav = new ModelAndView("profile");
        mav.addObject("USER", us.findById(userId).orElseThrow(UserNotFoundException::new));
        return mav;
        // Con esto puedo ingresar a:
        // http://localhost:8080/profile/1
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView userNotFound(){
        return new ModelAndView("404");
    }

}
