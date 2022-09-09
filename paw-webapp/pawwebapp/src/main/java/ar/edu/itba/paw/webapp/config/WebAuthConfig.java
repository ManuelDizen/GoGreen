package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.GoGreenUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableWebSecurity
public class WebAuthConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private Environment env;
    @Autowired
    private GoGreenUserDetailsService userDetailsService;

    @Override
    public void configure(final WebSecurity web) throws	Exception	{
        web.ignoring().antMatchers("/css/**","/js/**","/img/**",	"/favicon.ico",	"/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /* TODO: Este método que queda abajo es el que hay que revisar para nuestros
    roles y authorities (en el script de SQL está armado pero hay que hablarlo)
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement().invalidSessionUrl("/login").and().authorizeRequests().
                antMatchers("/login").anonymous().
                antMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/**").authenticated().and().
                formLogin().usernameParameter("j_username").passwordParameter("j_password").
                defaultSuccessUrl("/", false).loginPage("/login").and().
                rememberMe().rememberMeParameter("j_rememberme").userDetailsService(userDetailsService).
                key("mysupersecretketthatnobodyknowsabout"). //Esto hay que cambiarlo por una clave segura en env.application.properties
                tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))												.
                and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").
                and().exceptionHandling().accessDeniedPage("/403").
                and().csrf().disable();
        //TODO: ESTO HAY QUE REHACERLE TODOS LOS ANTMATCHERS!!! CHARLAR
    }
}
