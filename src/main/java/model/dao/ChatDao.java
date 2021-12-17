package model.dao;

import model.domain.Board;
import model.domain.Chat;
import model.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatDao extends Dao {
    /* Static Methods */
    public static ArrayList<Chat> getChatsByBoard(Board board) throws SQLException {
        String getChats = "select * from `user` join `chat` on `user`.`id` = `chat`.`send_by` where `chat`.`board_id` = ? order by `chat`.`send_at` desc";
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        ArrayList<Chat> chats = new ArrayList<>();

        preparedStatement = preparedStatement(getChats);
        preparedStatement.setInt(1, board.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            chats.add(new Chat(board,
                    new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("name")),
                    resultSet.getTimestamp("send_at"),
                    resultSet.getString("text")));
        }
        return chats;
    }

    public static void save(Chat chat) throws SQLException {
        String insertChat = "insert into `chat` (`board_id`, `send_by`, `send_at`, `text`) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = preparedStatement(insertChat);
        preparedStatement.setInt(1, chat.getBoard().getId());
        preparedStatement.setInt(2, chat.getSendBy().getId());
        preparedStatement.setTimestamp(3, chat.getSendAt());
        preparedStatement.setString(4, chat.getMessage());
        preparedStatement.executeUpdate();
    }
}
