package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Schermen.RegistratieScherm;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

public class RegistrationManager {
    private UserDAO userDAO;
    private RegistratieScherm registratieScherm;

    @DiyInject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void toonformulier(){
        registratieScherm.toonFormulier();
    }

    public boolean authenticateUser(String username, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        return userDAO.authenticateUser(username, password);
    }

    public User createUser(String username, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        if (userDAO.createUser(username, password)) {
            // Create a User object
            return new User(username, password);
        }
        return null;
    }
}
