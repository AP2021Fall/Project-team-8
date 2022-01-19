package app.jira.model.dao;

import app.jira.model.domain.Task;
import app.jira.model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AssignedDao extends Dao {
    /* Static Methods */
    public static LinkedHashMap<User, Timestamp> getAssignedUser(Task task) throws SQLException {
        String getAssigned = "select * from `user` join `assigned` on `user`.`id` = `assigned`.`user_id` where `assigned`.`task_id` = ?";
        LinkedHashMap<User, Timestamp> assignedUsers = new LinkedHashMap<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement = preparedStatement(getAssigned);
        preparedStatement.setInt(1, task.getId());

        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            assignedUsers.put(new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("name")
            ), resultSet.getTimestamp("assigned_at"));
        }
        return assignedUsers;
    }

    public static void removeAssignTask(Task task, ArrayList<User> users) throws SQLException {
        String assignTaskToUser = "delete from `assigned` where `assigned`.`user_id` = ? and `assigned`.`task_id` = ?;";
        PreparedStatement preparedStatement = preparedStatement(assignTaskToUser);
        int i = 0;

        for (User user : users) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, task.getId());

            preparedStatement.addBatch();
            i++;

            if (i % 1000 == 0 || i == users.size()) {
                preparedStatement.executeBatch(); // Execute every 1000 items.
            }
        }
    }

    public static void assignTaskToUsers(Task task, ArrayList<User> users, Timestamp now) throws SQLException {
        String assignTaskToUser = "insert into `assigned` (`user_id`, `task_id`, `assigned_at`) value (?, ?, ?);";
        PreparedStatement preparedStatement = preparedStatement(assignTaskToUser);
        int i = 0;

        for (User user : users) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, task.getId());
            preparedStatement.setTimestamp(3, now);

            preparedStatement.addBatch();
            i++;

            if (i % 1000 == 0 || i == users.size()) {
                preparedStatement.executeBatch(); // Execute every 1000 items.
            }
        }
    }
}
