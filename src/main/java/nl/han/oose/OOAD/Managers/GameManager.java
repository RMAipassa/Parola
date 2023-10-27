package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.AntwoordDAO;
import nl.han.oose.OOAD.DTO.AntwoordDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.Entity.Antwoord;
import nl.han.oose.OOAD.Entity.Vraag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private List<Vraag> questions;
    private int currentQuestionIndex;
    private String randomLetters;
    private int correctAnswerCount;
    private boolean isFirstQuestionPlayed;
    private boolean isLastQuestionAnswered;

    private AntwoordDAO antwoordDAO = new AntwoordDAO();

    private static final String LETTER_POOL = "aeiouybcdfghjklmnpqrstvwxz";

    public GameManager(List<VraagDTO> vraagDTOs) {
        this.questions = new LinkedList<>();
        this.randomLetters = "";
        this.correctAnswerCount = 0;
        this.isFirstQuestionPlayed = false;
        this.isLastQuestionAnswered = false;

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
        if (isLastQuestion(vraag)) {
            this.isLastQuestionAnswered = true;
        }

        return questionTextWithAnswers.toString();
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

    public Vraag getNextVraag() {
        currentQuestionIndex++;
        return getCurrentVraag();
    }

    public void addCorrectAnswer() {
        this.correctAnswerCount++;
        char randomLetter = generateRandomLetter();
        randomLetters += randomLetter;
    }


    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    public String getLetters() {
        return randomLetters;
    }

    public boolean isFirstQuestionPlayed() {
        return isFirstQuestionPlayed;
    }

    public boolean isLastQuestion(Vraag vraag) {
        return currentQuestionIndex == questions.size() - 1 && vraag == questions.get(questions.size() - 1);
    }

    public boolean isLastQuestionAnswered() {
        return isLastQuestionAnswered;
    }

    public void setFirstQuestionPlayed(boolean firstQuestionPlayed) {
        isFirstQuestionPlayed = firstQuestionPlayed;
    }

    private char generateRandomLetter() {
        Random random = new Random();
        int poolSize = LETTER_POOL.length();
        char randomLetter = LETTER_POOL.charAt(random.nextInt(poolSize));
        return randomLetter;
    }
}
