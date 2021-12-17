package controller;

import model.dao.LogDao;
import model.dao.NotificationDao;
import model.dao.OldPassDao;
import model.dao.UserDao;
import model.domain.Respond;
import model.domain.User;

public class ProfileController extends Controller {
    /* Instance Methods */
    public Respond changePass(User loggedInUser, String oldPass, String newPass) throws Exception {
        // NOTE: all logics go here if we have error throw Exception

        // Check For Error
        if (!loggedInUser.getPassword().equals(oldPass)) throw new Exception("wrong old password!");
        if (OldPassDao.countOldPassword(loggedInUser, newPass) != 0) throw new Exception("Please type a New Password!");
        User.assertValidPassword(newPass);
        // Success
        loggedInUser.setPassword(newPass);
        OldPassDao.addOldPass(loggedInUser);
        UserDao.update(loggedInUser);
        return new Respond(true, "password changed successfully");
    }

    public Respond changeUsername(User loggedInUser, String newUsername) throws Exception {
        // NOTE: all logics go here if we have error throw Exception

        // Check For Error
        User.assertValidUsername(newUsername);
        if (UserDao.countByUsername(newUsername) > 0) throw new Exception("username already taken!");
        if (loggedInUser.getUsername().equals(newUsername)) throw new Exception("you already have this username!");

        // Success
        loggedInUser.setUsername(newUsername);
        UserDao.update(loggedInUser);
        return new Respond(true, "username changed successfully");
    }

    public Respond getWorkspace(User loggedInUser) throws Exception {
        // Success
        return new Respond(true, WorkspaceController.getWorkspace(loggedInUser));
    }

    public Respond getBoard(User loggedInUser, String boardNameOrNum) throws Exception {
        WorkspaceController controller = new WorkspaceController();
        // Success
        return controller.selectBoard(loggedInUser, boardNameOrNum);
    }

    public Respond getNotifications(User loggedInUser) throws Exception {
        // Success
        return new Respond(true, NotificationDao.getNotifications(loggedInUser));
    }

    public Respond getLogs(User loggedInUser) throws Exception {
        // Success
        return new Respond(true, LogDao.getLogs(loggedInUser));
    }
}
