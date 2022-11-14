package ar.edu.itba.paw.models.exceptions;

public class UserRegisterException extends RuntimeException{
    private final static String MESSAGE = "User could not be registered";

    public String getErrMsg(){return MESSAGE;}
}
