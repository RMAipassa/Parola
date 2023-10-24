package nl.han.oose.OOAD.databaseConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseProperties {
    private Logger logger = Logger.getLogger(getClass().getName());
    private Properties properties;

    public DatabaseProperties() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                logger.log(Level.SEVERE, "Database properties file 'database.properties' not found");
                // You can throw a custom exception here if desired
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while loading database properties", e);
        }
    }

    public String connectionString() {
        return properties.getProperty("connectionString");
    }

    public String driverString() {
        return properties.getProperty("driver");
    }
}