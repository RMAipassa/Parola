package nl.han.oose.OOAD.Managers;

import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.DiyInject;
import nl.han.oose.OOAD.Schermen.VraagScherm;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.util.List;

public class VraagManager {
    private VraagDAO vraagDAO = new VraagDAO();
    private VraagScherm vraagScherm;

    @DiyInject
    public void setVraagDAO(VraagDAO vraagDAO) {
        this.vraagDAO = vraagDAO;
    }

    public void SelectVraag(){
        vraagScherm.ToonSelecteerFormulier();
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
}
