package ar.edu.itba.paw.models.exceptions;

public class ProductUpdateException extends RuntimeException{
    private final static String MESSAGE = "Error updating product.";

    public String getErrMsg(){return MESSAGE;}
}
