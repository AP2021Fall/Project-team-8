package view.entities;

import controller.AdminController;
import model.domain.Board;
import model.domain.Respond;
import model.domain.User;
import view.*;

import java.util.ArrayList;

public class AdminView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.ADMIN);
    private static final AdminController controller = new AdminController();

    /* Instance Fields */
    private final User user;

    /* Constructor */
    public AdminView(Window window, User user) {
        super(window);
        this.user = user;
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.ADMIN_USER_SHOW) showProfile(input.get("username"));
        else if (input.getCurrentC() == Command.ADMIN_BOARD_SHOW_ALL) showAllBoards();
        else if (input.getCurrentC() == Command.ADMIN_BOARD_SHOW_PENDING) showPendingBoards();
        else if (input.getCurrentC() == Command.ADMIN_BOARD_SHOW_SCOREBOARD) showScoreboard(input.get("board"));
        else if (input.getCurrentC() == Command.ADMIN_USER_BAN) banUser(input.get("username"));
        else if (input.getCurrentC() == Command.ADMIN_USER_UNBAN) unBanUser(input.get("username"));
        else if (input.getCurrentC() == Command.ADMIN_USER_REMOVE) removeUser(input.get("username"));
        else if (input.getCurrentC() == Command.ADMIN_BOARD_ACCEPT) acceptBoards(input.get("board"));
        else if (input.getCurrentC() == Command.ADMIN_BOARD_REJECT) rejectBoards(input.get("board"));
        else if (input.getCurrentC() == Command.ADMIN_BOARD_CHANGE_LEADER)
            changeLeader(input.get("board"), input.get("username"));
        else if (input.getCurrentC() == Command.ADMIN_SEND_NOTIFICATION_ALL) notificationAll(input.get("notification"));
        else if (input.getCurrentC() == Command.ADMIN_SEND_NOTIFICATION_BOARD)
            notificationBoard(input.get("notification"), input.get("board"));
        else if (input.getCurrentC() == Command.ADMIN_SEND_NOTIFICATION_USER)
            notificationUser(input.get("notification"), input.get("username"));

        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    public void showScoreboard(String board) {
        Respond respond = controller.request(() -> controller.showScoreboard(this.user, board));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getContent());
        } else {
            System.out.println(respond.getMessage());
        }
    }

    public void notificationAll(String notification) {
        Respond respond = controller.request(() -> controller.sendNotificationToAll(this.user, notification));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void notificationBoard(String notification, String boardId) {
        Respond respond = controller.request(() -> controller.sendNotificationToBoard(this.user, notification, boardId));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void notificationUser(String notification, String username) {
        Respond respond = controller.request(() -> controller.sendNotificationToUser(this.user, notification, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void changeLeader(String boardId, String username) {
        Respond respond = controller.request(() -> controller.changeBoardLeader(this.user, boardId, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void acceptBoards(String boardsId) {
        Respond respond = controller.request(() -> controller.acceptBoards(this.user, boardsId));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void rejectBoards(String boardsId) {
        Respond respond = controller.request(() -> controller.rejectBoards(this.user, boardsId));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void banUser(String username) {
        Respond respond = controller.request(() -> controller.banUser(this.user, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void unBanUser(String username) {
        Respond respond = controller.request(() -> controller.unBanUser(this.user, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void removeUser(String username) {
        Respond respond = controller.request(() -> controller.removeUser(this.user, username));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void showProfile(String username) {
        Respond respond = controller.request(() -> controller.getProfile(this.user, username));

        // Now Handle Respond
        if (respond.isSuccess()) System.out.println(respond.getContent());
        else System.out.println(respond.getMessage());
    }

    @SuppressWarnings("unchecked")
    public void showAllBoards() {
        Respond respond = controller.request(() -> controller.getAllBoards(this.user));

        // Now Handle Respond
        if (respond.isSuccess()) {
            ArrayList<Board> boards = (ArrayList<Board>) respond.getContent();
            System.out.print(Board.BoardListString(boards));
        } else System.out.println(respond.getMessage());
    }

    @SuppressWarnings("unchecked")
    public void showPendingBoards() {
        Respond respond = controller.request(() -> controller.getPendingBoards(this.user));

        // Now Handle Respond
        if (respond.isSuccess()) {
            ArrayList<Board> boards = (ArrayList<Board>) respond.getContent();
            System.out.print(Board.BoardListString(boards));
        } else System.out.println(respond.getMessage());
    }
}
