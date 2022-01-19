package app.jira.model.domain;

import java.sql.Timestamp;

public class Comment {
    /* Instance Fields */
    private final User from;
    private final String text;
    private final Task task;
    private final Timestamp sendAt;

    /* Constructor */
    public Comment(User from, String text, Task task, Timestamp sendAt) {
        this.from = from;
        this.text = text;
        this.task = task;
        this.sendAt = sendAt;
    }

    /* Getters And Setters */
    public User getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public Task getTask() {
        return task;
    }

    public Timestamp getSendAt() {
        return sendAt;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        return String.format("<%s>: %s", from.getUsername(), text);
    }
}
