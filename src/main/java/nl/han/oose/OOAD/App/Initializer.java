package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;

public class Initializer {

    public void run(ParolaControllerOwn parolaControllerOwn) {
        QuizDAO quizDAO = new QuizDAO();
        VraagDAO vraagDAO = new VraagDAO();
        UserDAO userDAO = new UserDAO();
        parolaControllerOwn.setQuizDAO(quizDAO);
        parolaControllerOwn.setUserDAO(userDAO);
        parolaControllerOwn.setVraagDAO(vraagDAO);

    }

}
