package ar.edu.itba.paw.models.exceptions;

public class ArticleCreationException extends RuntimeException{
    private final static String MESSAGE = "Error creating article";

    public String getErrMsg(){return MESSAGE;}
}