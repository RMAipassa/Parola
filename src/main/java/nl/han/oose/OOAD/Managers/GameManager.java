package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.Entity.Quiz;
import nl.han.oose.OOAD.Entity.Vraag;

import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private List<Vraag> questions;
    private int currentQuestionIndex;

    public GameManager(List<VraagDTO> vraagDTOs) {
        this.questions = new LinkedList<>();
        for (VraagDTO vraagDTO : vraagDTOs) {
            Vraag vraag = new Vraag(vraagDTO.getId(), vraagDTO.getVraagText(), vraagDTO.isMultipleChoice());
            questions.add(vraag);
        }
        currentQuestionIndex = 0;
    }

    public Vraag getCurrentVraag() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public Vraag getNextVraag() {
        currentQuestionIndex++;
        return getCurrentVraag();
    }

    public void addCorrectAnswer() {
        // Implement logic to track correct answers
        // For example, increment a score or store information about the player's performance
    }

    public boolean isQuizFinished() {
        return currentQuestionIndex >= questions.size();
    }
}

