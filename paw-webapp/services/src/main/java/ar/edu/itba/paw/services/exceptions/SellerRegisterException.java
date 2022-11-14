package ar.edu.itba.paw.services.exceptions;

public class SellerRegisterException extends RuntimeException{
    private final static String MESSAGE = "Seller could not be registered";

    public String getErrMsg(){return MESSAGE;}
}
