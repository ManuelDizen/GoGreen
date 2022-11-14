package ar.edu.itba.paw.services.exceptions;

public class InsufficientStockException extends RuntimeException{
    private final static String MESSAGE = "Not enough stock available for purchase";

    public String getErrMsg(){return MESSAGE;}
}