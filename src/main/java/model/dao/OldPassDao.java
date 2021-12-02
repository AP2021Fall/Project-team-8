package model.dao;

import model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OldPassDao extends Dao {
    /* Static Methods */
    public static void addOldPass(User user) throws SQLException {
        String insertOldPass = "insert into `old_pass` (`user_id`, `password`) values (?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertOldPass);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();
    }

    public static ArrayList<String> getOldPasswords(User user) throws SQLException {
        String getOldPassword = "select `password` from `old_pass` where `old_pass`.`user_id` = ?";
        ResultSet resultSet;
        ArrayList<String> oldPasswords = new ArrayList<>();

        PreparedStatement preparedStatement = preparedStatement(getOldPassword);
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            oldPasswords.add(resultSet.getString("password"));
        }
        return oldPasswords;
    }
}
