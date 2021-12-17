package view.entities;

import controller.ScoreboardController;
import model.domain.Board;
import model.domain.Respond;
import view.*;

public class ScoreboardView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.SCOREBOARD);
    private static final ScoreboardController controller = new ScoreboardController();

    /* Instance Fields */
    private final Board board;

    /* Constructor */
    public ScoreboardView(Window window, Board board) {
        super(window);
        this.board = board;
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.SCOREBOARD_SHOW) showScoreboard();

        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    public void showScoreboard() {
        Respond respond = controller.request(() -> controller.showScoreboard(board));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.print(respond.getContent());
        } else {
            System.out.println(respond.getMessage());
        }
    }

}