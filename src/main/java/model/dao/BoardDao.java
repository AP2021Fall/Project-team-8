package model.dao;

import model.domain.Board;
import model.domain.Scoreboard;
import model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDao extends Dao {
    /* Static Methods */
    public static Scoreboard getScoreboardByBoard(Board board) throws SQLException {
        String getScoreboard = "select `user`.`id`, `user`.`name`, `user`.`username`, count(case when `task`.`status` = 'done' then 0 end) * (10) + count(case when `task`.`status` = 'failed' then 0 end) * (-5) as `score` from `assigned` join `task` on `assigned`.`task_id` = `task`.`id` join `user` on `user`.`id` = `assigned`.`user_id` where `task`.`board_id` = ? group by `user`.`id` order by `score` desc, `name` asc";
        Scoreboard scoreboard = new Scoreboard();
        ResultSet resultSet;

        // Execution
        PreparedStatement preparedStatement = preparedStatement(getScoreboard);
        preparedStatement.setInt(1, board.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            scoreboard.getUsersScore().put(new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("name")),
                    resultSet.getInt("score"));
        }
        return scoreboard;
    }

    public static ArrayList<Board> getBoardsByLeader(User user) throws Exception {
        String getByLeader = "select `board`.`id` as `board_id`, `board`.`name` as `board_name`, `board`.`create_at`, `board`.`status`, `user`.* from `user` join `board` on `user`.`id` = `board`.`leader_id` where `user`.`id` = ? order by `board`.`create_at` desc";
        ArrayList<Board> boards = new ArrayList<>();
        ResultSet resultSet;

        // Execution
        PreparedStatement preparedStatement = preparedStatement(getByLeader);
        preparedStatement.setInt(1, user.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            boards.add(new Board(
                    resultSet.getInt("board_id"),
                    new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("name")),
                    resultSet.getString("board_name"),
                    resultSet.getTimestamp("create_at"),
                    ParticipantDao.getBoardContributors(resultSet.getInt("board_id")),
                    resultSet.getString("status")
            ));
        }
        return boards;
    }

    public static ArrayList<Board> getAllBoards(boolean isPending) throws Exception {
        String getByLeader = "select `board`.`id` as `board_id`, `board`.`name` as `board_name`, `board`.`create_at`, `board`.`status`, `user`.* from `user` join `board` on `user`.`id` = `board`.`leader_id` order by `board`.`id`";
        String getByLeaderStatus = "select `board`.`id` as `board_id`, `board`.`name` as `board_name`, `board`.`create_at`, `board`.`status`, `user`.* from `user` join `board` on `user`.`id` = `board`.`leader_id` where `board`.`status` = 'pending' order by `board`.`create_at`";
        ArrayList<Board> boards = new ArrayList<>();
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        // Execution
        if (isPending) preparedStatement = preparedStatement(getByLeaderStatus);
        else preparedStatement = preparedStatement(getByLeader);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            boards.add(new Board(
                    resultSet.getInt("board_id"),
                    new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("name")),
                    resultSet.getString("board_name"),
                    resultSet.getTimestamp("create_at"),
                    ParticipantDao.getBoardContributors(resultSet.getInt("board_id")),
                    resultSet.getString("status")
            ));
        }
        return boards;
    }

    public static Board getBoardById(int id) throws SQLException {
        String getByUsername = "select `board`.`id` as `board_id`, `board`.`name` as `board_name`, `board`.`create_at`, `board`.`status`, `user`.* from `user` join `board` on `user`.`id` = `board`.`leader_id` where `board`.`id` = ?";
        ResultSet resultSet;

        PreparedStatement preparedStatement = preparedStatement(getByUsername);
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) return null;
        resultSet.next();

        // Return Board
        return new Board(
                resultSet.getInt("board_id"),
                new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("name")),
                resultSet.getString("board_name"),
                resultSet.getTimestamp("create_at"),
                ParticipantDao.getBoardContributors(resultSet.getInt("board_id")),
                resultSet.getString("status")
        );
    }

    public static void updateBoardsStatus(ArrayList<Board> boards, String status) throws SQLException {
        String updateStatus = "update `board` set `status` = ? where `board`.`id` = ?;";
        PreparedStatement preparedStatement = preparedStatement(updateStatus);
        int i = 0;

        for (Board board : boards) {
            board.setStatus(status);
            preparedStatement.setString(1, board.getStatus());
            preparedStatement.setInt(2, board.getId());

            preparedStatement.addBatch();
            i++;

            if (i % 1000 == 0 || i == boards.size()) {
                preparedStatement.executeBatch(); // Execute every 1000 items.
            }
        }
    }

    public static void save(Board board) throws SQLException {
        String insertBoard = "insert into `board` (`leader_id`, `name`, `create_at`) values (?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertBoard);
        preparedStatement.setInt(1, board.getLeader().getId());
        preparedStatement.setString(2, board.getName());
        preparedStatement.setTimestamp(3, board.getCreateAt());
        preparedStatement.executeUpdate();
        board.setId(getGenerateKey(preparedStatement));
    }

    public static void update(Board board) throws SQLException {
        String updateBoard = "update `board` set `leader_id` = ?, `name` = ?, `create_At` = ?, `status` = ? where `board`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateBoard);
        preparedStatement.setInt(1, board.getLeader().getId());
        preparedStatement.setString(2, board.getName());
        preparedStatement.setTimestamp(3, board.getCreateAt());
        preparedStatement.setString(4, board.getStatus());
        preparedStatement.setInt(5, board.getId());
        preparedStatement.executeUpdate();
    }

    public static void delete(Board board) throws SQLException {
        String deleteBoard = "delete from `board` where `board`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(deleteBoard);
        preparedStatement.setInt(1, board.getId());
        preparedStatement.executeUpdate();
    }
}
