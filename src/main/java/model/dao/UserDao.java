package model.dao;


import model.domain.Date;
import model.domain.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;

public class UserDao extends Dao<User> {

    /* Constructor */
    public UserDao() {
    }


    /* Instance Fields */
    public int countByUsername(String username) throws SQLException {
        String getByUsername = "select * from `user` where `user`.`username` = ?";
        PreparedStatement preparedStatement = preparedStatement(getByUsername);
        preparedStatement.setString(1, username);
        return getCount(preparedStatement);
    }

    public int countByEmail(String email) throws SQLException {
        String getByEmail = "select * from `user` where `user`.`email` = ?";
        PreparedStatement preparedStatement = preparedStatement(getByEmail);
        preparedStatement.setString(1, email);
        return getCount(preparedStatement);
    }

    public User getByUsername(String username) throws SQLException {
        ArrayList<Dictionary<String, String>> result;
        Dictionary<String, String> user;
        String getByUsername = "select * from `user` where `user`.`username` = ?";
        PreparedStatement preparedStatement = preparedStatement(getByUsername);
        preparedStatement.setString(1, username);
        result = getResult(preparedStatement);
        if (result.size() != 1) return null;
        user = result.get(0);
        return new User(Integer.parseInt(user.get("id")), user.get("username"), user.get("password"), user.get("name"), user.get("email"), new Date(user.get("birthday")), Boolean.parseBoolean(user.get("is_admin")));
    }

    @Override
    public void save(User user) throws SQLException {
        String insertUser = "insert into `user` (`username`, `password`, `email`, `name`, `birthday`) values (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertUser);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getName());
        preparedStatement.setString(5, user.getBirthday().toString());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(User user) throws SQLException {
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

    @Override
    public void delete(User user) throws SQLException {
        String deleteUserById = "delete from `user` where `user`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(deleteUserById);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
    }
}