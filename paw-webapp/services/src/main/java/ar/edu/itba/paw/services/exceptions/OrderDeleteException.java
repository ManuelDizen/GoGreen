package ar.edu.itba.paw.services.exceptions;

public class OrderDeleteException extends RuntimeException{
    private final static String MESSAGE = "Error deleting order.";

    public String getErrMsg(){return MESSAGE;}
}
