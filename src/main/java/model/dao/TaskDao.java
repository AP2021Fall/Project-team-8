package model.dao;

import model.domain.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskDao extends Dao {
    /* Static Methods */
    public static Calendar getTaskByUser(User user) throws SQLException {
        String getTaskByUser = "select `task`.*, `board`.`name` as `board_name`, DATEDIFF(`task`.`deadline`, CURRENT_TIMESTAMP()) as `days` from `assigned` join `task` on `assigned`.`task_id` = `task`.`id` join `board` on `task`.`board_id` = `board`.`id` where `assigned`.`user_id` = ? and `task`.`deadline` > CURRENT_TIMESTAMP() and `task`.`status` != 'done' order by `task`.`deadline` desc";
        Calendar calendar = new Calendar();
        Task task;
        ResultSet resultSet;
        PreparedStatement preparedStatement = preparedStatement(getTaskByUser);
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();

        // Return Tasks
        while (resultSet.next()) {
            task = new Task(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    Priority.getPriorityByNth(resultSet.getInt("priority")),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("deadline"),
                    resultSet.getString("description"));
            task.setDeadlineDays(resultSet.getInt("days"));
            calendar.getTaskBoard().put(task, resultSet.getString("board_name"));
        }
        return calendar;
    }

    public static Task getTaskByBoard(Board board, int taskId) throws SQLException {
        String getByBoard = "select * from `task` where `task`.`id` = ? and `task`.`board_id` = ?";
        ResultSet resultSet;
        Task task;

        PreparedStatement preparedStatement = preparedStatement(getByBoard);
        preparedStatement.setInt(1, taskId);
        preparedStatement.setInt(2, board.getId());
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) return null;
        resultSet.next();

        // Return Task
        task = new Task(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                Priority.getPriorityByNth(resultSet.getInt("priority")),
                resultSet.getString("status"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("deadline"),
                resultSet.getString("description")
        );
        task.setList(ListDao.getListById(resultSet.getInt("list_id")));
        task.getAssignedUsers().putAll(AssignedDao.getAssignedUser(task));
        task.getComments().addAll(CommentDao.getCommentsByTask(task));
        return task;
    }

    public static Roadmap getRoadmapByBoard(Board board) throws SQLException {
        Roadmap roadmap = new Roadmap();
        ResultSet resultSet;
        String getTasksByBoard = "select *, `list`.`order` * 100 / (select count(*)from `list` where `list`.`board_id` = ?) as `percentage` from `list` join `task` on `list`.`id` = `task`.`list_id` where `task`.`board_id` = ? order by  `task`.`created_at` desc, `task`.`title`";
        PreparedStatement preparedStatement = preparedStatement(getTasksByBoard);
        preparedStatement.setInt(1, board.getId());
        preparedStatement.setInt(2, board.getId());
        resultSet = preparedStatement.executeQuery();

        // Return Tasks
        while (resultSet.next()) {
            roadmap.getTaskPercentage().put(new Task(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    Priority.getPriorityByNth(resultSet.getInt("priority")),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("deadline"),
                    resultSet.getString("description")
            ), resultSet.getInt("percentage"));
        }
        return roadmap;
    }

    public static ArrayList<Task> getTasksByList(List list) throws SQLException {
        // Update Status
        updateStatus(list);

        // Get Tasks
        String getTasks = "select `task`.`id` as `task_id`, `task`.`title`,`task`.`description`, `priority`, `status`, `created_at`, `deadline` from `list` join `task` on `list`.`id` = `task`.`list_id` where `list`.`id` = ?  order by `deadline` desc, `priority` desc";
        ArrayList<Task> tasks = new ArrayList<>();
        ResultSet resultSet;
        PreparedStatement preparedStatement = preparedStatement(getTasks);
        preparedStatement.setInt(1, list.getId());
        resultSet = preparedStatement.executeQuery();
        Task task;

        while (resultSet.next()) {
            task = new Task(
                    resultSet.getInt("task_id"),
                    resultSet.getString("title"),
                    Priority.getPriorityByNth(resultSet.getInt("priority")),
                    resultSet.getString("status"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getTimestamp("deadline"),
                    resultSet.getString("description")
            );
            task.setList(list);
            task.getAssignedUsers().putAll(AssignedDao.getAssignedUser(task));
            tasks.add(task);
        }
        return tasks;
    }

    private static void updateStatus(List list) throws SQLException {
        String updateStatus = "update `task` set `status` = if(`status` = 'done', 'done', if(`deadline` < CURRENT_TIMESTAMP(), 'failed', 'in-progress')) where `list_id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateStatus);
        preparedStatement.setInt(1, list.getId());
        preparedStatement.executeUpdate();
    }

    public static void save(Task task) throws SQLException {
        // Insert Task
        String insertTask = "insert into `task` (`board_id`, `list_id`, `title`, `created_at`, `deadline`, `priority`, `description`) values(? ,? ,? ,? ,? ,?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertTask);
        preparedStatement.setInt(1, task.getBoard().getId());
        preparedStatement.setInt(2, task.getList().getId());
        preparedStatement.setString(3, task.getTitle());
        preparedStatement.setTimestamp(4, task.getCreateAt());
        preparedStatement.setTimestamp(5, task.getDeadline());
        preparedStatement.setInt(6, task.getPriority().getNth());
        preparedStatement.setString(7, task.getDescription());
        preparedStatement.executeUpdate();
        task.setId(getGenerateKey(preparedStatement));
    }

    public static void update(Task task) throws SQLException {
        // Update This Task
        String updateTask = "update `task` set `list_id` = ?, `title` = ?, `priority` = ?, `status` = ?, `deadline` = ?, `description` = ?  where `task`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateTask);
        preparedStatement.setInt(1, task.getList().getId());
        preparedStatement.setString(2, task.getTitle());
        preparedStatement.setInt(3, task.getPriority().getNth());
        preparedStatement.setString(4, task.getStatus());
        preparedStatement.setTimestamp(5, task.getDeadline());
        preparedStatement.setString(6, task.getDescription());
        preparedStatement.setInt(7, task.getId());
        preparedStatement.executeUpdate();
    }

    public static void delete(Task task) throws SQLException {
        // Delete Task
        String deleteTask = "delete from `task` where `task`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(deleteTask);
        preparedStatement.setInt(1, task.getId());
        preparedStatement.executeUpdate();
    }
}
