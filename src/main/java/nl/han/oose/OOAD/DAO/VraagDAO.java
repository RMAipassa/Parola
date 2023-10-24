package nl.han.oose.OOAD.DAO;

import jakarta.inject.Inject;
import nl.han.oose.OOAD.DTO.VraagDTO;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VraagDAO {
    private DatabaseConnection databaseConnection;
    @Inject
    public void setDatabaseConnection(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public List<VraagDTO> getVragen() {
        databaseConnection.initConnection();
        List<VraagDTO> vragen = new ArrayList<>();
        try(Connection connection = databaseConnection.getConnection()){
            String vraagQuery = "SELECT id, question_text, is_multiple_choice, quiz_id FROM vraag";
            PreparedStatement vraagStatement = connection.prepareStatement(vraagQuery);
            ResultSet vraagResult = vraagStatement.executeQuery();

            while (vraagResult.next()) {
                int id = vraagResult.getInt("id");
                String vraagText = vraagResult.getString("question_text");
                boolean isMultipleChoice = vraagResult.getBoolean("is_multiple_choice");
                int quizId = vraagResult.getInt("quiz_id");

                VraagDTO vraagDTO = new VraagDTO(id, vraagText, isMultipleChoice, quizId);
                vragen.add(vraagDTO);
            }

            vraagResult.close();
            vraagStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vragen;
    }

    public List<VraagDTO> getVragenByQuizId(int quizId) {
        databaseConnection.initConnection();
        List<VraagDTO> vragen = new ArrayList<>();
        try(Connection connection = databaseConnection.getConnection()){
            String vraagQuery = "SELECT id, question_text, is_multiple_choice, quiz_id FROM vraag WHERE quiz_id = ?";
            PreparedStatement vraagStatement = connection.prepareStatement(vraagQuery);
            vraagStatement.setInt(1, quizId);
            ResultSet vraagResult = vraagStatement.executeQuery();

            while (vraagResult.next()) {
                int id = vraagResult.getInt("id");
                String vraagText = vraagResult.getString("question_text");
                boolean isMultipleChoice = vraagResult.getBoolean("is_multiple_choice");

                VraagDTO vraagDTO = new VraagDTO(id, vraagText, isMultipleChoice, quizId);
                vragen.add(vraagDTO);
            }

            vraagResult.close();
            vraagStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vragen;
    }
}
