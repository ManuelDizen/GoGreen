package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class CommentForm {

        @Size(max = 512)
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

}
