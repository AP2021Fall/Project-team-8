package view.entities;

import controller.WorkspaceController;
import model.domain.Board;
import model.domain.Respond;
import view.*;


public class WorkspaceView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.WORKSPACE);
    private static final WorkspaceController controller = new WorkspaceController();

    /* Constructor */
    public WorkspaceView(Window window) {
        super(window);
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.WORKSPACE_ENTER_BOARD) enterBoard(input.get("board"));
        else if (input.getCurrentC() == Command.WORKSPACE_CHANGE_BOARD_NAME)
            changeBoardName(input.get("board"), input.get("newName"));
        else if (input.getCurrentC() == Command.WORKSPACE_QUIT_BOARD) quitBoard(input.get("board"));
        else if (input.getCurrentC() == Command.WORKSPACE_CREATE_BOARD) createBoard(input.get("board"));
        else if (input.getCurrentC() == Command.WORKSPACE_REMOVE_BOARD) removeBoard(input.get("board"));
        else if (input.getCurrentC() == Command.WORKSPACE_SHOW) showWorkspace();
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    public void showWorkspace() {
        Respond respond = controller.request(() -> controller.getWorkspaceNew(window.getUser()));

        // Now Handle Respond
        if (respond.isSuccess()) System.out.println(respond.getContent());
        else System.out.println(respond.getMessage());
    }

    public void createBoard(String board) {
        Respond respond = controller.request(() -> controller.createBoard(window.getUser(), board));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void changeBoardName(String board, String newName) {
        Respond respond = controller.request(() -> controller.changeName(window.getUser(), board, newName));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void enterBoard(String board) {
        Respond respond = controller.request(() -> controller.selectBoard(window.getUser(), board));

        // Now Handle Respond
        if (respond.isSuccess()) {
            window.pushView(new BoardView(window, (Board) respond.getContent()));
        } else {

            System.out.println(respond.getMessage());
        }
    }

    public void removeBoard(String board) {
        Respond respond = controller.request(() -> controller.removeBoard(window.getUser(), board));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void quitBoard(String board) {
        Respond respond = controller.request(() -> controller.quitBoard(window.getUser(), board));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }
}