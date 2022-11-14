package ar.edu.itba.paw.services.exceptions;

public class ProductCreationException extends RuntimeException{
    private final static String MESSAGE = "Error creating product";

    public String getErrMsg(){return MESSAGE;}
}