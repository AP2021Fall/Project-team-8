package model.dao;

import model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class LogDao extends Dao{
    /* Static Methods */
    public static void addLog(User user, Timestamp now) throws SQLException {
        String insertLog = "insert into `log` (`user_id`, `log_in_at`) values (?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertLog);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.setTimestamp(2, now);
        preparedStatement.executeUpdate();
    }

    public static ArrayList<Timestamp> getLogs(User user) throws SQLException {
        String getLogs = "select `log_in_at` as `time` from `log` where `log`.`user_id` = ? order by `time` desc";
        ResultSet resultSet;
        ArrayList<Timestamp> timestamps = new ArrayList<>();

        PreparedStatement preparedStatement = preparedStatement(getLogs);
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            timestamps.add(resultSet.getTimestamp("time"));
        }
        return timestamps;
    }
}
