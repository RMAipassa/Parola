package nl.han.oose.OOAD.App;

import jakarta.inject.Inject;
import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Entity.Vraag;
import nl.han.oose.OOAD.Managers.GameManager;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
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

    public boolean authenticateUser(String username, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        return userDAO.authenticateUser(username, password);
    }

    public boolean createUser(String username, String password) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        if (userDAO.createUser(username, password)) {
            // Create a User object and add it to the users map
            User newUser = new User(username, password);
            users.put(username, newUser);
            return true;
        }
        return false;
    }

    public void startQuiz(String playerName) {
        // Retrieve a quiz to start
        QuizDTO quiz = getQuiz(playerName);
        if (quiz == null) {
            System.out.println("No available quizzes or all quizzes have been played.");
            return;
        }

        // Retrieve questions for the selected quiz
        List<VraagDTO> questions = getVragenByQuizId(quiz.getId());
        if (questions.isEmpty()) {
            System.out.println("No questions found for the selected quiz.");
            return;
        }

        // Create a GameManager object to store quiz-related data
        GameManager gameManager = new GameManager(questions);
        GameMangerMap.put(playerName, gameManager);
        System.out.println("Quiz started. Good luck!");
    }

    public String nextVraag(String playerName) {
        GameManager gameManager = GameMangerMap.get(playerName);
        if (gameManager != null) {
            Vraag vraag = gameManager.getNextVraag();
            if (vraag != null) {
                return vraag.getVraagText();
            }
        }
        return "No more questions or quiz not started.";
    }

    public void processAnswer(String playerName, String answer) {
        GameManager gameManager = GameMangerMap.get(playerName);
        if (gameManager != null) {
            Vraag vraag = gameManager.getCurrentVraag();
            if (vraag != null) {
                // Check the answer and update quiz data accordingly
                boolean isCorrect = checkAnswer(vraag, answer);
                if (isCorrect) {
                    gameManager.addCorrectAnswer();
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect.");
                }
            }
        }
    }

    public boolean quizFinished(String playerName) {
        GameManager gameManager = GameMangerMap.get(playerName);
        return gameManager == null || gameManager.isQuizFinished();
    }

    private QuizDTO getQuiz(String playerName) {
        // Implement logic to get an available quiz
        // You can check if the player has already played a quiz or select a random quiz
        // Return a QuizDTO if available, or null if none are available
        return null;
    }

    private boolean checkAnswer(Vraag vraag, String answer) {
        // Implement answer checking logic, depending on whether it's a multiple-choice or open question
        // Return true if the answer is correct, false otherwise
        return false;
    }
    
    public int getUserCredits(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getCredits();
        }
        return 0;
    }

    public void deductCredits(String username, int amount) {
        User user = users.get(username);
        if (user != null) {
            int currentCredits = user.getCredits();
            if (currentCredits >= amount) {
                user.deductCredits(40);
            }
        }
    }

    public List<QuizDTO> getQuizzes() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        quizDAO.setDatabaseConnection(databaseConnection);
        return quizDAO.getQuizzes();
    }

    public List<VraagDTO> getVragen() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        vraagDAO.setDatabaseConnection(databaseConnection);
        return vraagDAO.getVragen();
    }

    public List<VraagDTO> getVragenByQuizId(int quizId) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        vraagDAO.setDatabaseConnection(databaseConnection);
        return vraagDAO.getVragenByQuizId(quizId);
    }

    public String getLettersForRightAnswers(String playerName) {
        // Implement logic to get letters for right answers
        return "ABCDE";
    }

    public int calculateScore(String playerName, String word) {
        // Implement score calculation logic
        return 0;
    }
    @DiyInject
    public void setQuizDAO(QuizDAO quizDAO){
        this.quizDAO = quizDAO;
    }
    @DiyInject
    public void setVraagDAO(VraagDAO vraagDAO){
        this.vraagDAO = vraagDAO;
    }
    @DiyInject
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }
}
