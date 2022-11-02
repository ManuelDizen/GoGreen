package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface SecurityService {
    User getLoggedUser();
    String getLoggedEmail();
    String getLoggedFirstName();
    String getLoggedSurname();

    List<Role> getLoggedUserRoles();
}
