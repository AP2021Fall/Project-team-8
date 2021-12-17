package view.entities;

import controller.MainController;
import model.domain.User;
import view.*;

public class MainView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.MAIN);
    private static final MainController controller = new MainController();

    /* Constructor */
    public MainView(Window window, User user) {
        super(window);
        window.setUser(user);
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.MAIN_ENTER_MENU) enterMenu(input.get("menuName"));
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    public void enterMenu(String menuName) {
        View view = Menu.createView(menuName, window);
        if (view == null) {
            System.out.println("Invalid Menu Name (Available Menu: " + Menu.availableMenu() + ")");
        } else {
            window.pushView(view);
        }
    }

    @Override
    public void back() {
        super.back();
        window.setUser(null);
    }
}
