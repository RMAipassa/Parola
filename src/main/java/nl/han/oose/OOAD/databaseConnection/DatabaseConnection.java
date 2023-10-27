package nl.han.oose.OOAD.databaseConnection;

import nl.han.oose.OOAD.Exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private Connection connection;


    public Connection getConnection() {
        return connection;
    }

    public void initConnection() {
        DatabaseProperties databaseProperties = new DatabaseProperties();
        try {
            Class.forName(databaseProperties.driverString());
            connection = DriverManager.getConnection(databaseProperties.connectionString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

}