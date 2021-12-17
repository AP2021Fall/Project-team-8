package view.entities;

import controller.ProfileController;
import model.domain.Date;
import model.domain.Notification;
import model.domain.Respond;
import view.*;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ProfileView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.PROFILE);
    private static final ProfileController controller = new ProfileController();

    /* Instance Fields */
    private int wrongPassCounter = 0;

    /* Constructor */
    public ProfileView(Window window) {
        super(window);
        System.out.println(String.format("Profile Menu (user: %s)", window.getUser().getUsername()));
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.PROFILE_SHOW) System.out.println(window.getUser());
        else if (input.getCurrentC() == Command.PROFILE_SHOW_BOARD) showBoard(input.get("board"));
        else if (input.getCurrentC() == Command.PROFILE_SHOW_BOARDS) showBoards();
        else if (input.getCurrentC() == Command.PROFILE_SHOW_NOTIFICATION) showNotifications();
        else if (input.getCurrentC() == Command.PROFILE_SHOW_LOG) showLogs();
        else if (input.getCurrentC() == Command.PROFILE_CHANGE_PASSWORD)
            changePassword(input.get("oldPass"), input.get("newPass"));
        else if (input.getCurrentC() == Command.PROFILE_CHANGE_USERNAME) changeUsername(input.get("newUsername"));
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    @SuppressWarnings("unchecked")
    public void showLogs() {
        Respond respond = controller.request(() -> controller.getLogs(window.getUser()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            StringBuilder out = new StringBuilder();
            ArrayList<Timestamp> logs = (ArrayList<Timestamp>) respond.getContent();
            out.append("your logged in info:\n");
            for (Timestamp log : logs) {
                out.append(Date.timeFormat.format(log)).append("\n");
            }
            out.setLength(out.length() - 1);
            System.out.println(out);
        } else {
            System.out.println(respond.getMessage());
        }
    }

    public void showBoard(String boardNameOrNum) {
        Respond respond = controller.request(() -> controller.getBoard(window.getUser(), boardNameOrNum));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getContent());
        } else {
            System.out.println(respond.getMessage());
        }
    }

    public void showBoards() {
        Respond respond = controller.request(() -> controller.getWorkspace(window.getUser()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getContent());
        } else {
            System.out.println(respond.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void showNotifications() {
        Respond respond = controller.request(() -> controller.getNotifications(window.getUser()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            StringBuilder out = new StringBuilder();
            ArrayList<Notification> notifications = (ArrayList<Notification>) respond.getContent();
            if (notifications.size() == 0) out.append("no notification yet\n");
            for (Notification notification : notifications) {
                out.append(notification.getFrom().getName()).append(": ").append(notification.getText())
                        .append(String.format(" (send at: %s)", Date.timeFormat.format(notification.getSendAt())))
                        .append("\n");
            }
            System.out.print(out);
        } else {
            System.out.println(respond.getMessage());
        }
    }

    public void changePassword(String oldPass, String newPass) {
        Respond respond = controller.request(() -> controller.changePass(window.getUser(), oldPass, newPass));

        // Now Handle Respond
        if (!respond.isSuccess() && respond.getMessage().contains("wrong")) {
            if (wrongPassCounter == 2) {
                System.out.println("you were kicked from system");
                window.getView().back();
                window.getView().back();
            }
            wrongPassCounter++;
        }
        System.out.println(respond.getMessage());
    }

    public void changeUsername(String newUsername) {
        Respond respond = controller.request(() -> controller.changeUsername(window.getUser(), newUsername));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }
}
