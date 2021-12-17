package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Dao {
    /* Static Fields */
    protected static DataBase dataBase = DataBase.getInstance();

    /* Static Methods */
    protected static PreparedStatement preparedStatement(String query) {
        try {
            return dataBase.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(e.getErrorCode());
        }
        return null;
    }

    protected static int getGenerateKey(PreparedStatement preparedStatement) {
        ResultSet resultSet;
        try {
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(e.getErrorCode());
        }
        return 0;
    }

    protected static int getCount(PreparedStatement preparedStatement) {
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
