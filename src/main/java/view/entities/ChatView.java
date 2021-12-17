package view.entities;

import controller.ChatController;
import model.domain.Board;
import model.domain.Chat;
import model.domain.Respond;
import view.*;

import java.util.ArrayList;

public class ChatView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.CHAT);
    private static final ChatController controller = new ChatController();

    /* Instance Fields */
    private final Board board;

    /* Constructor */
    public ChatView(Window window, Board board) {
        super(window);
        this.board = board;
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.CHAT_SHOW) showChats();
        else if (input.getCurrentC() == Command.CHAT_SEND_MESSAGE) sendMessage(input.get("message"));
        else if (input.getCurrentC() == Command.CHAT_SEND_NOTIFICATION)
            sendNotification(input.get("user"), input.get("notification"));
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    /* Instance Methods */
    @SuppressWarnings("unchecked")
    private void showChats() {
        Respond respond = controller.request(() -> controller.getChats(board));

        // Now Handle Respond
        if (respond.isSuccess()) {
            StringBuilder out = new StringBuilder();
            ArrayList<Chat> chats = (ArrayList<Chat>) respond.getContent();
            if (chats.size() == 0) out.append("no message yet\n");
            for (Chat chat : chats) {
                out.append(chat).append("\n");
            }
            System.out.print(out);
        } else {
            System.out.println(respond.getMessage());
        }
    }

    private void sendMessage(String message) {
        Respond respond = controller.request(() -> controller.sendMessage(window.getUser(), board, message));

        // Now Handle Respond
        if (respond.isSuccess()) {
            showChats();
        } else {
            System.out.println(respond.getMessage());
        }
    }

    private void sendNotification(String username, String text) {
        Respond respond = controller.request(() -> controller.sendNotification(window.getUser(), board, username, text));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

}