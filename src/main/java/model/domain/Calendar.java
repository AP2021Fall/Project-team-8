package model.domain;

import java.util.LinkedHashMap;

public class Calendar {
    /* Instance Fields */
    private final LinkedHashMap<Task, String> taskBoard = new LinkedHashMap<>();

    /* Constructors */
    public Calendar() {
    }

    /* Getters And Setters */
    public LinkedHashMap<Task, String> getTaskBoard() {
        return taskBoard;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        if (taskBoard.size() == 0) out.append("no deadlines");
        taskBoard.forEach((task, boardName) -> {

            if (task.getDeadlineDays() < 4) out.append("***");
            else if (task.getDeadlineDays() <= 10) out.append("**");
            else out.append("*");
            out.append(String.format("\t%s__remaining days: %d (name of board: %s)",
                    Date.timeFormat.format(task.getDeadline()), task.getDeadlineDays(), boardName));
        });
        return out.toString();
    }
}
