package nl.han.oose.OOAD.databaseConnection;

import jakarta.inject.Inject;
import nl.han.oose.OOAD.Exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private Connection connection;
    private DatabaseProperties databaseProperties;

    public Connection getConnection() {
        return connection;
    }

    public void initConnection() {
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

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    // Puur voor test doeleinden.
    public DatabaseProperties getDatabaseProperties() {
        return databaseProperties;
    }

}