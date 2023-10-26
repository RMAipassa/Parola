package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizManager {
	private QuizDAO quizDAO = new QuizDAO();

	@DiyInject
	public void setQuizDAO(QuizDAO quizDAO) {
		this.quizDAO = quizDAO;
	}

	public void toonaanmaakformulier(){

	}

	public List<QuizDTO> getQuizzes() {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		quizDAO.setDatabaseConnection(databaseConnection);
		return quizDAO.getQuizzes();
	}

	public QuizDTO getQuiz(String playerName) {
        // First, get all quizzes
        List<QuizDTO> allQuizzes = getQuizzes();

        // Filter unplayed quizzes
        List<QuizDTO> unplayedQuizzes = new ArrayList<>();
        for (QuizDTO quiz : allQuizzes) {
            if (!hasUserPlayedQuiz(playerName, quiz.getId())) {
                unplayedQuizzes.add(quiz);
            }
        }

        // If unplayed quizzes are available, select one randomly
        if (!unplayedQuizzes.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(unplayedQuizzes.size());
            return unplayedQuizzes.get(randomIndex);
        }

        // If no unplayed quizzes are available, get a random played quiz
        return getRandomPlayedQuizFromDatabase(allQuizzes);
    }

	private QuizDTO getRandomPlayedQuizFromDatabase(List<QuizDTO> quizzes) {
        Random random = new Random();
        int randomIndex = random.nextInt(quizzes.size());

        QuizDTO quiz = quizzes.get(randomIndex);

        return quiz;
    }

    private boolean hasUserPlayedQuiz(String playerName, int id) {
        return quizDAO.hasUserPlayedQuiz(playerName, id);
    }
}
