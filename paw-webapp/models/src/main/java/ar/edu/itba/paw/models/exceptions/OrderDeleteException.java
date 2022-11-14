package ar.edu.itba.paw.models.exceptions;

public class OrderDeleteException extends RuntimeException{
    private final static String MESSAGE = "Error deleting order.";

    public String getErrMsg(){return MESSAGE;}
}
