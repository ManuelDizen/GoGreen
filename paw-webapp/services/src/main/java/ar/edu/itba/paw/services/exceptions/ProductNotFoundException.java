package ar.edu.itba.paw.services.exceptions;

public class ProductNotFoundException extends RuntimeException{

        private final static String MESSAGE = "Product was not found";

        public String getErrMsg(){return MESSAGE;}
}
