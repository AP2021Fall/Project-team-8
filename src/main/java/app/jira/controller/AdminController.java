package app.jira.controller;

import app.jira.model.dao.BoardDao;
import app.jira.model.dao.NotificationDao;
import app.jira.model.dao.ParticipantDao;
import app.jira.model.dao.UserDao;
import app.jira.model.domain.Board;
import app.jira.model.domain.Participant;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AdminController extends Controller {
    /* Static Methods */
    private static void assertIsAdmin(User user) throws Exception {
        if (!user.isAdmin()) throw new Exception("Access denied");
    }

    private static ArrayList<Board> assertPendingExists(String ids) throws Exception {
        Board findBoard;
        ArrayList<Board> res = new ArrayList<>();
        ArrayList<Board> pendingBoards = BoardDao.getAllBoards(true);
        for (String id : ids.split(",\\s*")) {
            int idNum = castToInt(id, "board id");
            findBoard = pendingBoards.stream().filter(board -> board.getId() == idNum).findFirst().orElse(null);
            if (findBoard == null) throw new Exception("Some teams are not in pending status! Try again");
            res.add(findBoard);
        }
        return res;
    }

    /* Instance Methods */
    public Respond getProfile(User loggedInUser, String selectedUsername) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        User selectedUser;

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedUser = UserDao.getByUsername(selectedUsername);
        if (selectedUser == null)
            throw new Exception(String.format("There is not any user with username: %s!", selectedUsername));

        // Success
        return new Respond(true, selectedUser);
    }

    public Respond banUser(User loggedInUser, String selectedUsername) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        User selectedUser;

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedUser = UserDao.getByUsername(selectedUsername);
        if (selectedUser == null)
            throw new Exception(String.format("There is not any user with username: %s!", selectedUsername));

        // Success
        selectedUser.setBan(true);
        UserDao.update(selectedUser);
        return new Respond(true, selectedUsername + " banned from system successfully!");
    }

    public Respond unBanUser(User loggedInUser, String selectedUsername) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        User selectedUser;

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedUser = UserDao.getByUsername(selectedUsername);
        if (selectedUser == null)
            throw new Exception(String.format("There is not any user with username: %s!", selectedUsername));

        // Success
        selectedUser.setBan(false);
        UserDao.update(selectedUser);
        return new Respond(true, selectedUsername + " unbanned from system successfully!");
    }

    public Respond removeUser(User loggedInUser, String selectedUsername) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        User selectedUser;

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedUser = UserDao.getByUsername(selectedUsername);
        if (selectedUser == null)
            throw new Exception(String.format("There is not any user with username: %s!", selectedUsername));

        // Success
        UserDao.delete(selectedUser);
        return new Respond(true, selectedUsername + " deleted from system successfully!");
    }

    public Respond getAllBoards(User loggedInUser) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        // Check For Error
        assertIsAdmin(loggedInUser);

        // Success
        return new Respond(true, BoardDao.getAllBoards(false));
    }

    public Respond getPendingBoards(User loggedInUser) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        // Check For Error
        assertIsAdmin(loggedInUser);

        // Success
        return new Respond(true, BoardDao.getAllBoards(true));
    }

    public Respond acceptBoards(User loggedInUser, String boardIds) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        ArrayList<Board> selectedPendingBoards;
        String notification;
        Timestamp now;
        ArrayList<User> users = new ArrayList<>();

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedPendingBoards = assertPendingExists(boardIds);

        // Success
        BoardDao.updateBoardsStatus(selectedPendingBoards, "accepted");
        now = Timestamp.from(Instant.now());

        for (Board selectedPendingBoard : selectedPendingBoards) {
            users.add(selectedPendingBoard.getLeader());
            notification = String.format("The board %s was accepted!", selectedPendingBoard.getName());
            NotificationDao.sendNotificationToUsers(loggedInUser, notification, now, users);
            users.clear();
        }

        return new Respond(true, String.format("(%s) set accepted successfully", selectedPendingBoards.stream().map(Board::getName).collect(Collectors.joining(", "))));
    }

    public Respond rejectBoards(User loggedInUser, String boardIds) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        ArrayList<Board> selectedPendingBoards;
        Timestamp now;
        ArrayList<User> users = new ArrayList<>();
        String notification;

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedPendingBoards = assertPendingExists(boardIds);

        // Success
        BoardDao.updateBoardsStatus(selectedPendingBoards, "rejected");

        now = Timestamp.from(Instant.now());

        for (Board selectedPendingBoard : selectedPendingBoards) {
            users.add(selectedPendingBoard.getLeader());
            notification = String.format("The board %s was rejected!", selectedPendingBoard.getName());
            NotificationDao.sendNotificationToUsers(loggedInUser, notification, now, users);
            users.clear();
        }

        return new Respond(true, String.format("(%s) set rejected successfully", selectedPendingBoards.stream().map(Board::getName).collect(Collectors.joining(", "))));
    }

    public Respond changeBoardLeader(User loggedInUser, String boardId, String selectedUsername) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Board selectedBoard;
        User selectedUser;
        Participant contributor, newParticipant;
        ArrayList<Participant> contributorsCopy;
        int boardIdNum;
        String notification;

        // Check For Error
        assertIsAdmin(loggedInUser);
        boardIdNum = castToInt(boardId, "board id");
        selectedBoard = BoardDao.getBoardById(boardIdNum);
        if (selectedBoard == null) throw new Exception(String.format("there is no board with id %s", boardId));

        selectedUser = UserDao.getByUsername(selectedUsername);
        if (selectedUser == null)
            throw new Exception(String.format("There is not any user with username: %s!", selectedUsername));

        if (selectedBoard.getLeader().getId() == selectedUser.getId())
            throw new Exception(String.format("(%s) is already the leader of board (%s)", selectedUsername, selectedBoard.getName()));


        // Success
        contributor = selectedBoard.getContributor(selectedUsername);
        if (contributor == null) {
            contributorsCopy = new ArrayList<>(selectedBoard.getContributors());
            newParticipant = new Participant(selectedUser, Timestamp.from(Instant.now()), false);
            selectedBoard.getContributors().clear();
            selectedBoard.getContributors().add(newParticipant);
            selectedBoard.getContributors().addAll(contributorsCopy);
            ParticipantDao.save(newParticipant, selectedBoard);
        }

        // Send Notification
        ArrayList<User> sendTo = selectedBoard.getContributors().stream().map(Participant::getUser).collect(Collectors.toCollection(ArrayList::new));
        notification = String.format("now user (%s) is the leader of board (%s)", selectedUser.getName(), selectedBoard.getName());
        NotificationDao.sendNotificationToUsers(loggedInUser, notification, Timestamp.from(Instant.now()), sendTo);

        selectedBoard.setLeader(selectedUser);
        BoardDao.update(selectedBoard);
        return new Respond(true, String.format("now user (%s) is the leader of board (%s)", selectedUser.getName(), selectedBoard.getName()));
    }

    public Respond sendNotificationToAll(User loggedInUser, String notifyText) throws Exception {
        // Check For Error
        assertIsAdmin(loggedInUser);

        // Success
        NotificationDao.sendNotificationToUsers(loggedInUser, notifyText, Timestamp.from(Instant.now()), UserDao.getAllUsers());
        return new Respond(true, "notification send successfully");
    }

    public Respond sendNotificationToUser(User loggedInUser, String notifyText, String username) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        User selectedUser;
        ArrayList<User> users = new ArrayList<>();

        // Check For Error
        assertIsAdmin(loggedInUser);
        selectedUser = UserDao.getByUsername(username);
        if (selectedUser == null)
            throw new Exception(String.format("There is not any user with username: %s!", username));

        // Success
        users.add(selectedUser);
        NotificationDao.sendNotificationToUsers(loggedInUser, notifyText, Timestamp.from(Instant.now()), users);
        return new Respond(true, "notification send successfully");
    }

    public Respond sendNotificationToBoard(User loggedInUser, String notifyText, String boardId) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        ArrayList<User> users;
        Board selectedBoard;
        int boardIdNum;

        // Check For Error
        assertIsAdmin(loggedInUser);
        boardIdNum = castToInt(boardId, "board id");
        selectedBoard = BoardDao.getBoardById(boardIdNum);
        if (selectedBoard == null) throw new Exception(String.format("there is no board with id %s", boardId));

        // Success
        users = selectedBoard.getContributors().stream().map(Participant::getUser).collect(Collectors.toCollection(ArrayList::new));
        users.add(selectedBoard.getLeader());
        NotificationDao.sendNotificationToUsers(loggedInUser, notifyText, Timestamp.from(Instant.now()), users);
        return new Respond(true, "notification send successfully");
    }

    public Respond showScoreboard(User loggedInUser, String boardId) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Board selectedBoard;
        int boardIdNum;

        // Check For Error
        assertIsAdmin(loggedInUser);
        boardIdNum = castToInt(boardId, "board id");
        selectedBoard = BoardDao.getBoardById(boardIdNum);
        if (selectedBoard == null) throw new Exception(String.format("there is no board with id %s", boardId));

        // Success
        return new Respond(true, BoardDao.getScoreboardByBoard(selectedBoard));
    }
}