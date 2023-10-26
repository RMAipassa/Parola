package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Entity.Vraag;
import nl.han.oose.OOAD.Managers.GameManager;
import nl.han.oose.OOAD.Managers.QuizManager;
import nl.han.oose.OOAD.Managers.RegistrationManager;
import nl.han.oose.OOAD.Managers.VraagManager;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParolaController {
    private static ParolaController instance;

    private QuizManager quizManager = new QuizManager();
    private RegistrationManager registrationManager = new RegistrationManager();
    private VraagManager vraagManager = new VraagManager();
//    private QuizDAO quizDAO;
//    private VraagDAO vraagDAO;
//    private UserDAO userDAO;
    private Map<String, User> users = new HashMap<>();
    private Map<String, GameManager> GameMangerMap = new HashMap<>();

    private boolean FirstquestionPlayed = false;


    public static ParolaController getInstance() {
        if (instance == null) {
            instance = new ParolaController();
        }
        return instance;
    }

    public boolean authenticateUser(String username) {
        return registrationManager.authenticateUser(username);
    }

    public boolean createUser(String username) {
        User newUser = registrationManager.createUser(username);
        if (newUser != null) {
            // Add created User object to the users map
            users.put(username, newUser);
            return true;
        }
        return false;
    }

    public void startQuiz(String playerName) {
        if(!authenticateUser(playerName)){
            System.out.println("User doesnt exist, creating user ..... ..... .....");
            createUser(playerName);
        }
        QuizDTO quiz = quizManager.getQuiz(playerName);
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

    public String nextQuestion(String playerName) {
        GameManager gameManager = GameMangerMap.get(playerName);
        if (gameManager != null) {
            if(!FirstquestionPlayed){
                this.FirstquestionPlayed = true;
                return gameManager.getCurrentVraag().getVraagText();

            }
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
        return quizManager.getQuizzes();
    }

    public List<VraagDTO> getVragen() {
        return vraagManager.getVragen();
    }

    public List<VraagDTO> getVragenByQuizId(int quizId) {
        return vraagManager.getVragenByQuizId(quizId);
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
    public void setQuizManager(QuizManager quizManager){
        this.quizManager = quizManager;
    }
    @DiyInject
    public void setRegistrationManager(RegistrationManager registrationManager){
        this.registrationManager = registrationManager;
    }
    @DiyInject
    public void setVraagManager(VraagManager vraagManager){
        this.vraagManager = vraagManager;
    }
//    @DiyInject
//    public void setQuizDAO(QuizDAO quizDAO){
//        this.quizDAO = quizDAO;
//    }
//    @DiyInject
//    public void setVraagDAO(VraagDAO vraagDAO){
//        this.vraagDAO = vraagDAO;
//    }
//    @DiyInject
//    public void setUserDAO(UserDAO userDAO){
//        this.userDAO = userDAO;
//    }
}
