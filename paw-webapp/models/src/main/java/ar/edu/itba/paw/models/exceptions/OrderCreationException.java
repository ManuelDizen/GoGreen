package ar.edu.itba.paw.models.exceptions;

public class OrderCreationException extends RuntimeException{
    private final static String MESSAGE = "Order could not be created";

    public String getErrMsg(){return MESSAGE;}
}
