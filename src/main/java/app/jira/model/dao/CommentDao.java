package app.jira.model.dao;

import app.jira.model.domain.Comment;
import app.jira.model.domain.Task;
import app.jira.model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDao extends Dao {
    /* Static Methods */
    public static ArrayList<Comment> getCommentsByTask(Task task) throws SQLException {
        ArrayList<Comment> comments = new ArrayList<>();
        ResultSet resultSet;
        String getByTask = "select * from `comment` join `user` on `comment`.`from` = `user`.`id` where `task_id` = ? order by `send_at`";
        PreparedStatement preparedStatement = preparedStatement(getByTask);
        preparedStatement.setInt(1, task.getId());
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            comments.add(new Comment(new User(
                    resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("name")),
                    resultSet.getString("text"), task,
                    resultSet.getTimestamp("send_at")));
        }
        return comments;
    }

    public static void save(Comment comment) throws SQLException {
        String insertComment = "insert into `comment` (`task_id`, `from`, `text`, `send_at`) value(?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertComment);
        preparedStatement.setInt(1, comment.getTask().getId());
        preparedStatement.setInt(2, comment.getFrom().getId());
        preparedStatement.setString(3, comment.getText());
        preparedStatement.setTimestamp(4, comment.getSendAt());
        preparedStatement.executeUpdate();
    }
}
