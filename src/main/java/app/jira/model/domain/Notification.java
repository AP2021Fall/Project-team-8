package app.jira.model.domain;

import java.sql.Timestamp;

public class Notification {
    private User from;
    private User to;
    private Timestamp sendAt;
    private String text;

    public Notification(User from, User to, Timestamp sendAt, String text) {
        this.from = from;
        this.to = to;
        this.sendAt = sendAt;
        this.text = text;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Timestamp getSendAt() {
        return sendAt;
    }

    public void setSendAt(Timestamp sendAt) {
        this.sendAt = sendAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
