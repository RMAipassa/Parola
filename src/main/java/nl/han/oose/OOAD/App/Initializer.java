package nl.han.oose.OOAD.App;

import nl.han.oose.OOAD.DAO.QuizDAO;
import nl.han.oose.OOAD.DAO.UserDAO;
import nl.han.oose.OOAD.DAO.VraagDAO;
import nl.han.oose.OOAD.Managers.QuizManager;

public class Initializer {

    public void run(ParolaController parolaController) {
        QuizManager quizManager = new QuizManager();
        run(quizManager);
        parolaController.setQuizManager(quizManager);

//        QuizDAO quizDAO = new QuizDAO();
        VraagDAO vraagDAO = new VraagDAO();
        UserDAO userDAO = new UserDAO();
//        parolaController.setQuizDAO(quizDAO);
        parolaController.setUserDAO(userDAO);
        parolaController.setVraagDAO(vraagDAO);

    }

    public void run(QuizManager quizManager) {
        QuizDAO quizDAO = new QuizDAO();
        quizManager.setQuizDAO(quizDAO);
    }

}
