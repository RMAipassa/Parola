package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.AntwoordDAO;
import nl.han.oose.OOAD.DTO.AntwoordDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.Entity.Quiz;
import nl.han.oose.OOAD.Entity.Vraag;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private List<Vraag> questions;
    private int currentQuestionIndex;

    private String randomLetters;
    private int correctAnswerCount;

    private AntwoordDAO antwoordDAO = new AntwoordDAO();

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    private static final String LETTER_POOL = "aeiouybcdfghjklmnpqrstvwxz";

    public GameManager(List<VraagDTO> vraagDTOs) {

        this.questions = new LinkedList<>();
        this.randomLetters = "";
        this.correctAnswerCount = 0;
        for (VraagDTO vraagDTO : vraagDTOs) {
            Vraag vraag = new Vraag(vraagDTO.getId(), vraagDTO.getVraagText(), vraagDTO.isMultipleChoice());


            List<AntwoordDTO> vraagAntwoorden = getAntwoordenByVraagId(vraagDTO.getId());

            for (AntwoordDTO antwoordDTO : vraagAntwoorden) {
                vraag.addAntwoord(antwoordDTO.getAntwoordText(), antwoordDTO.isCorrect());
            }

            questions.add(vraag);
        }

        currentQuestionIndex = 0;
    }

    private List<AntwoordDTO> getAntwoordenByVraagId(int id) {
        antwoordDAO.setDatabaseConnection(databaseConnection);
        return antwoordDAO.getAntwoordenByVraagId(id);
    }

    public Vraag getCurrentVraag() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public String getQuestionTextWithAnswers(Vraag vraag) {
        StringBuilder questionTextWithAnswers = new StringBuilder(vraag.getVraagText());

        if (vraag.isMultipleChoice()) {
            List<String> answerOptions = new ArrayList<>();
            for (int i = 0; i < vraag.getAntwoorden().size(); i++) {
                answerOptions.add((char) ('A' + i) + ". " + vraag.getAntwoorden().get(i).getAntwoordText());
            }
            String answers = String.join("\n", answerOptions);
            questionTextWithAnswers.append("\n").append(answers);
        }

        return questionTextWithAnswers.toString();
    }
    public Vraag getNextVraag() {
        currentQuestionIndex++;
        return getCurrentVraag();
    }

    public void addCorrectAnswer() {
        this.correctAnswerCount++;
        char randomLetter = generateRandomLetter();
        randomLetters += randomLetter;
    }

    public boolean isQuizFinished() {
        return currentQuestionIndex >= questions.size();
    }




    private char generateRandomLetter() {
        Random random = new Random();
        int poolSize = LETTER_POOL.length();
        char randomLetter = LETTER_POOL.charAt(random.nextInt(poolSize));
        return randomLetter;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public String getLetters() {
        return randomLetters;
    }
    public void setAntwoordDAO(AntwoordDAO antwoordDAO) {
        this.antwoordDAO = antwoordDAO;
    }
}



