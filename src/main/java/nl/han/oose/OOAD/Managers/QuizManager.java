package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.List;

public class QuizManager {
	private QuizDAO quizDAO;

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
		// Implement logic to get an available quiz
		// You can check if the player has already played a quiz or select a random quiz
		// Return a QuizDTO if available, or null if none are available
		return null;
	}
}
