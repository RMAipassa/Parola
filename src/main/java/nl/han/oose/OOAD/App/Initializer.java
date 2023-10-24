package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;

public class Initializer {

    public void run(ParolaController parolaController) {
        QuizDAO quizDAO = new QuizDAO();
        VraagDAO vraagDAO = new VraagDAO();
        UserDAO userDAO = new UserDAO();
        parolaController.setQuizDAO(quizDAO);
        parolaController.setUserDAO(userDAO);
        parolaController.setVraagDAO(vraagDAO);

    }

}
