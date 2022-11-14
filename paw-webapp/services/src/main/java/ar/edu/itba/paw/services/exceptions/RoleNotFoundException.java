package ar.edu.itba.paw.services.exceptions;

public class RoleNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Role was not found";

    public String getErrMsg(){return MESSAGE;}
}
