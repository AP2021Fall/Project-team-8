package view.entities;

import controller.BoardController;
import model.domain.Board;
import model.domain.List;
import model.domain.Respond;
import model.domain.Task;
import view.*;

import java.util.ArrayList;

public class BoardView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.BOARD);
    private static final BoardController controller = new BoardController();

    /* Instance Fields */
    private final Board board;

    /* Constructor */
    public BoardView(Window window, Board board) {
        super(window);
        this.board = board;
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();

        // For Leader
        //      List
        if (input.getCurrentC() == Command.BOARD_ADD_LIST) addList(input.get("name"), input.get("order"));
        else if (input.getCurrentC() == Command.BOARD_REMOVE_LIST) removeList(input.get("name"));
        else if (input.getCurrentC() == Command.BOARD_CHANGE_LIST)
            changeList(input.get("name"), input.get("newName"), input.get("order"));
            //      Task
        else if (input.getCurrentC() == Command.BOARD_ADD_TASK)
            addTask(input.get("title"), input.get("deadline"), input.get("list"), input.get("priority"), input.get("description"));
        else if (input.getCurrentC() == Command.BOARD_DELETE_TASK) deleteTask(input.get("id"));
        else if (input.getCurrentC() == Command.BOARD_CHANGE_TASK)
            changeTask(input.get("id"), input.get("title"), input.get("deadline"), input.get("list"), input.get("priority"), input.get("description"));
        else if (input.getCurrentC() == Command.BOARD_ASSIGN_TO) assignTo(input.get("assign"), input.get("taskId"));
        else if (input.getCurrentC() == Command.BOARD_REMOVE_ASSIGN_TO)
            removeAssign(input.get("assign"), input.get("taskId"));
            //      User
        else if (input.getCurrentC() == Command.BOARD_INVITE_USER) inviteUser(input.get("username"));
        else if (input.getCurrentC() == Command.BOARD_REMOVE_USER) removeUser(input.get("username"));
        else if (input.getCurrentC() == Command.BOARD_CHANGE_LEADER) changeLeader(input.get("username"));
        else if (input.getCurrentC() == Command.BOARD_SUSPEND_USER)
            suspendUser(input.get("username"), input.get("unBan"));
            // For All
        else if (input.getCurrentC() == Command.BOARD_SHOW_DETAILS) System.out.println(board);
        else if (input.getCurrentC() == Command.BOARD_SHOW_LISTS) showLists();
        else if (input.getCurrentC() == Command.BOARD_TASK_NEXT_BACK)
            nextPrevious(input.get("id"), input.get("direction"));
        else if (input.getCurrentC() == Command.BOARD_TASK_FORCE) forceTask(input.get("id"), input.get("listName"));
        else if (input.getCurrentC() == Command.BOARD_TASK_COMMENT) sendComment(input.get("id"), input.get("comment"));
        else if (input.getCurrentC() == Command.BOARD_TASK_SHOW) showTask(input.get("id"));
        else if (input.getCurrentC() == Command.BOARD_CHAT) window.pushView(new ChatView(window, board));
        else if (input.getCurrentC() == Command.BOARD_SCOREBOARD) window.pushView(new ScoreboardView(window, board));
        else if (input.getCurrentC() == Command.BOARD_ROADMAP) window.pushView(new RoadmapView(window, board));
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    private void showTask(String taskId) {
        Respond respond = controller.request(() -> controller.showTask(board, taskId));

        // Now Handle Respond
        if (respond.isSuccess() && respond.getContent() instanceof Task) {
            Task task = (Task) respond.getContent();
            System.out.println(task.toStringDetails());
        } else {
            System.out.println(respond.getMessage());
        }
    }

    private void sendComment(String taskId, String comment) {
        Respond respond = controller.request(() -> controller.setCommentTask(window.getUser(), board, taskId, comment));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    private void nextPrevious(String taskId, String direction) {
        Respond respond = controller.request(() -> controller.nextPreviousList(window.getUser(), board, taskId, direction));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    private void forceTask(String taskId, String listName) {
        Respond respond = controller.request(() -> controller.forceChangeTaskList(window.getUser(), board, taskId, listName));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    private void suspendUser(String username, String unban) {
        Respond respond = controller.request(() -> controller.suspendUser(window.getUser(), board, username, unban));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    private void changeLeader(String username) {
        Respond respond = controller.request(() -> controller.changeLeader(window.getUser(), board, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    private void changeTask(String id, String title, String deadline, String list, String priority, String description) {
        Respond respond = controller.request(() -> controller.changeTask(window.getUser(), board, id, title, list, priority, deadline, description));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void inviteUser(String username) {
        Respond respond = controller.request(() -> controller.inviteUser(window.getUser(), board, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void removeUser(String username) {
        Respond respond = controller.request(() -> controller.removeUser(window.getUser(), board, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void changeList(String name, String newName, String order) {
        Respond respond = controller.request(() -> controller.changeList(window.getUser(), board, name, newName, order));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void addList(String name, String order) {
        Respond respond = controller.request(() -> controller.addList(window.getUser(), board, name, order));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void removeList(String name) {
        Respond respond = controller.request(() -> controller.removeList(window.getUser(), board, name));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void removeAssign(String removeUsers, String taskId) {
        Respond respond = controller.request(() -> controller.removeAssignTask(window.getUser(), board, taskId, removeUsers));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void assignTo(String users, String taskId) {
        Respond respond = controller.request(() -> controller.assignTask(window.getUser(), board, taskId, users));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void deleteTask(String id) {
        Respond respond = controller.request(() -> controller.removeTask(window.getUser(), board, id));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void addTask(String title, String deadline, String list, String priority, String description) {
        Respond respond = controller.request(() -> controller.addTask(window.getUser(), board, title, deadline, list, priority, description));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    @SuppressWarnings("unchecked")
    public void showLists() {
        Respond respond = controller.request(() -> controller.getLists(board));

        // Now Handle Respond
        if (respond.isSuccess()) {
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<List> lists = (ArrayList<List>) respond.getContent();
            if (lists.size() == 0) stringBuilder.append("there is no list in this board\n");
            for (List list : lists) {
                stringBuilder.append(list).append("\n");
            }
            System.out.print(stringBuilder);
        } else {
            System.out.println(respond.getMessage());
        }
    }
}