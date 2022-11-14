package ar.edu.itba.paw.services.exceptions;

public class CommentNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Comment was not found";

    public String getErrMsg(){return MESSAGE;}
}
