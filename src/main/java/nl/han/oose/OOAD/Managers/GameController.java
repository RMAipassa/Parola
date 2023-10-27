package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DTO.UserDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.Entity.QuizResult;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Entity.Vraag;
import nl.han.oose.OOAD.Strategies.CorrectAnswersCalculationStrategy;
import nl.han.oose.OOAD.Strategies.ScoreCalculationStrategy;
import nl.han.oose.OOAD.Strategies.TotalTimeCalculationStrategy;
import nl.han.oose.OOAD.Strategies.WordLengthCalculationStrategy;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private Map<String, GameManager> gameManagerMap;
    private Map<String, Long> quizStartTimeMap;
    private Map<String, User> users;
    private Map<String, Boolean> lastQuestionPlayedMap = new HashMap<>();

    private QuizDTO quiz;
    public GameController() {
        gameManagerMap = new HashMap<>();
        quizStartTimeMap = new HashMap<>();
        users = new HashMap<>();
        lastQuestionPlayedMap = new HashMap<>();
    }

    public void startQuiz(String playerName, Map<String, User> users, Map<String, Long> quizStartTimeMap,
                          VraagManager vraagManager, CreditsManager creditsManager, QuizManager quizManager) {
        loadUsersFromDatabase(users);
        if (!authenticateUser(playerName, users)) {
            System.out.println("User doesn't exist, creating user...");
            createUser(playerName, users);
        }
        if (getUserCredits(playerName, users) < 40) {
            System.out.println("User doesn't have enough credits, you need to buy more to continue playing");
            int boughtCredits = creditsManager.buyCredits();
            User user = users.get(playerName);
            user.addCredits(boughtCredits);
            UserDAO userDAO = new UserDAO();
            if (userDAO.updateUserCredits(playerName, user.getCredits())) {
                if (deductCredits(playerName, 40, users)) {
                    System.out.println("User paid for the quiz.");
                }
            }
        }
        this.quiz = quizManager.getQuiz(playerName);
        if (quiz == null) {
            System.out.println("No available quizzes or all quizzes have been played.");
            return;
        }
        quizStartTimeMap.put(playerName, System.currentTimeMillis() / 1000);
        List<VraagDTO> questions = vraagManager.getVragenByQuizId(quiz.getId());
        if (questions.isEmpty()) {
            System.out.println("No questions found for the selected quiz.");
            return;
        }
        lastQuestionPlayedMap.put(playerName, false);
        GameManager gameManager = new GameManager(questions);
        gameManagerMap.put(playerName, gameManager);
    }

    public String nextQuestion(String playerName, QuizManager quizManager) {
        GameManager gameManager = gameManagerMap.get(playerName);
        boolean lastQuestionPlayed = lastQuestionPlayedMap.get(playerName);

        if (gameManager != null) {
            if (!gameManager.isFirstQuestionPlayed()) {
                gameManager.setFirstQuestionPlayed(true);
                Vraag currentVraag = gameManager.getCurrentVraag();
                return gameManager.getQuestionTextWithAnswers(currentVraag);
            }
            Vraag vraag = gameManager.getNextVraag();

            if (vraag != null) {
                if (gameManager.isLastQuestion(vraag)) {
                    lastQuestionPlayedMap.put(playerName, true); // Mark the last question as played
                }
                return gameManager.getQuestionTextWithAnswers(vraag);
            }
        }

        return lastQuestionPlayed ? "No more questions or quiz not started." : "Give your answer to this question";
    }

    public void processAnswer(String playerName, String answer, QuizManager quizManager) {
        GameManager gameManager = gameManagerMap.get(playerName);
        if (gameManager != null) {
            Vraag vraag = gameManager.getCurrentVraag();
            if (vraag != null) {
                boolean isCorrect = gameManager.checkAnswer(vraag, answer);
                if (isCorrect) {
                    gameManager.addCorrectAnswer();
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect.");
                }
            }
        }
    }

    public boolean quizFinished(String playerName, QuizManager quizManager) {
        GameManager gameManager = gameManagerMap.get(playerName);
        QuizDAO quizDAO = new QuizDAO();
        if (gameManager == null || (gameManager.isLastQuestionAnswered())) {
            int quizId = quiz.getId();

            boolean hasBeenPlayed = quizDAO.hasUserPlayedQuiz(playerName, quizId);

            if (!hasBeenPlayed) {
                quizDAO.markQuizAsPlayed(playerName, quizId);
            }

            return true;
        }

        return false;
    }

    public String getLettersForRightAnswers(String playerName, QuizManager quizManager) {
        GameManager gameManager = gameManagerMap.get(playerName);
        return gameManager.getLetters();
    }

    public int calculateScore(String playerName, String word) {
        GameManager gameManager = gameManagerMap.get(playerName);
        int correctAnswerCount = gameManager.getCorrectAnswerCount();
        int wordLength = word.length();
        int totalTime = getQuizTimeElapsed(playerName);

        QuizResult quizResult = new QuizResult(correctAnswerCount, wordLength, totalTime);


        ScoreCalculationStrategy correctAnswersStrategy = new CorrectAnswersCalculationStrategy();
        ScoreCalculationStrategy totalTimeStrategy = new TotalTimeCalculationStrategy();
        ScoreCalculationStrategy wordLengthStrategy = new WordLengthCalculationStrategy();


        int correctAnswersScore = correctAnswersStrategy.calculateScore(quizResult);
        int totalTimeScore = totalTimeStrategy.calculateScore(quizResult);
        int wordLengthScore = wordLengthStrategy.calculateScore(quizResult);


        boolean wordExists = checkWordInWordList(word);

        int finalScore = correctAnswersScore + totalTimeScore;


        if (wordExists) {
            finalScore += wordLengthScore;
        }

        return finalScore;
    }

    private boolean authenticateUser(String username, Map<String, User> users) {
        return users.containsKey(username);
    }

    private void createUser(String username, Map<String, User> users) {
        User newUser = new User(username);
        users.put(username, newUser);
    }

    private void loadUsersFromDatabase(Map<String, User> users) {
        UserDAO userDAO = new UserDAO();
        List<UserDTO> usersFromDB = userDAO.getAllUsers();
        for (UserDTO userDTO : usersFromDB) {
            User user = new User(userDTO.getUsername());
            user.setCredits(userDTO.getCredits());
            users.put(userDTO.getUsername(), user);
        }
    }


    private int getUserCredits(String username, Map<String, User> users) {
        User user = users.get(username);
        if (user != null) {
            return user.getCredits();
        }
        return 0;
    }

    private boolean deductCredits(String username, int amount, Map<String, User> users) {
        User user = users.get(username);
        if (user != null) {
            int currentCredits = user.getCredits();
            if (currentCredits >= amount) {
                user.deductCredits(amount);
                UserDAO userDAO = new UserDAO();
                return (userDAO.updateUserCredits(username, user.getCredits()));
            }
        }
        return false;
    }

    private int getQuizTimeElapsed(String playerName) {
        Long startTime = quizStartTimeMap.get(playerName);
        if (startTime != null) {
            long currentTime = System.currentTimeMillis() / 1000;
            return (int) (currentTime - startTime);
        }
        return -1;
    }

    private boolean checkWordInWordList(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/wordlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Word not found in the wordlist.txt file
    }

    public Map<String, GameManager> getGameManagerMap() {
        return gameManagerMap;
    }

    public Map<String, Long> getQuizStartTimeMap() {
        return quizStartTimeMap;
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
