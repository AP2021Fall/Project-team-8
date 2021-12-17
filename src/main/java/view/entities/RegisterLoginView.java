package view.entities;

import controller.LoginController;
import model.domain.Respond;
import model.domain.User;
import view.*;

public class RegisterLoginView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.REGISTER_LOGIN);
    private static final LoginController controller = new LoginController();

    /* Constructor */
    public RegisterLoginView(Window window) {
        super(window);
    }

    /* Instance Fields */
    @Override
    public void run() {
        input.interact();
        if (input.getCurrentC() == Command.REGISTER_LOGIN_SIGN_UP)
            handleSignUp(
                    input.get("username"), input.get("password1"), input.get("password2"),
                    input.get("email"), input.get("name"), input.get("birthday")
            );
        else if (input.getCurrentC() == Command.REGISTER_LOGIN_LOGIN)
            handleLogin(input.get("username"), input.get("password"));
        else if (input.getCurrentC() == Command.GLOBAL_BACK) back();
        else if (input.getCurrentC() == Command.GLOBAL_HELP) input.commandHelp();
    }

    public void handleSignUp(String username, String pass1, String pass2, String email, String name, String birthday) {
        Respond respond = controller.request(() -> controller.signUp(username, pass1, pass2, email, name, birthday));

        // Now Handle Respond
        System.out.println(respond.getMessage());
    }

    public void handleLogin(String username, String password) {
        Respond respond = controller.request(() -> controller.login(username, password));
        User user;
        // Now Handle Respond
        if (respond.isSuccess()) {
            user = (User) respond.getContent();
            if (user.isAdmin()) window.pushView(new AdminView(window, user));
            else window.pushView(new MainView(window, user));
        }
        System.out.println(respond.getMessage());
    }
}
