package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.Map;

public class RegistrationManager {
    private UserDAO userDAO;

    public RegistrationManager() {
        userDAO = new UserDAO();
    }

    public boolean authenticateUser(String username) {
        return userDAO.authenticateUser(username);
    }

    public User createUser(String username) {
        if (userDAO.createUser(username)) {
            return new User(username);
        }
        return null;
    }

    public int getUserCredits(String username, Map<String, User> users) {
        User user = users.get(username);
        if (user != null) {
            return user.getCredits();
        }
        return 0;
    }

    public boolean deductCredits(String username, int amount, Map<String, User> users, CreditsManager creditsManager) {
        User user = users.get(username);
        if (user != null) {
            int currentCredits = user.getCredits();
            if (currentCredits >= amount) {
                user.deductCredits(amount);
                userDAO.updateUserCredits(username, user.getCredits());
                return true;
            }
        }
        return false;
    }
}
