package app.jira.model.dao;

import app.jira.model.domain.Notification;
import app.jira.model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class NotificationDao extends Dao {
    /* Static Methods */
    public static ArrayList<Notification> getNotifications(User user) throws SQLException {
        String getNotifications = "select * from `user` join `notification` on `user`.`id` = `notification`.`from_id` where `notification`.`to_id` = ? order by `notification`.`send_at` desc";
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        ArrayList<Notification> notifications = new ArrayList<>();
        preparedStatement = preparedStatement(getNotifications);
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            notifications.add(new Notification(
                    new User(
                            resultSet.getInt("from_id"),
                            resultSet.getString("username"),
                            resultSet.getString("name")),
                    user,
                    resultSet.getTimestamp("send_at"),
                    resultSet.getString("text")
            ));
        }
        return notifications;
    }

    public static void sendNotificationToUsers(User from, String text, Timestamp now, ArrayList<User> users) throws SQLException {
        String insertNotification = "insert into `notification` (`to_id`, `from_id`, `text`, `send_at`) values(?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertNotification);
        int i = 0;

        for (User user : users) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, from.getId());
            preparedStatement.setString(3, text);
            preparedStatement.setTimestamp(4, now);

            preparedStatement.addBatch();
            i++;

            if (i % 1000 == 0 || i == users.size()) {
                preparedStatement.executeBatch(); // Execute every 1000 items.
            }
        }
    }
}
