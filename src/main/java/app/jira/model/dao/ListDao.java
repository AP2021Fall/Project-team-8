package app.jira.model.dao;

import app.jira.model.domain.Board;
import app.jira.model.domain.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListDao extends Dao {
    public static List getListById(int listId) throws SQLException {
        String getListById = "select * from `list` where `list`.`id` = ?";
        ResultSet resultSet;
        List list;

        PreparedStatement preparedStatement = preparedStatement(getListById);
        preparedStatement.setInt(1, listId);
        resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) return null;
        resultSet.next();

        // Return List
        list = new List(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("order"));
        list.getTasks().addAll(TaskDao.getTasksByList(list));
        return list;
    }

    public static ArrayList<List> getBoardLists(Board board) throws SQLException {
        String getBoardList = "select `list`.* from `board` join `list` on `board`.`id` = `list`.`board_id` where `board`.`id` = ? order by `order` asc";
        ArrayList<List> lists = new ArrayList<>();
        ResultSet resultSet;
        List list;
        PreparedStatement preparedStatement = preparedStatement(getBoardList);
        preparedStatement.setInt(1, board.getId());
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list = new List(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("order")
            );
            list.getTasks().addAll(TaskDao.getTasksByList(list));
            lists.add(list);
        }
        return lists;
    }

    private static void updateOrder(Board board, List list, int addDelete) throws SQLException {
        String updateOrder = "update `list` SET `list`.`order` = `list`.`order` + (?) WHERE `list`.`board_id` = ? and `list`.`order` >= ?";
        PreparedStatement preparedStatement = preparedStatement(updateOrder);
        preparedStatement.setInt(1, addDelete);
        preparedStatement.setInt(2, board.getId());
        preparedStatement.setInt(3, list.getOrder());
        preparedStatement.executeUpdate();
    }

    private static void changeOrder(Board board, List list, int lastOrder) throws SQLException {
        int addSub = 1, min = list.getOrder(), max = lastOrder;
        if (lastOrder < list.getOrder()) {
            addSub = -1;
            min = lastOrder;
            max = list.getOrder();
        }

        String updateOrder = "update `list` SET `list`.`order` = `list`.`order` + (?) WHERE `list`.`board_id` = ? and `list`.`order` >= ? and `list`.`order` <= ?";
        PreparedStatement preparedStatement = preparedStatement(updateOrder);
        preparedStatement.setInt(1, addSub);
        preparedStatement.setInt(2, board.getId());
        preparedStatement.setInt(3, min);
        preparedStatement.setInt(4, max);
        preparedStatement.executeUpdate();
    }

    public static void update(List list, Board board, int lastOrder) throws SQLException {
        // Update List Order
        changeOrder(board, list, lastOrder);

        // Update This List
        String updateList = "update `list` set `name` = ?, `order` = ? where `list`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(updateList);
        preparedStatement.setString(1, list.getName());
        preparedStatement.setInt(2, list.getOrder());
        preparedStatement.setInt(3, list.getId());
        preparedStatement.executeUpdate();
    }

    public static void save(Board board, List list) throws SQLException {
        // Update List Order
        updateOrder(board, list, 1);

        // Insert List
        String insertList = "insert into `list` (`board_id`, `name`, `order`) value(?, ?, ?);";
        PreparedStatement preparedStatement = preparedStatement(insertList);
        preparedStatement.setInt(1, board.getId());
        preparedStatement.setString(2, list.getName());
        preparedStatement.setInt(3, list.getOrder());
        preparedStatement.executeUpdate();
        list.setId(getGenerateKey(preparedStatement));
    }

    public static void delete(List list, Board board) throws SQLException {
        // Update Priority
        updateOrder(board, list, -1);

        // Delete Task
        String deleteList = "delete from `list` where `list`.`id` = ?";
        PreparedStatement preparedStatement = preparedStatement(deleteList);
        preparedStatement.setInt(1, list.getId());
        preparedStatement.executeUpdate();
    }
}