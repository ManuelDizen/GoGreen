package ar.edu.itba.paw.models.exceptions;

public class UnauthorizedRoleException extends RuntimeException{
    private final static String MESSAGE = "Not authorized to perform this action.";

    public String getErrMsg(){return MESSAGE;}
}
