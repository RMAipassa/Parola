package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Schermen.RegistratieScherm;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

public class RegistrationManager {
    private UserDAO userDAO = new UserDAO();
    private RegistratieScherm registratieScherm;


    public void toonformulier(){
        registratieScherm.toonFormulier();
    }

    public boolean authenticateUser(String username) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        return userDAO.authenticateUser(username);
    }

    public User createUser(String username) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        if (userDAO.createUser(username)) {
            return new User(username);
        }
        return null;
    }
}
