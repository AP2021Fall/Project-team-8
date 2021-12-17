package model.domain;

import java.util.ArrayList;

public class List {
    /* Static Fields */
    public static String table = "%-5s | %-9s | %-15s | %-17s | %-17s | %-15s | %s\n";

    /* Instance Fields */
    private int id;
    private final ArrayList<Task> tasks = new ArrayList<>();
    private String name;
    private int order;

    /* Constructors */
    public List(int id, String name, int order) {
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public List(String name, int order) {
        this.name = name;
        this.order = order;
    }

    /* Getters And Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("name: ").append(name).append("\n");
        out.append(String.format(table, "ID", "Priority", "Title", "Created At", "Deadline", "Status", "Assigned To"));
        if (tasks.size() == 0) out.append(String.format(table, "---", "---", "---", "---", "---", "---", "---"));
        for (Task task : tasks) {
            out.append(task);
        }
        return out.toString();
    }
}
