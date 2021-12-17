package view.entities;

import controller.RoadmapController;
import model.domain.Board;
import model.domain.Respond;
import view.*;

public class RoadmapView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.ROADMAP);
    private static final RoadmapController controller = new RoadmapController();

    /* Instance Fields */
    private final Board board;

    /* Constructor */
    public RoadmapView(Window window, Board board) {
        super(window);
        this.board = board;
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.ROADMAP_SHOW) showRoadmap();

        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    public void showRoadmap() {
        Respond respond = controller.request(() -> controller.showRoadmap(board));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.print(respond.getContent());
        } else {
            System.out.println(respond.getMessage());
        }
    }
}