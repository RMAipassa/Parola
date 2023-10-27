package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Managers.*;

import java.util.List;
import java.util.Map;

public class ParolaController {
    private static ParolaController instance;

    private QuizManager quizManager;
    private RegistrationManager registrationManager;
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
        registrationManager = new RegistrationManager();
        vraagManager = new VraagManager();
        creditsManager = new CreditsManager();
        gameController = new GameController();
        quizStartTimeMap = gameController.getQuizStartTimeMap();
        users = gameController.getUsers();
    }

    public boolean authenticateUser(String username) {
        return registrationManager.authenticateUser(username);
    }

    public boolean createUser(String username) {
        User newUser = registrationManager.createUser(username);
        if (newUser != null) {
            users.put(username, newUser);
            return true;
        }
        return false;
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

    public int getUserCredits(String username) {
        return registrationManager.getUserCredits(username, users);
    }

    public boolean deductCredits(String username, int amount) {
        return registrationManager.deductCredits(username, amount, users, creditsManager);
    }

    public List<QuizDTO> getQuizzes() {
        return quizManager.getQuizzes();
    }

    public List<VraagDTO> getVragen() {
        return vraagManager.getVragen();
    }

    public List<VraagDTO> getVragenByQuizId(int quizId) {
        return vraagManager.getVragenByQuizId(quizId);
    }

    public String getLettersForRightAnswers(String playerName) {
        return gameController.getLettersForRightAnswers(playerName, quizManager);
    }

    public int calculateScore(String playerName, String word) {
        return gameController.calculateScore(playerName, word);
    }
}
