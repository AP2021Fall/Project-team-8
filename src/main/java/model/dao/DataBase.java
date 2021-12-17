package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBase {
    /* Static Fields */
    private static DataBase dataBase = new DataBase();

    /* Instance Fields */
    private Statement statement;
    private Connection connection;

    /* Constructor */
    private DataBase() {
        // This is singleton class
    }

    /* Static Methods */
    public static DataBase getInstance() {
        if (DataBase.dataBase == null)
            DataBase.dataBase = new DataBase();

        return DataBase.dataBase;
    }

    /* Getters And Setters */
    public Statement getStatement() {
        return statement;
    }

    private void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    /* Instance Methods */
    public void run() {
        try {
            // load the mysql-JDBC driver using the current class loader
            Class.forName("com.mysql.cj.jdbc.Driver");

            // create a database connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jira", "root", "");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            setStatement(statement);
            setConnection(connection);

        } catch (SQLException | ClassNotFoundException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            System.exit(e.hashCode());
        }
    }

    public void close() {
        try {
            if (getConnection() != null)
                getConnection().close();
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
            System.exit(e.hashCode());
        }
    }
}