package ar.edu.itba.paw.models.exceptions;

public class ProductDeleteException extends RuntimeException{
    private final static String MESSAGE = "Error deleting product.";

    public String getErrMsg(){return MESSAGE;}
}
