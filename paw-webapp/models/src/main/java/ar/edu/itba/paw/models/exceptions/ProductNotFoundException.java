package ar.edu.itba.paw.models.exceptions;

public class ProductNotFoundException extends RuntimeException{

        private final static String MESSAGE = "Product was not found";

        public String getErrMsg(){return MESSAGE;
    }
}
