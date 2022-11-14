package ar.edu.itba.paw.models.exceptions;

public class ProductCreationException extends RuntimeException{
    private final static String MESSAGE = "Error creating product";

    public String getErrMsg(){return MESSAGE;}
}