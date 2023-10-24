package nl.han.oose.OOAD.DAO;
import nl.han.oose.OOAD.DTO.QuizDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    private Connection connection;

    public QuizDAO(Connection connection) {
        this.connection = connection;
    }

    public List<QuizDTO> getQuizzes() {
        List<QuizDTO> quizzes = new ArrayList<>();
        try {
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

