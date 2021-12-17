package model.domain;

import java.util.ArrayList;

public class Workspace {
    /* Instance Fields */
    private User user;
    private final ArrayList<Board> leaderBoards;
    private final ArrayList<Board> guestBoards;

    /* Constructor */
    public Workspace(User user, ArrayList<Board> leaderBoards, ArrayList<Board> guestBoards) {
        this.user = user;
        this.leaderBoards = leaderBoards;
        this.guestBoards = guestBoards;
    }

    /* Getters And Setters */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Board> getLeaderBoards() {
        return leaderBoards;
    }

    public ArrayList<Board> getGuestBoards() {
        return guestBoards;
    }

    /* Instance Methods */
    @Override
    public String toString() {
        Integer number = 1;
        StringBuilder workSpaceStr = new StringBuilder();
        workSpaceStr.append("own boards:\n");
        if (leaderBoards.size() == 0) workSpaceStr.append("(empty)\n");
        for (Board leaderBoard : leaderBoards) {
            workSpaceStr.append(number).append(". ").append("name: ").append(leaderBoard.getName()).append(", create at: ")
                    .append(Date.timeFormat.format(leaderBoard.getCreateAt())).append(", status: ").append(leaderBoard.getStatus()).append("\n");
            number++;
        }
        workSpaceStr.append("guest boards:\n");
        if (guestBoards.size() == 0) workSpaceStr.append("(empty)\n");
        for (Board guestBoard : guestBoards) {
            workSpaceStr.append(number).append(". ").append("name: ").append(guestBoard.getName()).append(", participate at: ")
                    .append(Date.timeFormat.format(guestBoard.getParticipateTime(this.user))).append("\n");
            number++;
        }
        workSpaceStr.setLength(workSpaceStr.length() - 1);
        return workSpaceStr.toString();
    }
}
