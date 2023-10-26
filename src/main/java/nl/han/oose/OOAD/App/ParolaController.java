package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.DTO.QuizDTO;
import nl.han.oose.OOAD.DTO.UserDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.Entity.Antwoord;
import nl.han.oose.OOAD.Entity.QuizResult;
import nl.han.oose.OOAD.Entity.User;
import nl.han.oose.OOAD.Entity.Vraag;
import nl.han.oose.OOAD.Managers.*;
import nl.han.oose.OOAD.Strategies.CorrectAnswersCalculationStrategy;
import nl.han.oose.OOAD.Strategies.ScoreCalculationStrategy;
import nl.han.oose.OOAD.Strategies.TotalTimeCalculationStrategy;
import nl.han.oose.OOAD.Strategies.WordLengthCalculationStrategy;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParolaController {
    private static ParolaController instance;

    private QuizManager quizManager = new QuizManager();
    private RegistrationManager registrationManager = new RegistrationManager();
    private VraagManager vraagManager = new VraagManager();

    private CreditsManager creditsManager = new CreditsManager();
    private Map<String, Long> quizStartTimeMap = new HashMap<>();
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
            users.put(username, newUser);
            return true;
        }
        return false;
    }

    public void startQuiz(String playerName) {
        loadUsersFromDatabase();
        if(!authenticateUser(playerName)){
            System.out.println("User doesnt exist, creating user ..... ..... .....");
            createUser(playerName);
        }
        if(getUserCredits(playerName) < 40){
            System.out.println("User doesnt have enough credits, you need to buy more to continue playing");
            int boughtCredits = creditsManager.buyCredits();
            User user = users.get(playerName);
            user.addCredits(boughtCredits);


        }
        QuizDTO quiz = quizManager.getQuiz(playerName);
        if (quiz == null) {
            System.out.println("No available quizzes or all quizzes have been played.");
            return;
        }


        quizStartTimeMap.put(playerName, System.currentTimeMillis() / 1000);


        List<VraagDTO> questions = getVragenByQuizId(quiz.getId());
        if (questions.isEmpty()) {
            System.out.println("No questions found for the selected quiz.");
            return;
        }

        deductCredits(playerName, 40);
        GameManager gameManager = new GameManager(questions);
        GameMangerMap.put(playerName, gameManager);
    }

    public void loadUsersFromDatabase() {
        UserDAO userDAO = new UserDAO();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        userDAO.setDatabaseConnection(databaseConnection);
        List<UserDTO> usersFromDB = userDAO.getAllUsers();
        for (UserDTO userDTO : usersFromDB) {
            User user = new User(userDTO.getUsername());
            user.addCredits(userDTO.getCredits());
            users.put(userDTO.getUsername(), user);
        }
    }
    public String nextQuestion(String playerName) {
        GameManager gameManager = GameMangerMap.get(playerName);

        if (gameManager != null) {
            if (!FirstquestionPlayed) {
                this.FirstquestionPlayed = true;
                Vraag currentVraag = gameManager.getCurrentVraag();
                return gameManager.getQuestionTextWithAnswers(currentVraag);
            }

            Vraag vraag = gameManager.getNextVraag();

            if (vraag != null) {
                return gameManager.getQuestionTextWithAnswers(vraag);
            }
        }

        return "No more questions or quiz not started.";
    }

    public void processAnswer(String playerName, String answer) {
        GameManager gameManager = GameMangerMap.get(playerName);
        if (gameManager != null) {
            Vraag vraag = gameManager.getCurrentVraag();
            if (vraag != null) {
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

    public boolean checkAnswer(Vraag vraag, String answer) {
        if (vraag.isMultipleChoice()) {
            int selectedOption = answer.toUpperCase().charAt(0) - 'A';
            if (selectedOption >= 0 && selectedOption < vraag.getAntwoorden().size()) {
                return vraag.getAntwoorden().get(selectedOption).isCorrect();
            }
        } else {
            for (Antwoord antwoord : vraag.getAntwoorden()) {
                if (antwoord.isCorrect() && antwoord.getAntwoordText().equalsIgnoreCase(answer)) {
                    return true;
                }
            }
        }

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
        GameManager gameManager = GameMangerMap.get(playerName);
        return gameManager.getLetters();
    }


    public int calculateScore(String playerName, String word) {
        GameManager gameManager = GameMangerMap.get(playerName);
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

    public int getQuizTimeElapsed(String playerName) {
        Long startTime = quizStartTimeMap.get(playerName);
        if (startTime != null) {
            long currentTime = System.currentTimeMillis() / 1000;
            return (int)(currentTime - startTime);
        }
        return -1;
    }
}
