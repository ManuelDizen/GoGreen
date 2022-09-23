package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface SecurityService {
    public User getLoggedUser();
    public String getLoggedEmail();
    public String getLoggedFirstName();
    public String getLoggedSurname();

    public List<Role> getLoggedUserRoles();
    public Boolean loggedUserIsSeller();
}
