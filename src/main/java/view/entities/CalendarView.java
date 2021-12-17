package view.entities;

import controller.CalendarController;
import model.domain.Respond;
import view.*;

public class CalendarView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.CALENDAR);
    private static final CalendarController controller = new CalendarController();

    /* Constructor */
    public CalendarView(Window window) {
        super(window);
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.CALENDAR_SHOW_DEADLINES) showDeadlines();
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    private void showDeadlines() {
        Respond respond = controller.request(() -> controller.showCalendar(window.getUser()));

        // Now Handle Respond
        if (respond.isSuccess()) {
            System.out.println(respond.getContent());
        } else System.out.println(respond.getMessage());
    }
}
