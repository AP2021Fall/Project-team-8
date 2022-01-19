package app.jira.model.dao;

import app.jira.model.domain.Board;
import app.jira.model.domain.Participant;
import app.jira.model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipantDao extends Dao {
    /* Static Methods */
    public static ArrayList<Board> getBoardsByContributor(User user) throws SQLException {
        String getByContributor = "select `participant`.`participated_at`, `board`.`id` as `board_id`, `board`.`status`, `user`.`id` as `leader_id`, `user`.`name` as `leader_name`, `user`.`username`, `board`.`create_at`, `board`.`name` as `board_name` from `participant` join `board` on `participant`.`board_id` = `board`.`id` join `user` on `user`.`id` = `board`.`leader_id` where `participant`.`user_id`= ? and `board`.`leader_id` != `participant`.`user_id` order by `participant`.`participated_at` desc, `board`.`create_at` desc";
        ArrayList<Board> boards = new ArrayList<>();
        ResultSet resultSet;

        // Execution
        PreparedStatement preparedStatement = preparedStatement(getByContributor);
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            boards.add(new Board(
                    resultSet.getInt("board_id"),
                    new User(resultSet.getInt("leader_id"), resultSet.getString("username"), resultSet.getString("leader_name")),
                    resultSet.getString("board_name"),
                    resultSet.getTimestamp("create_at"),
                    ParticipantDao.getBoardContributors(resultSet.getInt("board_id")),
                    resultSet.getString("status")
            ));
        }
        return boards;
    }

    public static ArrayList<Participant> getBoardContributors(int boardId) throws SQLException {
        String getByPart = "select * from `user` join `participant` on `user`.`id` = `participant`.`user_id` where `participant`.`board_id` = ? order by `participant`.`participated_at` desc";
        ArrayList<Participant> contributors = new ArrayList<>();
        ResultSet resultSet;

        PreparedStatement preparedStatement = preparedStatement(getByPart);
        preparedStatement.setInt(1, boardId);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            contributors.add(new Participant(new User(
                    resultSet.getInt("user_id"),
                    resultSet.getString("username"),
                    resultSet.getString("name")
            ), resultSet.getTimestamp("participated_at"),
                    resultSet.getBoolean("is_suspend")));
        }
        return contributors;
    }

    public static void update(Participant participant, Board board) throws SQLException {
        String updateParticipant = "update `participant` set `is_suspend` = ? where `user_id` = ? and `board_id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateParticipant);
        preparedStatement.setBoolean(1, participant.isSuspend());
        preparedStatement.setInt(2, participant.getUser().getId());
        preparedStatement.setInt(3, board.getId());
        preparedStatement.executeUpdate();
    }

    public static void save(Participant participant, Board board) throws SQLException {
        String addToBoard = "insert into `participant` (`user_id`, `board_id`, `participated_at`) value (?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(addToBoard);
        preparedStatement.setInt(1, participant.getUser().getId());
        preparedStatement.setInt(2, board.getId());
        preparedStatement.setTimestamp(3, participant.getParticipated_at());
        preparedStatement.executeUpdate();
    }

    public static void delete(Board board, User user) throws SQLException {
        String deleteFromBoard = "delete from `participant` where `participant`.`board_id` = ? and `participant`.`user_id` = ?";
        PreparedStatement preparedStatement = preparedStatement(deleteFromBoard);
        preparedStatement.setInt(1, board.getId());
        preparedStatement.setInt(2, user.getId());
        preparedStatement.executeUpdate();
    }
}
