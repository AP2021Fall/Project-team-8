package app.jira.model.domain;

import java.sql.Timestamp;

public class Participant {
    /* Instance Fields */
    private final User user;
    private final Timestamp participated_at;
    private boolean isSuspend;


    /* Constructor */
    public Participant(User user, Timestamp participated_at, boolean isSuspend) {
        this.user = user;
        this.participated_at = participated_at;
        this.isSuspend = isSuspend;
    }

    /* Getters And Setters */
    public User getUser() {
        return user;
    }

    public Timestamp getParticipated_at() {
        return participated_at;
    }

    public boolean isSuspend() {
        return isSuspend;
    }

    public void setSuspend(boolean suspend) {
        isSuspend = suspend;
    }
}
