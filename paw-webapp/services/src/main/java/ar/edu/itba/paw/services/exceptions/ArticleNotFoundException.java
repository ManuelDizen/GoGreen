package ar.edu.itba.paw.services.exceptions;

public class ArticleNotFoundException extends RuntimeException{
    private final static String MESSAGE = "Article was not found";

    public String getErrMsg(){return MESSAGE;}
}
