package nl.han.oose.OOAD.DAO;

import nl.han.oose.OOAD.DTO.UserDTO;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DatabaseConnection databaseConnection;

    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<UserDTO> getAllUsers() {
        databaseConnection.initConnection();
        List<UserDTO> users = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection()) {
            String query = "SELECT username, credits FROM users";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String username = result.getString("username");
                int credits = result.getInt("credits");
                users.add(new UserDTO(username, credits));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean authenticateUser(String username) {
        databaseConnection.initConnection();
        try(Connection connection = databaseConnection.getConnection()){
            String query = "SELECT username, password FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createUser(String username) {
        databaseConnection.initConnection();
        try(Connection connection = databaseConnection.getConnection()){
            String query = "INSERT INTO users (username, password, credits) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, "password");
            statement.setInt(3, 1000);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
