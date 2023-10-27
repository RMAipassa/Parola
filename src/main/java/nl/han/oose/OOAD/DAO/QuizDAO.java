package nl.han.oose.OOAD.DAO;

import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizDAO {
    private DatabaseConnection databaseConnection = new DatabaseConnection();



    public List<QuizDTO> getQuizzes() {
        databaseConnection.initConnection();
        List<QuizDTO> quizzes = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection()) {
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

    public List<QuizDTO> getUnplayedQuizzes(String playerName, List<QuizDTO> allQuizzes) {
        List<QuizDTO> unplayedQuizzes = new ArrayList<>();
        for (QuizDTO quiz : allQuizzes) {
            if (!hasUserPlayedQuiz(playerName, quiz.getId())) {
                unplayedQuizzes.add(quiz);
            }
        }
        return unplayedQuizzes;
    }

    public QuizDTO getRandomPlayedQuiz(List<QuizDTO> quizzes) {
        Random random = new Random();
        int randomIndex = random.nextInt(quizzes.size());
        return quizzes.get(randomIndex);
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

    public void markQuizAsPlayed(String playerName, int quizId) {
        databaseConnection.initConnection();
        Connection connection = databaseConnection.getConnection();

        String insertQuery = "INSERT INTO User_Played (user_id, Quiz_id, Heeft_gespeeld) " +
                "VALUES ((SELECT id FROM users WHERE username = ?), ?, 1)";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, playerName);
            insertStatement.setInt(2, quizId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
