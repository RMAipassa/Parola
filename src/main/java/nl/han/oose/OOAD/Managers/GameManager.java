package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.AntwoordDAO;
import nl.han.oose.OOAD.DTO.AntwoordDTO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.Entity.Quiz;
import nl.han.oose.OOAD.Entity.Vraag;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private List<Vraag> questions;
    private int currentQuestionIndex;



    private AntwoordDAO antwoordDAO = new AntwoordDAO();

    private DatabaseConnection databaseConnection = new DatabaseConnection();

    public GameManager(List<VraagDTO> vraagDTOs) {

        this.questions = new LinkedList<>();
        for (VraagDTO vraagDTO : vraagDTOs) {
            Vraag vraag = new Vraag(vraagDTO.getId(), vraagDTO.getVraagText(), vraagDTO.isMultipleChoice());

            // Retrieve answers for the current vraagDTO
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

    public void setAntwoordDAO(AntwoordDAO antwoordDAO) {
        this.antwoordDAO = antwoordDAO;
    }
}



