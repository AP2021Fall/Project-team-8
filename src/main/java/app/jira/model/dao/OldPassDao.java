package app.jira.model.dao;

import app.jira.model.domain.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OldPassDao extends Dao {
    /* Static Methods */
    public static void addOldPass(User user) throws SQLException {
        String insertOldPass = "insert into `old_pass` (`user_id`, `password`) values (?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertOldPass);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();
    }

    public static int countOldPassword(User user, String password) throws SQLException {
        String getOldPassword = "select `password` from `old_pass` where `old_pass`.`user_id` = ? and `old_pass`.`password` = ?";

        PreparedStatement preparedStatement = preparedStatement(getOldPassword);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, password);
        return getCount(preparedStatement);
    }
}
