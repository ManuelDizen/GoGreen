package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.GoGreenUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.validation.constraints.Pattern;
import java.util.concurrent.TimeUnit;

@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
@EnableWebSecurity
public class WebAuthConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private Environment env;
    @Autowired
    private GoGreenUserDetailsService userDetailsService;


    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_SELLER \n ROLE_ADMIN > ROLE_BUYER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final WebSecurity web) throws	Exception	{
        web.ignoring().antMatchers("/css/**","/js/**","/img/**",	"/favicon.ico",	"/403");
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

        http.sessionManagement().
                invalidSessionUrl("/").and()
                .authorizeRequests()
                    .antMatchers("/", "/explore", "/productpage/**").permitAll()
                    .antMatchers("/login", "/register", "/registerbuyer", "/registerseller").anonymous()
                    .antMatchers("/userProfile").hasRole("USER")
                    .antMatchers("/sellerProfile").hasRole("SELLER")
                    .antMatchers("/createProduct").hasRole("SELLER")
                .and().formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/explore", false)
                    .failureUrl("/login?failure=true")
                    .loginPage("/login")
                .and().rememberMe()
                    .rememberMeParameter("remember-me")
                    .userDetailsService(userDetailsService)
                    .key(env.getProperty("rememberme.key"))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
        /*
        TODO: Esto va a haber que cambiarlo dinamicamente, de momento va a funcionar xq tiene las paginas
            creadas, pero eso no se va a mantener.
        */

    }
}
