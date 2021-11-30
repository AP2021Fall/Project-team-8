package controller;

import model.domain.Respond;

public class LoginController extends Controller {
    /* Constructor */
    public LoginController() {
        super();
    }

    /* Instance Methods */
    public Respond signUp(String username, String password1, String password2, String email, String name, String birthday) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        // Check For Error

        // Success
        return new Respond(true, "user created successfully!");
    }

    public Respond login(String username, String password) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        // Check For Error

        // Success
        return new Respond(true, "user logged in successfully!", null);
    }
}
