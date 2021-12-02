package model.dao;

import model.domain.Date;
import model.domain.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao extends Dao {
    /* Static Methods */
    public static int countByUsername(String username) throws SQLException {
        String getByUsername = "select * from `user` where `user`.`username` = ?";
        PreparedStatement preparedStatement = preparedStatement(getByUsername);
        preparedStatement.setString(1, username);
        return getCount(preparedStatement);
    }

    public static int countByEmail(String email) throws SQLException {
        String getByEmail = "select * from `user` where `user`.`email` = ?";
        PreparedStatement preparedStatement = preparedStatement(getByEmail);
        preparedStatement.setString(1, email);
        return getCount(preparedStatement);
    }

    public static User getByUsernamePassword(String username, String password) throws SQLException {
        User user = new User();
        String getByUsername = "select * from `user` where `user`.`username` = ? and `user`.`password` = ?";
        ResultSet resultSet;

        PreparedStatement preparedStatement = preparedStatement(getByUsername);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) return null;
        resultSet.next();

        // Set User Fields
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setAdmin(resultSet.getBoolean("is_admin"));
        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthday(new Date(resultSet.getString("birthday")));
        user.setScore(resultSet.getInt("score"));
        user.setOldPasswords(OldPassDao.getOldPasswords(user));
        user.setLogs(LogDao.getLogs(user));
        return user;
    }

    public static void save(User user) throws SQLException {
        String insertUser = "insert into `user` (`username`, `password`, `email`, `name`, `birthday`) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertUser);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getName());
        preparedStatement.setString(5, user.getBirthday().toString());
        preparedStatement.executeUpdate();
        user.setId(getGenerateKey(preparedStatement));
    }

    public static void update(User user) throws SQLException {
        String updateUser = "update `user` set `username` = ?, `password` = ?, `name` = ?, `email` = ?, `score` = ?, `birthday` = ?  where `user`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateUser);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setInt(5, user.getScore());
        preparedStatement.setString(6, user.getBirthday().toString());
        preparedStatement.setInt(7, user.getId());
        preparedStatement.executeUpdate();
    }

    public static void delete(User user) throws SQLException {
        String deleteUserById = "delete from `user` where `user`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(deleteUserById);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }
}