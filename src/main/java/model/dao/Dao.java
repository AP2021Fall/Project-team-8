package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public abstract class Dao<T> {
    /* Static Fields */
    protected static DataBase dataBase = DataBase.getInstance();

    /* Instance Methods */
    public abstract void save(T t) throws SQLException;

    public abstract void update(T t) throws SQLException;

    public abstract void delete(T t);


    protected PreparedStatement preparedStatement(String query) {
        try {
            return dataBase.getConnection().prepareStatement(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(e.getErrorCode());
        }
        return null;
    }

    protected ArrayList<Dictionary<String, String>> getResult(PreparedStatement preparedStatement) {
        String temp;
        try {
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            ArrayList<Dictionary<String, String>> resultSet = new ArrayList<>();
            while (rs.next()) {
                Dictionary<String, String> res = new Hashtable<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    temp = rs.getString(i);
                    res.put(metaData.getColumnName(i), temp == null ? "" : temp);
                }
                resultSet.add(res);
            }
            return resultSet;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    protected void exeUpdate(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    protected int getGenerateKey() {
        try {
            return dataBase.getStatement().getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(e.getErrorCode());
        }
        return 0;
    }

    protected int getCount(PreparedStatement preparedStatement) {
        try {
            ResultSet rs = preparedStatement.executeQuery();
            int counter = 0;
            while (rs.next()) {
                counter++;
            }
            return counter;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(e.getErrorCode());
        }
        return 0;
    }
}
