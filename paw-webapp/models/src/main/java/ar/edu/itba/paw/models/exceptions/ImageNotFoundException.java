package ar.edu.itba.paw.models.exceptions;

public class ImageNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Image was not found";

    public String getErrMsg(){return MESSAGE;}
}
