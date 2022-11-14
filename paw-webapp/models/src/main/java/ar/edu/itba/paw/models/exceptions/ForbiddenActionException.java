package ar.edu.itba.paw.models.exceptions;

public class ForbiddenActionException extends RuntimeException{
    private final static String MESSAGE = "User is not authorized to perform action";

    public String getErrMsg(){return MESSAGE;}
}
