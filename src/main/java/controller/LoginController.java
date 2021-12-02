package controller;


import model.dao.UserDao;
import model.domain.Date;
import model.domain.Respond;
import model.domain.User;

public class LoginController extends Controller {
    /* Static Fields */
    private final static UserDao userDao = new UserDao();

    /* Instance Methods */
    public Respond signUp(String username, String password1, String password2, String email, String name, String birthday) throws Exception {
        // NOTE: all logics go here if we have error throw Exception
        // Check For Error
        if (userDao.countByUsername(username) != 0)
            throw new Exception(String.format("user with username %s already exists!", username));
        if (!password1.equals(password2)) throw new Exception("Your passwords are not the same!");
        if (userDao.countByEmail(email) != 0) throw new Exception("User with this email already exists!");
        assertValidEmail(email);

        // Success
        userDao.save(new User(username, password1, name, email, new Date(birthday)));
        return new Respond(true, "user created successfully!");
    }

    public Respond login(String username, String password) throws Exception {
        // NOTE: all logics go here if we have error throw Exception

        // Check For Error
        User user = userDao.getByUsername(username);
        if (user == null) throw new Exception(String.format("There is not any user with username: %s!", username));
        if (!user.getPassword().equals(password)) throw new Exception("Username and password didn't match!");

        // Success
        return new Respond(true, "user logged in successfully!", user);
    }

    private void assertValidEmail(String email) throws Exception {
        if (!email.matches("^[a-zA-Z0-9\\.]+@(gmail\\.com|yahoo\\.com)$"))
            throw new Exception("Email address is invalid!");
    }
}
