package model.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board {
    /* Instance Fields */
    private int id;
    private User leader;
    private String name;
    private final Timestamp createAt;
    private final ArrayList<Participant> contributors;
    private String status;

    /* Constructor */
    public Board(int id, User leader, String name, Timestamp createAt, ArrayList<Participant> contributors, String status) {
        this.id = id;
        this.leader = leader;
        this.name = name;
        this.createAt = createAt;
        this.contributors = contributors;
        this.status = status;
    }

    public Board(User leader, String name, Timestamp createAt, ArrayList<Participant> contributors, String status) {
        this.leader = leader;
        this.name = name;
        this.createAt = createAt;
        this.contributors = contributors;
        this.status = status;
    }

    /* Static Methods */
    public static String BoardListString(ArrayList<Board> boards) {
        String table = "%-5s | %-15s | %-15s | %-16s | %-10s | %s\n";
        StringBuilder out = new StringBuilder();

        out.append(String.format(table, "ID", "Name", "Leader", "Created At", "Status", "Contributors"));
        out.append("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n");
        if (boards.size() == 0) out.append(String.format(table, "---", "---", "---", "---", "---", "---"));
        for (Board board : boards) {
            out.append(String.format(table, board.getId(), board.getName(), board.getLeader().getUsername(),
                    Date.timeFormat.format(board.getCreateAt()), board.getStatus(),
                    board.getContributors().size() == 0 ? "---" : board.getContributors().stream().map(participant -> participant.getUser().getUsername()).collect(Collectors.joining(", "))));
        }

        return out.toString();
    }

    /* Getters And Setters */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public ArrayList<Participant> getContributors() {
        return contributors;
    }

    /* Instance Methods */
    public Timestamp getParticipateTime(User user) {
        for (Participant contributor : contributors) {
            if (contributor.getUser().getId() == user.getId()) return contributor.getParticipated_at();
        }
        return null;
    }

    public Participant getContributor(String username) {
        for (Participant contributor : contributors) {
            if (contributor.getUser().getUsername().equals(username)) return contributor;
        }
        return null;
    }

    public void assertIsNotSuspend(String username) throws Exception {
        Participant participant = assertContributorExists(username);
        if (participant.isSuspend())
            throw new Exception("you are suspended!!!");
    }

    public Participant assertContributorExists(String username) throws Exception {
        Participant contributor = getContributor(username);
        if (contributor == null)
            throw new Exception(String.format("can not find user (%s) in this board", username));
        return contributor;
    }

    public void assertContributorNotExists(String username) throws Exception {
        Participant contributor = getContributor(username);
        if (contributor != null)
            throw new Exception(String.format("user (%s) already exists in this board", username));
    }

    @Override
    public String toString() {
        String table = "%-5s | %-20s | %-30s | %-20s | %-10s %s\n";
        StringBuilder out = new StringBuilder();
        out.append("leader of board: ").append(leader.getName()).append(", name of board: ")
                .append(name).append(", board created at: ").append(Date.timeFormat.format(createAt)).append("\ncontributors:\n");
        out.append(String.format(table, "ID", "Username", "Name", "Join At", "Is Suspend", ""));
        if (contributors.size() == 0) out.append(String.format(table, "---", "---", "---", "---", "---", ""));
        for (Participant contributor : contributors) {
            out.append(String.format(table, contributor.getUser().getId(), contributor.getUser().getUsername(), contributor.getUser().getName(), Date.timeFormat.format(contributor.getParticipated_at()), contributor.isSuspend(), contributor.getUser().getId() == leader.getId() ? "(leader)" : ""));
        }
        out.setLength(out.length() - 1);
        return out.toString();
    }
}
