package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Managers.GameManager;

import java.util.HashMap;
import java.util.Map;

public class ParolaController {
    private static ParolaController instance;

    private QuizDAO quizDAO;
    private VraagDAO vraagDAO;
    private UserDAO userDAO;
    private Map<String, User> users = new HashMap<>();
    private Map<String, GameManager> GameMangerMap = new HashMap<>();


    public static ParolaController getInstance() {
        if (instance == null) {
            instance = new ParolaController();
        }
        return instance;
    }

    public void startQuiz(String playername) {
    }

    public boolean nextQuestion(String playername) {
    }

    public void processAnswer(String playername, String answer) {
    }

    public boolean quizFinished(String playername) {
    }

    public String getLettersForRightAnswers(String playername) {
    }

    public int calculateScore(String playername, String word) {
    }
}
