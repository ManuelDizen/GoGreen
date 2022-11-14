package ar.edu.itba.paw.services.exceptions;

public class OrderNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Order was not found";

    public String getErrMsg(){return MESSAGE;}
}
