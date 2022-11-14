package ar.edu.itba.paw.services.exceptions;

public class UserRegisterException extends RuntimeException{
    private final static String MESSAGE = "User could not be registered";

    public String getErrMsg(){return MESSAGE;}
}
