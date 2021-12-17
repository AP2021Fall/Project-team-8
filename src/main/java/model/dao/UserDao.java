package model.dao;

import model.domain.Date;
import model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static User getByUsername(String username) throws SQLException {
        String getByUsername = "select * from `user` where `user`.`username` = ?";
        ResultSet resultSet;

        PreparedStatement preparedStatement = preparedStatement(getByUsername);
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) return null;
        resultSet.next();

        // Return User
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                new Date(resultSet.getString("birthday")),
                resultSet.getBoolean("is_admin"),
                resultSet.getBoolean("is_ban")
        );
    }

    public static ArrayList<User> getAllUsers() throws SQLException {
        // Get Users
        String getAllUsers = "select * from `user` where `user`.`is_admin` = 0 order by `user`.`id`";
        ArrayList<User> users = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement = preparedStatement(getAllUsers);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
           users.add(new User(
                   resultSet.getInt("id"),
                   resultSet.getString("username"),
                   resultSet.getString("password"),
                   resultSet.getString("name"),
                   resultSet.getString("email"),
                   new Date(resultSet.getString("birthday")),
                   resultSet.getBoolean("is_admin"),
                   resultSet.getBoolean("is_ban")
           ));
        }
        return users;
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
        String updateUser = "update `user` set `username` = ?, `password` = ?, `name` = ?, `email` = ?, `birthday` = ?, `is_ban` = ?  where `user`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateUser);
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getBirthday().toString());
        preparedStatement.setBoolean(6, user.isBan());
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