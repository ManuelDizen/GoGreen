package ar.edu.itba.paw.webapp.config;

import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class RefererRedirectionAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler{

    public RefererRedirectionAuthenticationSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }
}