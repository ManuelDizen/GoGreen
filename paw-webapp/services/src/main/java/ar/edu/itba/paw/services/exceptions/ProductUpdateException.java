package ar.edu.itba.paw.services.exceptions;

public class ProductUpdateException extends RuntimeException{
    private final static String MESSAGE = "Error updating product.";

    public String getErrMsg(){return MESSAGE;}
}
