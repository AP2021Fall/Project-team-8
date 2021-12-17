package model.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class Task {
    /* Instance Fields */
    private int id;
    private String title;
    private Priority priority;
    private String status;
    private final Timestamp createAt;
    private Timestamp deadline;
    private Board board;
    private List list;
    private final LinkedHashMap<User, Timestamp> assignedUsers = new LinkedHashMap<>();
    private final ArrayList<Comment> comments = new ArrayList<>();
    private String description;
    private int deadlineDays;

    /* Constructors */
    public Task(int id, String title, Priority priority, String status, Timestamp createAt, Timestamp deadline, String description) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.createAt = createAt;
        this.deadline = deadline;
        this.description = description;
    }

    public Task(String title, Priority priority, String status, Timestamp createAt, Timestamp deadline, Board board, List list, String description) {
        this.title = title;
        this.priority = priority;
        this.status = status;
        this.createAt = createAt;
        this.deadline = deadline;
        this.board = board;
        this.list = list;
        this.description = description;
    }

    /* Getters And Setters */
    public int getDeadlineDays() {
        return deadlineDays;
    }

    public void setDeadlineDays(int deadlineDays) {
        this.deadlineDays = deadlineDays;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public LinkedHashMap<User, Timestamp> getAssignedUsers() {
        return assignedUsers;
    }

    /* Instance Methods */
    public void assertIsAssignedTo(User user) throws Exception {
        User assignedTo = assignedUsers.keySet().stream().filter(user1 -> user1.getId() == user.getId()).findFirst().orElse(null);
        if (assignedTo == null)
            throw new Exception(String.format("this task is not assigned to (%s)", user.getUsername()));
    }

    public void updateStatus(ArrayList<List> lists) throws Exception {
        if (getStatus().contains("failed")) throw new Exception("deadline already passed");
        if (list.getOrder() == lists.size()) this.setStatus("done");
        else if (this.getStatus().contains("done")) this.setStatus("in-progress");
    }

    @Override
    public String toString() {
        return String.format(
                List.table, id, priority.getName(), title,
                Date.timeFormat.format(createAt), Date.timeFormat.format(deadline), status,
                assignedUsers.size() != 0 ? assignedUsers.keySet().stream().map(User::getUsername)
                        .collect(Collectors.joining(", ")) : "(not assigned yet)");
    }

    public String toStringDetails() {
        return String.format("ID: %d\n" +
                        "Title: %s\n" +
                        "Description: %s\n" +
                        "Priority: %s\n" +
                        "Date and time of creation: %s\n" +
                        "Date and time of deadline: %s\n" +
                        "Assigned users: %s\n" +
                        "Comments:\n%s", id, title, description, priority, createAt, deadline,
                assignedUsers.size() != 0 ? assignedUsers.keySet().stream().map(User::getUsername).collect(Collectors.joining(", ")) : "(not assigned yet)",
                comments.size() != 0 ? comments.stream().map(Comment::toString).collect(Collectors.joining("\n")) : "(no comment yet)");
    }
}
