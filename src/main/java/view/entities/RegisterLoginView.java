package view.entities;

import view.*;

public class RegisterLoginView extends View {
    /* Static Fields */
    private static final Input input = new Input(Section.REGISTER_LOGIN);

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
        System.out.println("handle sign up");
    }

    public void handleLogin(String username, String password) {
        System.out.println("handle login");
    }
}
