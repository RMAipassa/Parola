package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.List;

public class VraagManager {
    private VraagDAO vraagDAO;

    public VraagManager() {
        vraagDAO = new VraagDAO();
    }

    public List<VraagDTO> getVragen() {
        return vraagDAO.getVragen();
    }

    public List<VraagDTO> getVragenByQuizId(int quizId) {
        return vraagDAO.getVragenByQuizId(quizId);
    }
}
