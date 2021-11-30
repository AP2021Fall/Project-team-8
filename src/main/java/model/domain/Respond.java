package model.domain;

public class Respond {
    /* Instance Fields */
    boolean success;
    String message;
    Object content;

    public Respond(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Respond(boolean success, String message, Object content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }

    /* Getters And Setters */
    public boolean isSuccess() {
        return success;
    }

    public Object getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }
}

