package ar.edu.itba.paw.models.exceptions;

public class TokenNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Token was not found";

    public String getErrMsg(){return MESSAGE;}
}
