package app.jira.controller;

import app.jira.model.dao.ChatDao;
import app.jira.model.dao.NotificationDao;
import app.jira.model.domain.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChatController extends Controller {
    /* Instance Methods */
    public Respond getChats(Board board) throws Exception {
        // Success
        return new Respond(true, ChatDao.getChatsByBoard(board));
    }

    public Respond sendMessage(User loggedInUser, Board board, String message) throws Exception {
        // Check For Error
        if (loggedInUser.getId() != board.getLeader().getId())
            board.assertContributorExists(loggedInUser.getUsername());

        // Success
        ChatDao.save(new Chat(board, loggedInUser, Timestamp.from(Instant.now()), message));
        return new Respond(true, "chat send successfully");
    }

    public Respond sendNotification(User loggedInUser, Board board, String username, String notifyText) throws Exception {
        ArrayList<User> sendToUsers = board.getContributors().stream().map(Participant::getUser).collect(Collectors.toCollection(ArrayList::new));
        User sendToUser;

        // Check For Error
        authentication(loggedInUser, board);

        // Send To Username (Optional)
        if (username != null) {
            sendToUser = board.assertContributorExists(username).getUser();
            sendToUsers.clear();
            sendToUsers.add(sendToUser);
        }

        // Success
        NotificationDao.sendNotificationToUsers(loggedInUser, notifyText, Timestamp.from(Instant.now()), sendToUsers);
        return new Respond(true, "notification send successfully");
    }
}
