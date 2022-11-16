package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class CommentForm {

    @Size(min = 1, max = 300)
    private String message;

    private long parentId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
