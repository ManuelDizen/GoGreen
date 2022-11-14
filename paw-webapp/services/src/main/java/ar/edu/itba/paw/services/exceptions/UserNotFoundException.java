package ar.edu.itba.paw.services.exceptions;

public class UserNotFoundException extends RuntimeException{

    private final static String MESSAGE = "User was not found";

    public String getErrMsg(){return MESSAGE;}

}
