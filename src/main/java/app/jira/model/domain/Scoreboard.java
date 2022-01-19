package app.jira.model.domain;

import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Scoreboard {
    /* Instance Fields */
    private final LinkedHashMap<User, Integer> usersScore = new LinkedHashMap<>();

    /* Constructors */
    public Scoreboard() {
    }

    /* Getters And Setters */
    public LinkedHashMap<User, Integer> getUsersScore() {
        return usersScore;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        String table = "%-5s | %-15s | %s\n";
        AtomicInteger rank = new AtomicInteger(1);

        out.append(String.format(table, "Rank", "Username", "Score"));
        if (usersScore.size() == 0) out.append(String.format(table, "---", "---", "---"));
        usersScore.forEach((user, integer) -> {
            out.append(String.format(table, rank, user.getUsername(), integer.toString()));
            rank.getAndIncrement();
        });
        return out.toString();
    }
}
