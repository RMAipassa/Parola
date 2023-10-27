package nl.han.oose.OOAD.DAO;

import nl.han.oose.OOAD.DTO.AntwoordDTO;
import nl.han.oose.OOAD.databaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class AntwoordDAO {
    private DatabaseConnection databaseConnection =  new DatabaseConnection();

    public List<AntwoordDTO> getAntwoordenByVraagId(int vraagId) {
        databaseConnection.initConnection();
        List<AntwoordDTO> antwoorden = new ArrayList<>();
        try(Connection connection = databaseConnection.getConnection()) {
            String query = "SELECT id, answer_text, is_juist_antwoord FROM antwoord WHERE vraag_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, vraagId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String antwoordText = result.getString("answer_text");
                boolean isCorrect = result.getBoolean("is_juist_antwoord");

                AntwoordDTO antwoordDTO = new AntwoordDTO(id, antwoordText, isCorrect);
                antwoorden.add(antwoordDTO);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return antwoorden;
    }

}