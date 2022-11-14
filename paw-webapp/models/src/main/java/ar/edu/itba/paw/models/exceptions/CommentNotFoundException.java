package ar.edu.itba.paw.models.exceptions;

public class CommentNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Comment was not found";

    public String getErrMsg(){return MESSAGE;}
}
