package app.jira.controller;

import app.jira.model.dao.UserDao;
import app.jira.model.domain.Respond;
import app.jira.model.domain.User;

public class ForgetPasswordController extends Controller{
    public Respond changePassword(String username,String date,String email,String newPass, String confirm) throws Exception {
        User user = UserDao.getByUsername(username);
        System.out.println(user.getEmail());
        if (user != null)
            if (user.getBirthday().toString().equals(date))
                if (user.getEmail().equals(email))
                    if(newPass.equals(confirm))
                        if(newPass.length() > 5) {
                            User.assertValidPassword(newPass);
                            user.setPassword(newPass);
                            UserDao.update(user);
                            return new Respond(true,"Your password has been changed");
                        } else
                            return new Respond(false,"Password must be at least 8 characters");
                     else
                        return new Respond(false,"Password and its confirm aren't match");
                else
                    return new Respond(false,"User email is invalid!");
            else
                return new Respond(false,"User birthday is invalid");
        else
            return new Respond(false,"There is no User with this username");
    }

}
