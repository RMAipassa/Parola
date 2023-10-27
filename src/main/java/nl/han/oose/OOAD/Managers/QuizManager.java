package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.List;
import java.util.Random;

public class QuizManager {
    private QuizDAO quizDAO;

    public QuizManager() {
        quizDAO = new QuizDAO();
    }

    public List<QuizDTO> getQuizzes() {
        return quizDAO.getQuizzes();
    }

    public QuizDTO getQuiz(String playerName) {
        List<QuizDTO> allQuizzes = getQuizzes();
        List<QuizDTO> unplayedQuizzes = quizDAO.getUnplayedQuizzes(playerName, allQuizzes);

        if (!unplayedQuizzes.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(unplayedQuizzes.size());
            return unplayedQuizzes.get(randomIndex);
        }

        return quizDAO.getRandomPlayedQuiz(allQuizzes);
    }
}
