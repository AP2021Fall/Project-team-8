package model.domain;

import java.util.LinkedHashMap;

public class Roadmap {
    /* Instance Fields */
    private final LinkedHashMap<Task, Integer> taskPercentage = new LinkedHashMap<>();

    /* Constructors */
    public Roadmap() {
    }

    /* Getters And Setters */
    public LinkedHashMap<Task, Integer> getTaskPercentage() {
        return taskPercentage;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        if (taskPercentage.size() == 0) out.append("no task yet\n");
        taskPercentage.forEach((task, integer) -> {
            out.append(String.format("%s : %d%% done", task.getTitle(), integer)).append("\n");
        });
        return out.toString();
    }
}
