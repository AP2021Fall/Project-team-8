package controller;

import model.dao.*;
import model.domain.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class BoardController extends Controller {
    /* Static Methods*/
    private static List getListByName(ArrayList<List> lists, String listName) {
        return lists.stream().filter(list -> list.getName().equals(listName)).findFirst().orElse(null);
    }

    public static List assertListExists(ArrayList<List> lists, String listName) throws Exception {
        List selectedList = getListByName(lists, listName);
        if (selectedList == null)
            throw new Exception(String.format("there is no list with name (%s) in this board", listName));
        return selectedList;
    }

    public static void assertListNotExists(ArrayList<List> lists, String listName) throws Exception {
        List selectedList = getListByName(lists, listName);
        if (selectedList != null)
            throw new Exception(String.format("list with name (%s) already exists", listName));
    }

    public static Priority assertPriorityExists(String nameOrNum) throws Exception {
        if (Controller.isInt(nameOrNum)) {
            return Priority.assertPriorityExistsNth(Integer.parseInt(nameOrNum));
        } else {
            return Priority.assertPriorityExistsName(nameOrNum);
        }
    }

    private static Task assertTaskExists(Board board, String taskId) throws Exception {
        int taskIdNum;
        Task task;
        taskIdNum = castToInt(taskId, "task id");
        task = TaskDao.getTaskByBoard(board, taskIdNum);
        if (task == null) throw new Exception(String.format("there is no task with id (%d) in this board", taskIdNum));
        return task;
    }

    public static void assertAcceptedBoard(Board board) throws Exception {
        if (!board.getStatus().equals("accepted")) throw new Exception("This board is not accepted");
    }

    /* Instance Methods */
    // Contributor Section
    public Respond inviteUser(User user, Board board, String username) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        User invitedUser;
        ArrayList<Participant> contributorsCopy = new ArrayList<>(board.getContributors());
        Participant participant;

        // Check For Error
        authentication(user, board);
        assertAcceptedBoard(board);
        board.assertContributorNotExists(username);
        if (board.getLeader().getUsername().equals(username))
            throw new Exception(String.format("(%s) is the leader of this board", username));
        invitedUser = assertUserExists(username);

        // Success
        participant = new Participant(invitedUser, Timestamp.from(Instant.now()), false);
        board.getContributors().clear();
        board.getContributors().add(participant);
        board.getContributors().addAll(contributorsCopy);
        ParticipantDao.save(participant, board);
        return new Respond(true, String.format("user (%s) invited to board successfully", username));
    }

    public Respond removeUser(User user, Board board, String username) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Participant removeUser;

        // Check For Error
        authentication(user, board);
        if (user.getUsername().equals(username))
            throw new Exception("you are the leader of this board");
        removeUser = board.assertContributorExists(username);

        // Success
        board.getContributors().remove(removeUser);
        ParticipantDao.delete(board, removeUser.getUser());
        return new Respond(true, String.format("user (%s) removed from board successfully", username));
    }

    public Respond changeLeader(User user, Board board, String username) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Participant newLeader;
        String notification;

        // Check For Error
        authentication(user, board);
        newLeader = board.assertContributorExists(username);

        // Success
        if (newLeader.isSuspend()) {
            newLeader.setSuspend(false);
            ParticipantDao.update(newLeader, board);
        }

        board.setLeader(newLeader.getUser());
        BoardDao.update(board);

        // Send Notification
        ArrayList<User> sendTo = board.getContributors().stream().map(Participant::getUser).collect(Collectors.toCollection(ArrayList::new));
        notification = String.format("now user (%s) is the leader of board (%s)", newLeader.getUser().getName(), board.getName());
        NotificationDao.sendNotificationToUsers(user, notification, Timestamp.from(Instant.now()), sendTo);

        return new Respond(true, String.format("now user (%s) is leader of board (%s)", newLeader.getUser().getName(), board.getName()));
    }

    public Respond suspendUser(User user, Board board, String username, String remove) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Participant suspendedUser;
        String not = remove == null ? "" : "not";

        // Check For Error
        authentication(user, board);
        suspendedUser = board.assertContributorExists(username);
        if (remove == null) {
            if (user.getUsername().equals(username)) throw new Exception("you are the leader of this board");
            if (suspendedUser.isSuspend()) throw new Exception("user (%s) is already suspended");
            suspendedUser.setSuspend(true);
        } else {
            if (!suspendedUser.isSuspend()) throw new Exception("user (%s) is not suspended");
            suspendedUser.setSuspend(false);
        }

        // Success
        ParticipantDao.update(suspendedUser, board);
        return new Respond(true, String.format("now user (%s) is " + not + " suspended", suspendedUser.getUser().getUsername()));
    }

    // List Section
    public Respond addList(User user, Board board, String listName, String order) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        int orderNum;
        ArrayList<List> lists;

        // Check For Error
        authentication(user, board);
        assertAcceptedBoard(board);
        lists = ListDao.getBoardLists(board);
        assertListNotExists(lists, listName);
        orderNum = lists.size() + 1;

        // Optional Order
        if (order != null) {
            orderNum = castToInt(order, "list's order");
            assertInRange(orderNum, 1, lists.size() + 1, "list's order");
        }

        // Success
        ListDao.save(board, new List(listName, orderNum));
        return new Respond(true, String.format("list (%s) added to board successfully", listName));
    }

    public Respond changeList(User user, Board board, String listName, String newListName, String newListOrder) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        int orderNum, lastOrder;
        ArrayList<List> lists;
        List selectedList;

        // Check For Error
        authentication(user, board);
        lists = ListDao.getBoardLists(board);
        selectedList = assertListExists(lists, listName);

        // Change List's Name (Optional)
        if (newListName != null) {
            assertListNotExists(lists, newListName);
            selectedList.setName(newListName);
        }

        // Change List's Order (Optional)
        lastOrder = selectedList.getOrder();
        if (newListOrder != null) {
            orderNum = castToInt(newListOrder, "list's order");
            assertInRange(orderNum, 1, lists.size(), "list's order");
            selectedList.setOrder(orderNum);
        }

        // Success
        ListDao.update(selectedList, board, lastOrder);
        return new Respond(true, "list edited successfully");
    }

    public Respond removeList(User user, Board board, String listName) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        List removeList;

        // Check For Error
        authentication(user, board);
        removeList = assertListExists(ListDao.getBoardLists(board), listName);

        // Success
        ListDao.delete(removeList, board);
        return new Respond(true, String.format("list (%s) removed from board successfully", listName));
    }

    public Respond getLists(Board board) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        return new Respond(true, ListDao.getBoardLists(board));
    }

    // Task Section
    public Respond addTask(User loggedInUser, Board board, String title, String deadline, String listName, String priority, String description) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        ArrayList<List> lists;
        List selectedList;
        Priority priorityObj = Priority.LOWEST;
        Timestamp deadTime;

        // Check For Error
        authentication(loggedInUser, board);
        assertAcceptedBoard(board);
        lists = ListDao.getBoardLists(board);
        if (lists.size() == 0) throw new Exception("please create a list first");

        // List (Optional)
        selectedList = lists.get(0);
        if (listName != null) {
            selectedList = assertListExists(lists, listName);
        }

        // Priority (Optional)
        if (priority != null) {
            priorityObj = assertPriorityExists(priority);
        }

        // Deadline (Required)
        deadTime = assertValidTimestamp(deadline);

        // Success
        TaskDao.save(new Task(title, priorityObj, "in-progress", Timestamp.from(Instant.now()), deadTime, board, selectedList, description));
        return new Respond(true, String.format("task (%s) added to list (%s) successfully", title, selectedList.getName()));
    }

    public Respond removeTask(User loggedInUser, Board board, String taskId) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        // Check For Error
        authentication(loggedInUser, board);
        TaskDao.delete(assertTaskExists(board, taskId));

        // Success
        return new Respond(true, String.format("task with id (%s) removed successfully", taskId));
    }

    public Respond changeTask(User loggedInUser, Board board, String taskId, String newTitle, String newListName, String newPriority, String newDeadline, String newDescription) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Task task;
        List newList;
        ArrayList<List> lists;

        // Check For Error
        authentication(loggedInUser, board);
        task = assertTaskExists(board, taskId);

        // New List (Optional)
        if (newListName != null) {
            lists = ListDao.getBoardLists(board);
            newList = assertListExists(lists, newListName);
            task.setList(newList);
            task.updateStatus(lists);
        }

        // New Priority (Optional)
        if (newPriority != null) {
            task.setPriority(assertPriorityExists(newPriority));
        }

        // New Title (Optional)
        if (newTitle != null) {
            task.setTitle(newTitle);
        }

        // New Deadline (Optional)
        if (newDeadline != null) {
            task.setDeadline(assertValidTimestamp(newDeadline));
        }

        // New Description (Optional)
        if (newDescription != null) {
            task.setDescription(newDescription);
        }


        TaskDao.update(task);
        return new Respond(true, "task edited successfully");
    }

    public Respond assignTask(User loggedInUser, Board board, String taskId, String assignedUsernamesStr) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Task task;
        String[] usernames = assignedUsernamesStr.split(",\\s*");
        ArrayList<User> assignedTo = new ArrayList<>();
        User contributor;

        // Check For Error
        authentication(loggedInUser, board);
        task = assertTaskExists(board, taskId);

        // Get Assigned To Users
        for (String username : usernames) {
            contributor = board.assertContributorExists(username).getUser();
            if (AssignedDao.getAssignedUser(task).keySet().stream().anyMatch(user1 -> user1.getUsername().equals(username)))
                throw new Exception(String.format("task (%s) already assigned to user (%s)", task.getTitle(), username));
            assignedTo.add(contributor);
        }

        // Success
        AssignedDao.assignTaskToUsers(task, assignedTo, Timestamp.from(Instant.now()));
        return new Respond(true, String.format("users with usernames (%s) added to task (%s) successfully", assignedUsernamesStr, task.getTitle()));
    }

    public Respond removeAssignTask(User loggedInUser, Board board, String taskId, String removed) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Task task;
        ArrayList<User> removeUsers = new ArrayList<>();
        Set<User> assignedUsers;
        User assignedUser;

        // Check For Error
        authentication(loggedInUser, board);
        task = assertTaskExists(board, taskId);
        assignedUsers = AssignedDao.getAssignedUser(task).keySet();

        for (String removeUser : removed.split(",\\s*")) {
            assignedUser = assignedUsers.stream().filter(user1 -> user1.getUsername().equals(removeUser)).findFirst().orElse(null);
            if (assignedUser == null)
                throw new Exception(String.format("task (%s) is not assigned to user (%s)", task.getTitle(), removeUser));
            removeUsers.add(assignedUser);
        }

        // Success
        AssignedDao.removeAssignTask(task, removeUsers);
        return new Respond(true, String.format("users with username (%s) removed from task (%s) successfully", removed, task.getTitle()));
    }

    public Respond forceChangeTaskList(User loggedInUser, Board board, String taskId, String listName) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Task selectedTask;
        List newList;
        ArrayList<List> lists;

        // Check For Error
        selectedTask = assertTaskExists(board, taskId);
        if (loggedInUser.getId() != board.getLeader().getId()) {
            selectedTask.assertIsAssignedTo(loggedInUser);
            board.assertIsNotSuspend(loggedInUser.getUsername());
        }
        lists = ListDao.getBoardLists(board);
        newList = assertListExists(lists, listName);
        selectedTask.setList(newList);
        selectedTask.updateStatus(lists);

        // Success
        TaskDao.update(selectedTask);
        return new Respond(true, "task edited successfully");
    }

    public Respond nextPreviousList(User loggedInUser, Board board, String taskId, String nextOrPrevious) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        boolean isNext = nextOrPrevious.contains("next");
        Task selectedTask;
        ArrayList<List> lists;
        List newList;

        // Check For Error
        selectedTask = assertTaskExists(board, taskId);
        if (loggedInUser.getId() != board.getLeader().getId()) {
            selectedTask.assertIsAssignedTo(loggedInUser);
            board.assertIsNotSuspend(loggedInUser.getUsername());
        }
        lists = ListDao.getBoardLists(board);
        if (isNext) {
            if (selectedTask.getList().getOrder() >= lists.size()) throw new Exception("there is no next list");
            newList = lists.get(selectedTask.getList().getOrder());
        } else {
            if (selectedTask.getList().getOrder() - 2 < 0) throw new Exception("there is no previous list");
            newList = lists.get(selectedTask.getList().getOrder() - 2);
        }

        selectedTask.setList(newList);
        selectedTask.updateStatus(lists);

        // Success
        TaskDao.update(selectedTask);
        return new Respond(true, "task edited successfully");
    }

    public Respond showTask(Board board, String taskId) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Task selectedTask;

        // Check For Error
        selectedTask = assertTaskExists(board, taskId);

        // Success
        return new Respond(true, selectedTask);
    }

    public Respond setCommentTask(User loggedInUser, Board board, String taskId, String text) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        Task selectedTask;
        User sendUser;

        // Check For Error
        selectedTask = assertTaskExists(board, taskId);
        if (loggedInUser.getId() != board.getLeader().getId()) {
            selectedTask.assertIsAssignedTo(loggedInUser);
            board.assertIsNotSuspend(loggedInUser.getUsername());
            sendUser = loggedInUser;
        } else sendUser = board.getLeader();


        // Success
        CommentDao.save(new Comment(sendUser, text, selectedTask, Timestamp.from(Instant.now())));
        return new Respond(true, "comment set successfully");
    }
}
