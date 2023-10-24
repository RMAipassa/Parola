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
}

