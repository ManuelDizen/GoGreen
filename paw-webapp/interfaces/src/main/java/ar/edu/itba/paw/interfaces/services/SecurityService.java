package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

public interface SecurityService {
    public User getLoggedUser();
    public String getLoggedEmail();
    public String getLoggedFirstName();
    public String getLoggedSurname();
}
