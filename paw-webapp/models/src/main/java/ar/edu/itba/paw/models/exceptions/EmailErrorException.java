package ar.edu.itba.paw.models.exceptions;

public class EmailErrorException extends RuntimeException{
    private final static String MESSAGE = "Email could not be delivered.";

    public String getErrMsg(){return MESSAGE;}
}
