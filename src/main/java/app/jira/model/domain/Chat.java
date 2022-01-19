package app.jira.model.domain;

import java.sql.Timestamp;

public class Chat {
    Board board;
    User sendBy;
    Timestamp sendAt;
    String message;

    public Chat(Board board, User sendBy, Timestamp sendAt, String message) {
        this.board = board;
        this.sendBy = sendBy;
        this.sendAt = sendAt;
        this.message = message;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getSendBy() {
        return sendBy;
    }

    public void setSendBy(User sendBy) {
        this.sendBy = sendBy;
    }

    public Timestamp getSendAt() {
        return sendAt;
    }

    public void setSendAt(Timestamp sendAt) {
        this.sendAt = sendAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("%s :\"%s\"", sendBy.getName(), message);
    }
}
