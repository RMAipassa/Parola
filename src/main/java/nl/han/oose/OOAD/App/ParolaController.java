package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Managers.CreditsManager;
import nl.han.oose.OOAD.Managers.GameController;
import nl.han.oose.OOAD.Managers.QuizManager;
import nl.han.oose.OOAD.Managers.VraagManager;

import java.util.Map;

public class ParolaController {
    private static ParolaController instance;

    private QuizManager quizManager;

    private VraagManager vraagManager;
    private CreditsManager creditsManager;
    private GameController gameController;

    private Map<String, Long> quizStartTimeMap;
    private Map<String, User> users;

    public static ParolaController getInstance() {
        if (instance == null) {
            instance = new ParolaController();
        }
        return instance;
    }

    private ParolaController() {
        quizManager = new QuizManager();
        vraagManager = new VraagManager();
        creditsManager = new CreditsManager();
        gameController = new GameController();
        quizStartTimeMap = gameController.getQuizStartTimeMap();
        users = gameController.getUsers();
    }

    public void startQuiz(String playerName) {
        gameController.startQuiz(playerName, users, quizStartTimeMap, vraagManager, creditsManager, quizManager);
    }

    public String nextQuestion(String playerName) {
        return gameController.nextQuestion(playerName, quizManager);
    }

    public void processAnswer(String playerName, String answer) {
        gameController.processAnswer(playerName, answer, quizManager);
    }

    public boolean quizFinished(String playerName) {
        return gameController.quizFinished(playerName, quizManager);
    }


    public String getLettersForRightAnswers(String playerName) {
        return gameController.getLettersForRightAnswers(playerName, quizManager);
    }

    public int calculateScore(String playerName, String word) {
        return gameController.calculateScore(playerName, word);
    }
}
