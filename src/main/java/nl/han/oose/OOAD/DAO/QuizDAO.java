package nl.han.oose.OOAD.DAO;
import jakarta.inject.Inject;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import javax.xml.crypto.Data;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private DatabaseConnection databaseConnection;
    @DiyInject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<QuizDTO> getQuizzes() {
        databaseConnection.initConnection();
        List<QuizDTO> quizzes = new ArrayList<>();
        try(Connection connection = databaseConnection.getConnection()){
            String quizQuery = "SELECT id, name, theme FROM quiz";
            PreparedStatement quizStatement = connection.prepareStatement(quizQuery);
            ResultSet quizResult = quizStatement.executeQuery();

            while (quizResult.next()) {
                int id = quizResult.getInt("id");
                String name = quizResult.getString("name");
                String theme = quizResult.getString("theme");

                QuizDTO quizDTO = new QuizDTO(id, name, theme);
                quizzes.add(quizDTO);
            }

            quizResult.close();
            quizStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public boolean hasUserPlayedQuiz(String playerName, int quizId) {
        databaseConnection.initConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT Heeft_gespeeld FROM User_Played WHERE user_id = (SELECT id FROM users WHERE username = ?) AND Quiz_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            statement.setInt(2, quizId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getBoolean("Heeft_gespeeld");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

